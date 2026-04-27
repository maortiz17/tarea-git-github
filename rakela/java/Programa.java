import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.ILineaTicketDAO;
import dao.IProductoDAO;
import dao.ITicketDAO;
import dao.LineaTicketDAO;
import dao.ProductoDAO;
import dao.TicketDAO;
import entities.LineaTicket;
import entities.Producto;
import entities.Ticket;

public class Programa {

	private static Scanner sc = new Scanner(System.in);
    private static IProductoDAO productoDAO = new ProductoDAO();
    private static ITicketDAO ticketDAO = new TicketDAO();
    private static ILineaTicketDAO lineaTicketDAO = new LineaTicketDAO();
    
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	private static final String USR = "TPV";
	private static final String PWD = "TPV";
    
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
            pintarOpciones();
            opcion = Utilidades.leerEntero(sc, 0, 6, "Seleccione una opción: ");
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
                case 6:
                	menuVentas(con);
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
        System.out.println("6. GESTIÓN DE VENTAS");
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
    
    private static void menuVentas(Connection con) {
    	int opcion;
    	do {
    		System.out.println("SUBMENÚ GESTIÓN VENTAS");
    		System.out.println("1. Nueva venta");
    		System.out.println("2.Continuar la venta");
    		System.out.println("3. Consultar ticket");
    		System.out.println("4. Devolver compra");
    		System.out.println("0. VOLVER");
    		opcion = Utilidades.leerEntero(sc, 0, 4, "seleccione opcion: ");
    		
    		switch (opcion) {
    		
    		case 1: iniciarVenta(con); break;
    		case 2: continuarVenta(con); break;
    		case 3: consultarTicket(con); break;
    		case 4: devolverCompra(con); break;
    		case 0: break;
    			
    		}
    	} while (opcion !=0);
    }
    
    
    private static void iniciarVenta(Connection con) {
    	Ticket ticket = new Ticket();
    	boolean añadir = true;
    	
    	while(añadir) {
    		long idProd = Utilidades.leerEntero(sc,0, 999999, "ID del Producto a vender (0 para salir): ");
    		if (idProd == 0) {
    			añadir = false;
    		} else {
    			try {
    				Producto p = productoDAO.buscar(con,  idProd);
    				if (p != null) {
    					int cant = Utilidades.leerEntero(sc, 1, 100, "Cantidad: ");
    					LineaTicket linea = new LineaTicket(cant, p.getPrecio(), p);
    					ticket.getLineas().add(linea);
    				} else {
    					System.out.println("Ese producto no existe");
    				}
    			} catch (SQLException e) {
    				System.out.println("Error: " + e.getMessage());
    			}
    			
    		}
    	}
    	
    	if (!ticket.getLineas().isEmpty()) {
    		String cierreTicket = Utilidades.leerCadena(sc, "¿Se cierra la venta? S/N");
    		if(cierreTicket.equalsIgnoreCase("S")) {
    			ticket.setTicketCerrado("T");
    		}
    		
    		guardarVentaCompleta(con, ticket);
    	}
    }
    
    private static void guardarVentaCompleta(Connection con, Ticket ticket) {
    	
    	try {  
    		con.setAutoCommit(false);
    		long idTicket = ticketDAO.insertar(con, ticket);
    		for (LineaTicket linea : ticket.getLineas()) {
    			lineaTicketDAO.insertar(con,  linea, idTicket);
    		}
    		
    		con.commit(); // salió bien
    		
    	} catch (SQLException e) {
    		try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		System.out.println("Error. Venta anulada");
    	} finally {
    		try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    } 
    
    
    private static void continuarVenta(Connection con) {
        try {
            long idTicket = Utilidades.leerLongPositivo(sc, "ID de ticket pendiente: ");
            Ticket ticket = ticketDAO.buscarPorId(con, idTicket);

            if (ticket == null || ticket.getTicketCerrado().equals("T")) {
                System.out.println("El ticket no existe o ya está cerrado.");
                return;
            }

            boolean mas = true;
            while(mas) {
                long idProd = Utilidades.leerEntero(sc,0, 999999, "ID Producto (0 para parar): ");
                if(idProd == 0) {
                    mas = false;
                } else {
                    Producto p = productoDAO.buscar(con, idProd);
                    if (p != null) {
                        int cant = Utilidades.leerEntero(sc, 1, 10, "Cant: ");
                        LineaTicket nuevaLinea = new LineaTicket(cant, p.getPrecio(), p);
                        
                        // grabado en la bd directo
                        lineaTicketDAO.insertar(con, nuevaLinea, idTicket);
                        System.out.println("Producto añadido.");
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                }
            }

            String cerrar = Utilidades.leerCadena(sc, "¿Cerrar ticket ahora? S/N: ");
            if(cerrar.equalsIgnoreCase("S")) {
                ticketDAO.cerrarTicket(con, idTicket, "T");
                System.out.println("Ticket cerrado definitivamente.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void consultarTicket(Connection con) {
    	
    	long id = Utilidades.leerLongPositivo(sc, "ID del ticket a consultar: ");
    	
    	try {
    		Ticket t = ticketDAO.buscarPorId(con, id);
    		if(t != null) {
    			List<LineaTicket> lineas = lineaTicketDAO.listarPorTicket(con, id);
    			t.setLineas(lineas);
    			
    			System.out.println(t);
    			
    		} else {
    			System.out.println("Ticket no existe");
    		}
    	} catch (SQLException e) {
    		System.out.println("Error: " + e.getMessage());
    	}
    }
    
    private static void devolverCompra(Connection con) {
    	
    	long id = Utilidades.leerLongPositivo(sc, "ID del ticket a devolver");
    	try {
    		if (ticketDAO.borrar(con, id)) {
				System.out.println("borrado el ticket!!!");
			} else {
				System.out.println("no existe ese ticket");
			}
    	} catch (SQLException e) {
    		System.out.println("Error: " + e.getMessage());
    	}
    }
     
    
}