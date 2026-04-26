

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import dao.ILineaTicketDAO;
import dao.IProductoDAO;
import dao.ITicketDAO;
import dao.impl.LineaTicketDAOOracle;
import dao.impl.ProductoDAOOracle;
import dao.impl.TicketDAOOracle;
import entities.LineaTicket;
import entities.Producto;
import entities.Ticket;

public class Programa {
	

	private static final String [] OPCIONES_MENU_GENERAL = {
			"Menu inventario",
			"Menu TPV"
	};
	private static final String [] OPCIONES_MENU_TPV = {
			"Iniciar nueva venta",
			"Continuar venta",
			"Consultar ticker",
			"Devolver compra",
	};
	
	
    private static Scanner sc = new Scanner(System.in);
    private static IProductoDAO productoDAO = new ProductoDAOOracle();
    private static ITicketDAO ticketDAO = new TicketDAOOracle();
    private static ILineaTicketDAO lineaDAO = new LineaTicketDAOOracle();
    // Utiliza aquí el nombre del esquema y contraseña que tú hayas utilizado
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	private static final String USR = "java";
	private static final String PWD = "123";
    
    public static void main(String[] args) {

        try(Connection con = DriverManager.getConnection(URL, USR, PWD)){
            System.out.println("Conexión establecida");
            System.out.println("Esquema actual: " + con.getSchema());
            menuGeneral(con);
            
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }finally{
            sc.close();
        }
    }
    
    private static void menuGeneral(Connection con) throws SQLException {
    	int opcion = -1;
    	do {
			pintarMenu("MENU GENERAL", OPCIONES_MENU_GENERAL);
			opcion = Utilidades.leerEntero(sc, 0, 2, "Seleccione una opción: ");
			switch (opcion) {
			case 1:
				menuInventario(con);
				break;
			case 2:
				menuTPV(con);
				break;
			}
		} while (opcion != 0);
    }
    
    private static void llenarTicket(Connection con, Ticket ticket) throws SQLException {
    	try {
    		if(ticket.isTicketCerrado()) {
    			System.out.println("\nEl ticket seleccionado esta cerrado. No se puede agregar filas.");
    			return;
    		}
			con.setAutoCommit(false);
			String cerrarTicket = "";
			Producto producto = null;
			do {
				
			System.out.println("Dejar vacio para cerrar.");
			long idProducto = Utilidades.leerIdProducto(sc);
			producto = productoDAO.buscar(con, idProducto);    					
				
				if(producto != null) {    					
					System.out.println("Producto seleccionado: " + producto.toString());
					int cantidad = Utilidades.leerEntero(sc, 1, Integer.MAX_VALUE, "Ingresa la cantidad: ");
					LineaTicket linea = lineaDAO.crear(con, cantidad, producto.getPrecio(), producto, ticket.getId());    	
					ticket.getLineaTicket().add(linea);
					System.out.println("Linea agregada: " + linea.toString());
				} else {
					cerrarTicket = Utilidades.leerCadena(sc, "Si quieres cerrar el ticket escribe (T) o dejarlo abierto (F): ").toUpperCase().trim();
					if(cerrarTicket.equals("T")) {
						ticketDAO.modificar(con, ticket.getId(), true);
					}
				}
			}while(producto != null);
			con.commit();
		}catch(SQLException e) {
			con.rollback(); // por pue me pide un try?
			System.out.println("Error en la venta: " + e.getMessage());
		}finally {
			con.setAutoCommit(true);
		}
    }
    
    private static void iniciarVenta(Connection con) throws SQLException {
    		try {
    			con.setAutoCommit(false);
    			Ticket nuevoTicket = ticketDAO.crear(con, LocalDateTime.now(), false);
    			String cerrarTicket = "";
    			Producto producto = null;
    			if(nuevoTicket == null) {
    				System.out.println("Fallo la creacion del ticket");
    				return;
    			}
    			do {
    				
    					
				long idProducto = Utilidades.leerIdProducto(sc);
				producto = productoDAO.buscar(con, idProducto);    					
    				
    				if(producto != null) {    					
    					System.out.println("Producto seleccionado: " + producto.toString());
    					int cantidad = Utilidades.leerEntero(sc, 1, Integer.MAX_VALUE, "Ingresa la cantidad: ");
    					LineaTicket linea = lineaDAO.crear(con, cantidad, producto.getPrecio(), producto, nuevoTicket.getId());    	
    					nuevoTicket.getLineaTicket().add(linea);
    				} else {
    					cerrarTicket = Utilidades.leerCadena(sc, "Si quieres cerrar el ticket escribe (T) o dejarlo abierto (F): ").toUpperCase().trim();
    					if(cerrarTicket.equals("T")) {
    						ticketDAO.modificar(con, nuevoTicket.getId(), true);
    					}
    				}
    			}while(producto != null);
    			con.commit();
    		}catch(SQLException e) {
    			con.rollback(); // por pue me pide un try?
    			System.out.println("Error en la venta: " + e.getMessage());
    		}finally {
    			con.setAutoCommit(true);
    		}
    }
    
    private static void continuarVenta(Connection con) {
    		try {
    			List<Ticket> abiertos = ticketDAO.listarAbierto(con);
    			System.out.println("Lista tickets abiertos");
    			for (Ticket ticket : abiertos) {
					System.out.println("- " + ticket.toString());
				}
    			long idTicket = Utilidades.leerLongPositivo(sc, "Selecciona el id del ticket: ");
    			Ticket continuarTicket = ticketDAO.buscar(con, idTicket);
    			List<LineaTicket> lineas = lineaDAO.listarPorTicketId(con, idTicket);
    			for (LineaTicket lineaTicket : lineas) {
				System.out.println("- " + lineaTicket.toString());
			}
    			
    			llenarTicket(con, continuarTicket);
    			
    		}catch(SQLException e) {
    			System.out.println(e.getMessage());
    		}
    }
    
    private static void consultarTicket(Connection con) {
	    	try {
	    		long idTicket = Utilidades.leerLongPositivo(sc, "Ingresa el id del ticket a consultar: ");
	    		Ticket ticketBuscado = ticketDAO.buscar(con, idTicket);
	    		List<LineaTicket> lineas = lineaDAO.listarPorTicketId(con, ticketBuscado.getId());
	    		if(!ticketBuscado.isTicketCerrado()) {
	    			System.out.println("EL TICKET ESTA ABIERTO!!!!");
	    		}
	    		System.out.println("*****Ticket*****");
	    		System.out.println(ticketBuscado.toString());
	    		System.out.println("Lineas");
	    		for (LineaTicket lineaTicket : lineas) {
	    			System.out.println("- " + lineaTicket.toString());
			}
	    		
	    	}catch(SQLException e) {
	    		System.out.println();
	    	}
    }
    
    private static void eliminarTicket(Connection con) {
	    	try {
	    		long idTicket = Utilidades.leerLongPositivo(sc, "Escribe el id del ticket a eliminar: ");
	    		boolean eliminado = ticketDAO.eliminar(con, idTicket);
	    		if(eliminado) {
	    			System.out.println("Eliminado existosamente");
	    		}else {
	    			System.out.println("Error al eliminar");
	    		}
	    	}catch(SQLException e) {
	    		System.out.println(e.getMessage());
	    	}
    		
    }

    private static void menuTPV(Connection con) throws SQLException {
    	int opcion = -1;
    	do {
			pintarMenu("MENU TPV", OPCIONES_MENU_TPV);
			opcion = Utilidades.leerEntero(sc, 0, 4, "Seleccione una opción: ");
			switch (opcion) {
			case 1:
				iniciarVenta(con);
				break;
			case 2:
				continuarVenta(con);
				break;
			case 3:
				consultarTicket(con);
				break;
			case 4:
				eliminarTicket(con);
				break;
			}
		} while (opcion != 0);
    }
    
    private static void menuInventario(Connection con){
        int opcion;
        do{
            opcionesInventario();
            opcion = Utilidades.leerEntero(sc, 0, 5, "Seleccione una opción: ");
            switch(opcion){
                case 1:
                    buscarProducto(con);
                    break;
                case 2:
                    listarProductos(con);
                    break;
                case 3:
                    crearProducto(con);
                    break;
                case 4:
                    modificarProducto(con);
                    break;
                case 5:
                    eliminarProducto(con);
                    break;
                case 0:
                    System.out.println("Hasta pronto");
                    break;
            }
        }while (opcion != 0);
    }

    private static <T> void pintarMenu(String titulo, T [] opciones) {
    	System.out.println("\n--- " + titulo + " ---");
    	int i = 1;
    	for (T t : opciones) {
			System.out.println(i + ". " + t);
			i++;
		}
    	System.out.println("0. Salir");
    }
    
    private static void opcionesInventario(){
        System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
        System.out.println("1. Buscar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Crear producto");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Borrar producto");
        System.out.println("0. Salir");
    }
    
    private static void buscarProducto(Connection con){
        long id = Utilidades.leerLongPositivo(sc, "Introduzca id del producto: ");
        try{
            Producto p = productoDAO.buscar(con, id);
            if (p != null){
                System.out.println(p);
                return;
            }
            System.out.println("Producto no encontrado");
        }catch(SQLException e){
            System.out.println("Error al leer de la base de datos: " + e.getMessage());
        }
    }

    private static void listarProductos(Connection con){
        try{
            List<Producto> productos = productoDAO.listar(con);
            if (productos.isEmpty()){
                System.out.println("No hay productos en la base de datos");
                return;
            }
            for (Producto p : productos){
                System.out.println(p);
            }
        }catch(SQLException e){
            System.out.println("Error al leer de la base de datos: " + e.getMessage());
        }
    }

    private static void crearProducto(Connection con){
        String barcode = Utilidades.leerCadena(sc, "Barcode del producto: ");
        String nombre = Utilidades.leerCadena(sc, "Nombre del producto: ");
        Double precio = Utilidades.leerDoublePositivo(sc, "Precio del producto: ");
        try{
            Producto p = productoDAO.crear(con, barcode, nombre, precio);
            if (p != null){
                System.out.println("Producto creado correctamente: " + p);
                return;
            }
            System.out.println("No se pudo crear el producto");
        }catch(SQLException e){
            System.out.println("Error al crear el producto: " + e.getMessage());
        }
    }

    private static void modificarProducto(Connection con){
        long id = Utilidades.leerLongPositivo(sc, "Introduzca id del producto: ");
        
        try{
            Producto p = productoDAO.buscar(con, id);
            if (p == null){
                    System.out.println("Producto no encontrado");
                    return;
            }
            String barcode = Utilidades.leerCadena(sc, "Barcode del producto(" + p.getBarcode() + "): ");
            String nombre = Utilidades.leerCadena(sc, "Nombre del producto(" + p.getNombre() + "): ");
            Double precio = Utilidades.leerDoubleOpcional(sc, "Precio del producto(" + p.getPrecio() + "): ");
            if (barcode.isEmpty()){
                barcode = p.getBarcode();
            }
            if (nombre.isEmpty()){
                nombre = p.getNombre();
            }
            if (precio == null){
                precio = p.getPrecio();
            }
            p = productoDAO.modificar(con, id, barcode, nombre, precio);
            if (p != null){
                System.out.println("Producto modificado correctamente: " + p);
                return;
            }
            System.out.println("Producto no encontrado");
        }catch(SQLException e){
            System.out.println("Error al modificar el producto: " + e.getMessage());
        }
    }

    private static void eliminarProducto(Connection con){
        long id = Utilidades.leerLongPositivo(sc, "Introduzca id del producto: ");
        try{
            if(productoDAO.borrar(con, id)){
                System.out.println("Producto eliminado correctamente");
                return;
            }
            System.out.println("Producto no encontrado");
        }catch(SQLException e){
            System.out.println("Error al leer de la base de datos: " + e.getMessage());
        }
    }
}
