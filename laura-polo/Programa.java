package tarea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import tarea.dao.ILineaTicketDAO;
import tarea.dao.IProductoDAO;
import tarea.dao.ITicketDAO;
import tarea.dao.impl.LineaTicketDAOOracle;
import tarea.dao.impl.ProductoDAOOracle;
import tarea.dao.impl.TicketDAOOracle;
import tarea.entities.LineaTicket;
import tarea.entities.Producto;
import tarea.entities.Ticket;

public class Programa {
    private static Scanner sc = new Scanner(System.in);
    private static IProductoDAO productoDAO = new ProductoDAOOracle();
    private static ITicketDAO ticketDAO = new TicketDAOOracle();
    private static ILineaTicketDAO lineaTicketDAO = new LineaTicketDAOOracle();
    
    // Utiliza aquí el nombre del esquema y contraseña que tú hayas utilizado
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	private static final String USR = "java";
	private static final String PWD = "oracle123";
    
    public static void main(String[] args) {

        try(Connection con = DriverManager.getConnection(URL, USR, PWD)){
            System.out.println("Conexión establecida");
            System.out.println("Esquema actual: " + con.getSchema());

            menuPrincipal(con);
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }finally{
            sc.close();
        }
    }
    
    //metodos para gestionar el menu principal
    private static void menuPrincipal(Connection con) {
    	int opcion;
    	do {
    		pintarPrimerasOpciones();
    		opcion = Utilidades.leerEntero(sc, 0, 2, "Seleccione una opción: ");
    		switch(opcion) {
    		case 1:
    			menu(con);
    			break;
    		case 2:
    			menuVentas(con);
    			break;
    		case 0: 
    			System.out.println("Cerrando terminal... Hasta pronto!");
    			break;
    		}
    		
    	}while(opcion != 0);
    }

    private static void pintarPrimerasOpciones() {
    	System.out.println("\n==== SISTEMA DE GESTÓN (TPV) ====");
        System.out.println("1. Mantenimiento de productos");
        System.out.println("2. Gestón de ventas");
        System.out.println("0. Salir");
    }
    
    private static void menu(Connection con){
        int opcion;
        do{
            pintarOpciones();
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

    private static void pintarOpciones(){
        System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
        System.out.println("1. Buscar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Crear producto");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Borrar producto");
        System.out.println("0. Salir");
    }
    
    //Menu de gestion de ventas
    private static void menuVentas(Connection con) {
    	int opcion;
        do{
            pintarMenuVentas();
            opcion = Utilidades.leerEntero(sc, 0, 4, "Seleccione una opción: ");
            switch(opcion){
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
                	devolverCompra(con);
                    break;
                case 0:
                    System.out.println("Hasta pronto");
                    break;
            }
        }while (opcion != 0);
    }
    
    private static void pintarMenuVentas() {
    	System.out.println("\n--- GESTIÓN DE VENTAS ---");
        System.out.println("1. Iniciar nueva venta");
        System.out.println("2. Continuar venta");
        System.out.println("3. Consultar ticket");
        System.out.println("4. Devolver compra");
        System.out.println("0. Volver al menú principal");
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
    
    private static Map<Producto, Integer> pedirProductos(Connection con){
    	Map<Producto, Integer> productos = new HashMap<>();
    	Long productoId;
    	
    	do {
    		productoId = Utilidades.leerLongOpcional(sc, "Introduzca el id del producto: ");
    		if(productoId != null) {
    			
    			try {
    				Producto producto = productoDAO.buscar(con, productoId);
    				if(producto != null) {
    					Integer cantidad = Utilidades.leerEntero(sc, 1, Integer.MAX_VALUE, "Introduzca la cantidad: ");
    					
    					productos.put(new Producto(
    							producto.getId(), 
    							producto.getBarcode(),
    							producto.getNombre(),
    							producto.getPrecio()),
    							cantidad);
    				}else {
    					System.out.println("Producto no encontrado");
    				}
    			}catch(SQLException e) {
    				System.out.println("Error al leer de la base de datos: " + e.getMessage());
    			}
    		}
    		
    	}while(productoId != null);
    	
    	return productos;
    }
    
    private static void mostrarTotal(Connection con, Ticket ticket) throws SQLException{
    	System.out.println("\n\t********** TICKET **********");
    	System.out.println("-".repeat(50));
    	System.out.printf("%-25s %5s %8s %8s\n", "PRODUCTO", "CANT.", "PRECIO", "IMPORTE");
    	System.out.println("-".repeat(50));
    	List<LineaTicket> lineas = lineaTicketDAO.getAllById(con, ticket.getId());
    	
    	double total = 0;
    	double subtotal = 0;
    	for(LineaTicket lt : lineas) {
    		total += lt.getCantidad() * lt.getPrecioVenta();
    		subtotal = lt.getCantidad() * lt.getPrecioVenta();
    		long productoId = lt.getProductoId();
    		String nombreProducto="";
    		
    		nombreProducto = productoDAO.buscar(con, productoId).getNombre();
    		
    		System.out.printf("%-25s%5d%9.2f%9.2f€\n", nombreProducto, lt.getCantidad(), lt.getPrecioVenta(), subtotal);
    	}
    	
    	System.out.println("-".repeat(50));
    	System.out.printf("%-38s%10.2f€\n", "TOTAL", total);
    	System.out.printf("**ID: %d**\n", ticket.getId());
    }
    
    private static List<LineaTicket> crearLineasTicket(Connection con, Ticket ticket, Map<Producto, Integer> productos) throws SQLException {
    	List<LineaTicket> lineasCreadas = new ArrayList<>();
    	long ticketId = ticket.getId();

        for (Map.Entry<Producto, Integer> entrada : productos.entrySet()) {
            Producto producto = entrada.getKey();
            int cantidad = entrada.getValue();
            
            LineaTicket linea = lineaTicketDAO.crear(
                    con,
                    cantidad,
                    producto.getPrecio(),
                    producto.getId(),
                    ticketId);
            
            lineasCreadas.add(linea);
        }
        
        return lineasCreadas;
    }
    
    private static void iniciarVenta(Connection con) {
    	Map<Producto, Integer> productos = pedirProductos(con);
    	
    	if(productos.isEmpty()) {
    		System.out.println("Venta cancelada");
    		return;
    	}
    	String ticketCerrado;
		int opcionTicket = Utilidades.leerEntero(sc, 1, 2, "¿Desea cerrar el ticket? [ 1. Sí | 2. No ] ");
		if(opcionTicket == 1) {
			ticketCerrado = "T";
		}else {
			ticketCerrado = "F";
		}
		
    	//Lo recojo todo el un try-catch y lanzo las excepciones para no hacer varios try-catch cada vez que llamo a un método DAO
    	try {
    		con.setAutoCommit(false);
    		Ticket ticket = ticketDAO.crear(con, ticketCerrado);

    	     con.commit();
    	    
		    System.out.println("Ticket guardado correctamente");
		     
		    if(ticketCerrado.equals("T")) {
		    	mostrarTotal(con, ticket);
		    }
		    
    				
    	}catch(SQLException e) {
    		try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("Error al hacer rollback: " + e1.getMessage());
			}
    	}finally {
    		try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Error al restaurar autoCommit: " + e.getMessage());
			}
    	}
    	
    }
    
    
    private static void continuarVenta(Connection con) {
    	List<Ticket> ticketsSinCerrar = new ArrayList<>();
    	
    	try {
    		ticketsSinCerrar = ticketDAO.buscarTicketPorEstado(con, "F");
    		if (ticketsSinCerrar.isEmpty()) {
                System.out.println("No hay tickets abiertos.");
                return;
    		}
    		
    		System.out.println("\n**** Tickets abiertos ****");
    		System.out.printf("%8s%20s%15s\n", "ID", "FECHA/HORA", "LÍNEAS");
			
    		int contador=0;
    		for(Ticket ticket : ticketsSinCerrar) {
    			contador++;
    			System.out.printf("[%d]%s", contador, ticket);
    			List<LineaTicket> lineas = lineaTicketDAO.getAllById(con, ticket.getId());
    			System.out.printf("%8d\n", lineas.size());
    		}
    		
    		int opcion = Utilidades.leerEntero(sc, 0, ticketsSinCerrar.size(), "Seleccione un ticket por el número de impresión (0 para cancelar): ");
    		if(opcion == 0) {
    			System.out.println("Selección cancelada");
    			return;
    		}
    		Ticket ticketSeleccionado = ticketsSinCerrar.get(opcion-1);
    		
    		Map<Producto, Integer> productos = pedirProductos(con);
    		
    		int opcionTicket = Utilidades.leerEntero(sc, 1, 2, "¿Desea cerrar el ticket? [ 1. Sí | 2. No ] ");
    		//Si es true se actualiza el ticket como cerrado, si es false, se crean las lineas pero no se actualiza a nada
    		boolean cerrarTicket = (opcionTicket == 1 ? true : false);
    		if(actualizarTicket(con, ticketSeleccionado, productos, cerrarTicket)) {
    			mostrarTotal(con, ticketSeleccionado);
    		}
    		
    	}catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
    	
    }
    
    private static boolean actualizarTicket(Connection con, Ticket ticket, Map<Producto, Integer> productos, boolean cerrarTicket) {
    	try {
    		con.setAutoCommit(false);
    		
    		List<LineaTicket> lineasCreadas = crearLineasTicket(con, ticket, productos);
    		
    		if (cerrarTicket) {
                Ticket ticketModificado = ticketDAO.modificar(con, ticket.getId(), "T");
                if (ticketModificado == null) {
                    throw new SQLException("No se pudo cerrar el ticket");
                }
    		}
			
			con.commit();
			System.out.println("Ticket guardado correctamente");
			
			for (LineaTicket linea : lineasCreadas) {
	            ticket.anadirLinea(linea);
	        }
    		
			return true;
			
    	}catch(SQLException e) {
    		try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("Error al hacer rollback: " + e1.getMessage());
			}
    		System.out.println("Error en la venta. Operación anulada.");
    	}finally {
    		try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Error al restaurar autoCommit: " + e.getMessage());
			}
    	}
		return false;
    }
    
    
    private static void consultarTicket(Connection con) {
    	
    	try {
    		List<Ticket> listaTickets = ticketDAO.getAll(con);
    		if(listaTickets.isEmpty()) {
    			System.out.println("No hay tikets registrados");
    			return;
    		}
    		
    		long ticketId = Utilidades.leerLongPositivo(sc, "Introduzca el id del ticket: ");
    		Ticket ticket = ticketDAO.buscarPorId(con, ticketId);
    		if(ticket == null) {
    			throw new SQLException("Ticket no encontado.");
    		}
    		System.out.printf("\n%5s%20s%15s%15s\n", "ID", "FECHA/HORA", "LÍNEAS", "ESTADO");
			System.out.print(ticket);
			List<LineaTicket> lineas = lineaTicketDAO.getAllById(con, ticketId);
			System.out.printf("%8d", lineas.size());
    		if(ticket.getTicketCerrado().equals("F")) {
    			System.out.printf("%18s\n", "⚠ Abierto");
    		}else {
    			System.out.printf("%18s\n", "Cerrado");
    		}
    	}catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
    	
    }
    
    private static void devolverCompra(Connection con) {
    	long ticketId = Utilidades.leerLongPositivo(sc, "Introduzca el id del ticket: ");
    	try {
    		List<Ticket> ticketsCerrados = ticketDAO.buscarTicketPorEstado(con, "T");
    		if(ticketsCerrados.isEmpty()) {
    			System.out.println("No hay tickets registrados");
    			return;
    		}
    		for(Ticket ticket : ticketsCerrados) {
    			if(ticket.getId() == ticketId) {
    				if(ticketDAO.borrar(con, ticketId)) {
    					System.out.println("Compra devuelta. Ticket borrado correctamente.");
    					return;
    				}
    			}
    		}
    	}catch(SQLException e) {
    		System.out.println("No se ha podido realizar la operación: " + e.getMessage());
    	}
    }
}

