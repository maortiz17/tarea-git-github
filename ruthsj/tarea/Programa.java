package tarea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    
    private static ILineaTicketDAO lineaTicketDAO = new LineaTicketDAOOracle();
    private static ITicketDAO ticketDAO = new TicketDAOOracle();
    
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	private static final String USR = "java";
	private static final String PWD = "oracle123";
    
    public static void main(String[] args) {

        try(Connection con = DriverManager.getConnection(URL, USR, PWD)){
            System.out.println("Conexión establecida");
            System.out.println("Esquema actual: " + con.getSchema());

            menu(con);
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }finally{
            sc.close();
        }
    }

    private static void menu(Connection con){
        int opcion;
        do{
        	
        	pintarMenuPrincipal();
        	opcion = Utilidades.leerEntero(sc, 0, 2, "Seleccione una opción: ");
        	switch(opcion) {
        	case 1:
        		menuProductos(con);
        		break;
        	case 2:
        		menuVentas(con);
        		break;
        	case 0:
        		System.out.println("Hasta pronto");
        		break;
        	}
        	
        }while (opcion != 0);
    }

    private static void pintarOpcionesProductos(){
        System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
        System.out.println("1. Buscar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Crear producto");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Borrar producto");
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
    /* CÓDIGO NUEVO */
    private static void pintarMenuPrincipal() {
    	System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestión de productos");
        System.out.println("2. Gestión de ventas");
        System.out.println("0. Salir");
    }
    
    private static void pintarOpcionesVentas() {
    	System.out.println("\n--- GESTIÓN DE VENTAS ---");
        System.out.println("1. Iniciar nueva venta");
        System.out.println("2. Continuar venta");
        System.out.println("3. Consultar ticket");
        System.out.println("4. Devolver compra");
        System.out.println("0. Volver al menú principal");
    }
    
    private static void menuVentas(Connection con) {
    	int opcion;
    	do {
    		pintarOpcionesVentas();
    		opcion = Utilidades.leerEntero(sc, 0, 4, "Seleccione una opción: ");
    		switch(opcion) {
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
    		}
    	}while(opcion != 0);
    }
    
    private static void menuProductos(Connection con) {
    	int opcion;
    	do {
	        pintarOpcionesProductos();
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
	                //System.out.println("Hasta pronto");
	                break;
	        }
    	} while(opcion != 0);
    }
    
    private static void iniciarVenta(Connection con) {

    	Ticket ticket = new Ticket(LocalDateTime.now(), "F");
    	try {
    		con.setAutoCommit(false);
    		
        	long id = ticketDAO.crear(con, ticket);
        	
        	ticket.setId(id);
        	
        	anadirLineas(con, ticket);
        	
        	con.commit();
        	
    	}catch(SQLException e) {
    		try {
    			con.rollback();
    		}catch(SQLException ex) {
    			System.out.println("Error al hacer rollback: " + ex.getMessage());
    		}
    		System.out.println("Error al crear el ticket. Operación cancelada. " + e.getMessage());
    		
    	} finally {
    		try {
    			con.setAutoCommit(true);
    		}catch(SQLException ex) {
    			System.out.println("Error al restaurar el autocommit: " + ex.getLocalizedMessage());
    		}
    	}
    }
    
    private static void anadirLineas(Connection con, Ticket ticket) throws SQLException {
    	String idProducto = "";
    	do {
    		idProducto = Utilidades.leerCadena(sc, "Introduzca el id del producto");
    		if(!idProducto.isEmpty()) {
    			try {
    				long id = Long.parseLong(idProducto);
            		Producto producto = productoDAO.buscar(con, id);
            		if(producto == null) {
            			System.out.println("Producto no encontrado");
            		}else {
            			int cantidad = Utilidades.leerEntero(sc, 1, 99, "Introduzca la cantidad: ");
            			
            			LineaTicket linea = new LineaTicket(cantidad, producto.getPrecio(), producto.getId(), ticket.getId());
            			
            			lineaTicketDAO.crear(con, linea);
            			
            			ticket.getLineas().add(linea);
            			System.out.println(linea);            			
            		}
    			}catch(NumberFormatException e) {
    				System.out.println("Id no válido");
    			}
    		}else {
    			System.out.println("\n------ LINEAS DEL TICKET ------");
    			double total = 0;
				for(LineaTicket lt : ticket.getLineas()) {
					System.out.println(lt);
					total += lt.getPrecioVenta() * lt.getCantidad();
				}
				System.out.printf("TOTAL TICKET: %.2f\n", total);
    			while(true) {
    				String cerrar = Utilidades.leerCadena(sc, "¿Quiere cerrar el ticket? (T/F): ");
    				if (cerrar.equalsIgnoreCase("T")) {
    					ticketDAO.cerrar(con, ticket.getId());
    					System.out.println("Ticket cerrado correctamente");
    					return;    					
    				}else if (cerrar.equalsIgnoreCase("F")) {
    					System.out.println("Ticket guardado como abierto");
    					return;
    				} 				
    			}
    		}
    	}while(!idProducto.isEmpty());
    }
    
    private static void continuarVenta(Connection con) {
    	
    	List<Ticket> ticketsAbiertos = new ArrayList<>();
    	try {
    		
    		ticketsAbiertos = ticketDAO.listarAbiertos(con);
    		
    		if (ticketsAbiertos.isEmpty()) {
    			System.out.println("No hay tickets abiertos");
    			return;
    		}
    		
    		for(Ticket t : ticketsAbiertos) {
      			System.out.println(t);
    		}
    		
       		long idTicket = Utilidades.leerLongPositivo(sc, "Introduzca el id del ticket que desea continuar: ");
           	
       		Ticket ticketElegido = ticketDAO.buscarPorId(con, idTicket);
           	
       		if(ticketElegido == null || ticketElegido.getTicketCerrado().equals("T")) {
       			System.out.println("Ticket no válido");
       			return;
       		}
       		ticketElegido.setLineas(lineaTicketDAO.listarPorTicket(con, idTicket));
       		try {
       			con.setAutoCommit(false);
       			anadirLineas(con, ticketElegido);
       			con.commit();
       			
       		}catch(SQLException e) {
       			try {
       				con.rollback();
       			}catch(SQLException ex) {
       				System.out.println("Error al hacer rollback: " + ex.getMessage());
       			}
       			System.out.println("Error en la transacción. Operación cancelada. " + e.getLocalizedMessage());
       			
       		}finally {
       			try {
       				con.setAutoCommit(true);
       			}catch(SQLException ex) {
       				System.out.println("Error al restaurar el autocommit: " + ex.getMessage());
       			}
       		}    		
    	}catch(SQLException e) {
    		System.out.println(e.getMessage()); 
    	}	
    }
    
    private static void consultarTicket(Connection con) {    	
    	try {
    		boolean hayTickets = listarTickets(con);
    		if(!hayTickets) {
    			return;
    		}
    		
    		String entrada = Utilidades.leerCadena(sc, "Introduzca el id del ticket: ");
    		if(entrada.isEmpty()) return;
    		long idTicket = Long.parseLong(entrada);
    		Ticket ticketBuscado = ticketDAO.buscarPorId(con, idTicket);
    		if(ticketBuscado == null) {
    			System.out.println("Ticket no encontrado");
    			return;
    		}
    		ticketBuscado.setLineas(lineaTicketDAO.listarPorTicket(con, idTicket));
    		System.out.println(ticketBuscado);
    		double total = 0;
    		for(LineaTicket lt : ticketBuscado.getLineas()) {
    			System.out.println(lt);
    			total += lt.getPrecioVenta() * lt.getCantidad();
    		} 
    		
    		System.out.printf("\t\t\tTOTAL: %.2f€\n", total);
    		
    		if(ticketBuscado.getTicketCerrado().equals("F")) {
    			System.out.println("AVISO: Ticket abierto");
    		}
    		
    	}catch(NumberFormatException e) {
    		System.out.println(e.getMessage()); 
    	}catch(SQLException e) {
    		System.out.println(e.getMessage()); 
    	}
    }
    
    private static void devolverCompra(Connection con) {
    	try {
    		boolean hayTickets = listarTickets(con);
    		if(!hayTickets) {
    			return;
    		}
    		
    		String entrada = Utilidades.leerCadena(sc, "Introduzca el id del ticket: ");
    		if(entrada.isEmpty()) {
    			System.out.println("Salir sin devolver compra");
    			return;
    		}
    		long idTicket = Long.parseLong(entrada);    		
    		Ticket ticketBuscado = ticketDAO.buscarPorId(con, idTicket);
    		if(ticketBuscado == null) {
    			System.out.println("Ticket no encontrado");
    			return;
    		}else {
    			System.out.println(ticketBuscado);
    		}
    		String confirmacion = "";
    		do {
    			confirmacion = Utilidades.leerCadena(sc, "¿Quiere borrar el ticket? (S/N): ");
    			if(confirmacion.equalsIgnoreCase("S")){
        			ticketDAO.borrar(con, idTicket);
        		}else if (confirmacion.equalsIgnoreCase("N")) {
        			System.out.println("Operación cancelada");
        		}else {
        			confirmacion = Utilidades.leerCadena(sc, "Opción no válida");
        		}
    		}while (!confirmacion.equalsIgnoreCase("S") && !confirmacion.equalsIgnoreCase("N"));
    	}catch(NumberFormatException e) {
    		System.out.println("Id no válido");
    	}catch(SQLException e) {
    		System.out.println(e.getLocalizedMessage()); 
    	}
    }
    
    private static boolean listarTickets (Connection con) {
    	List<Ticket> tickets = new ArrayList<>();
    	try {
    		tickets = ticketDAO.listarTodos(con);
    		
    		if(tickets.isEmpty()) {
    			System.out.println("No hay tickets");
    			return false;
    		}
    		
    		for(Ticket t : tickets) {
    			System.out.println(t);
    		}
    		
    		return true;
    		
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    		return false;
    	}
    }
}

