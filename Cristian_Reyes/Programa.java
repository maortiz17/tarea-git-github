package tarea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import tarea.dao.ILineaTicketDao;
import tarea.dao.IProductoDAO;
import tarea.dao.ITicketDao;
import tarea.dao.impl.LineaTicketDaoOracle;
import tarea.dao.impl.ProductoDAOOracle;
import tarea.dao.impl.TicketDaoOracle;
import tarea.entities.LineaTicket;
import tarea.entities.Producto;
import tarea.entities.Ticket;

public class Programa {
    private static Scanner sc = new Scanner(System.in);
    private static IProductoDAO productoDAO = new ProductoDAOOracle();
    private static ITicketDao ticketDao= new TicketDaoOracle();
    private static ILineaTicketDao LineaDao= new LineaTicketDaoOracle();
    // Utiliza aquí el nombre del esquema y contraseña que tú hayas utilizado
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
            pintarOpciones();
            opcion = Utilidades.leerEntero(sc, 0, 2, "Seleccione una opción: ");
            switch(opcion){
                case 1:
                    subMenuProductos(con);;
                    break;
                case 2:
                    subMenuVentas(con);
                    break;
                case 0:
                    System.out.println("Hasta pronto");
                    break;
              
            }
        }while (opcion != 0);
    }
    
    
    private static void subMenuProductos(Connection con)
    {
    	int opcion;
        do{
            pintarOpcionesProducto();
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
                    System.out.println("Volviendo al menu Principal...");
                    break;
            }
        }while (opcion != 0);
    	
    }
    
    private static void subMenuVentas(Connection con)
    {
    	int opcion;
        do{
            pintarOpcionesVentas();
            opcion = Utilidades.leerEntero(sc, 0, 5, "Seleccione una opción: ");
            switch(opcion){
                case 1:
                    inicarVenta(con);
                    break;
                case 2:
                	continuarVenta(con);
                    
                    break;
                case 3:
                	consultarTicket(con);
                    
                    break;
                case 4: devolverTicket(con);
                    
                    break;
             
                case 0:
                    System.out.println("Volviendo al menu principal");
                    break;
            }
        }while (opcion != 0);
    	
    }
    
    private static void devolverTicket(Connection con) {
		
    	int id= Utilidades.leerEntero(sc, 0, 100, "Introduce el id del Ticket: ");
    	
    	try {
			boolean borrado=ticketDao.borrar(con, id);
			
			if(borrado)
				System.out.println(">> Ticket Borrado y sus lineas");
			else {
				System.out.println("No se enconto ningun Ticket con ese ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
	}

	private static void consultarTicket(Connection con) {
		// TODO Auto-generated method stub
		Ticket t;
		int id;
		List<LineaTicket> lineas;
		
		try {
			
			id= Utilidades.leerEntero(sc, 0, 100, "Introduce el id del Ticket: ");
			t=ticketDao.buscarId(con, id);
			
			lineas=LineaDao.listarPorTicket(con, id);
			
			System.out.println(t);
			System.out.println("--".repeat(20));
			for(LineaTicket l: lineas )
			{
				System.out.println(l);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static void continuarVenta(Connection con) {
		Ticket t = new Ticket();
		
		List<Ticket> tickets;
		List<LineaTicket> lineas;
		
		try {
			
			tickets=ticketDao.listarAbierta(con);
			
			for(Ticket tk: tickets)
			{
				System.out.println(tk);
			}
			int id = Utilidades.leerEntero(sc,0, tickets.size(), "Elige un id: ");
			
			t=ticketDao.buscarId(con, id);
			
			lineas = LineaDao.listarPorTicket(con, id);
			
			t.setlTicket(lineas);
			
			venta(con, t);
			
    		
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		List<LineaTicket> lineasTicket= t.getlTicket();
	}

	private static void inicarVenta(Connection con) {
		
    	Ticket ticket = new Ticket();
    	
    	System.out.println("-----Nueva Venta-----");
    	
    	
    	venta(con, ticket);
    	
    	
		
	}
	
	
	private static void venta(Connection con, Ticket ticket)
	{
		Boolean añadiendo = true;
    	
    	while(añadiendo)
    	{
    		long idLong = Utilidades.leerLongPositivo(sc, "Introduzca ID del producto (0 para cobrar): ");
    		
    		if(idLong==0)
    		{
    			añadiendo=false;
    		}else {
				
    			
    			try {
					Producto p = productoDAO.buscar(con, idLong);
					
					if(p!=null)
					{
					int cantidad = Utilidades.leerEntero(sc, 0, 100, "Introduce Cantidad(0-100):");
					LineaTicket lTicket= new LineaTicket(cantidad, p.getPrecio(), p);
					
					ticket.getlTicket().add(lTicket);
					
					System.out.println("Añadido: "+p.getNombre()+" x "+ cantidad);
					
					}else 
					{
						System.out.println("El ID no existe");
					}
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Error al buscar el producto: " +e.getMessage());
				}
    			
    			
    			
			}
    		
    	}
    	
    	if(ticket.getlTicket().isEmpty())
    	{
    		System.out.println("Venta Cancelada, no hay productos en la lista");
    		return;
    	}
    	
    	try {
			con.setAutoCommit(false);
			int idGenerado = ticket.getId();
			if(ticket.getId()==0) {
			idGenerado= ticketDao.insertar(con, ticket);
			}
			
			for(LineaTicket lt: ticket.getlTicket())
			{
				if(lt.isEsNueva())
				{
				lt.setIdTicket(idGenerado);
				LineaDao.insertar(con, lt);
				lt.setEsNueva(false);
				}
			}
			con.commit();
			System.out.println(">> Venta Realizada");
		} catch (SQLException e) {
			
			try {
				con.rollback();
				System.out.println("Venta Abortada: Error al añadir la venta");
				
			} catch (SQLException e2) {
				System.out.println("Error al hacer Rollback");
			}
			System.out.println("Detalle: "+ e.getMessage());
		}finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e2) {
				System.out.println(e2.getMessage());
			}
		}
    	
	}

	private static void pintarOpciones()
    {
    	System.out.println("\n--- Menu ---");
        System.out.println("1. Mantenimiento de Productos");
        System.out.println("2. Gestion de Ventas");
        System.out.println("0. Salir");
    }
    
    private static void pintarOpcionesVentas()
    {
    	System.out.println("\n--- GESTIÓN DE VENTAS ---");
        System.out.println("1. Iniciar nueva Venta");
        System.out.println("2. Continuar Venta");
        System.out.println("3. Consultar Ticket");
        System.out.println("4. Devolver Compra");
        System.out.println("0. Volver al menu Principal");
    }
    
    

    private static void pintarOpcionesProducto(){
        System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
        System.out.println("1. Buscar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Crear producto");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Borrar producto");
        System.out.println("0. Volver al menu Principal");
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

