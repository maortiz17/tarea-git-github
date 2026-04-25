package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.LineaTicketDAO;
import dao.ProductoDAO;
import dao.TicketDAO;
import entities.LineaTicket;
import entities.Producto;
import entities.Ticket;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		menu();
	}
	
	
	public static void menu() {
		
        ProductoDAO productoDAO = new ProductoDAO();
        TicketDAO ticketDAO = new TicketDAO();
        LineaTicketDAO lineaDAO = new LineaTicketDAO();
		int opcion;
		
		do {
			System.out.println("\n--- TPV ---");
            System.out.println("1. Listar productos");
            System.out.println("2. Nueva venta");
            System.out.println("3. Continuar venta");
            System.out.println("4.Consultar ticket");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            
            opcion = sc.nextInt(); //suponemos que se da una entrada númerica aunque lo ideal sería gestionar las excepciones por error en la entrada de teclado
            
            switch (opcion) {
            case 1:
            	listarProductos(productoDAO);
            	break;
            case 2:
            	nuevaVenta(productoDAO, ticketDAO);
            	break;
            case 3:
            	continuarVenta(productoDAO, ticketDAO, null);
            	break;
            case 4:
            	consultarTicket(ticketDAO, lineaDAO);
            	break;
            }
            
                        
		} while (opcion!=0);
	}
	
	
	/**
	 * Método para mostrar todos los productos disponibles
	 * @param objeto de la clase{@link ProductoDAO}
	 */
	public static void listarProductos(ProductoDAO pDAO) {
		
		List<Producto> lista = pDAO.getAll();
		
		for (Producto p : lista) {
			System.out.println(p);
		}
	}
	
	
	/**
	 * Método para iniciar una nueva venta
	 * @param objeto de la clase {@link ProductoDAO}
	 * @param objeto de la clase {@link TicketDAO}
	 */
	public static void nuevaVenta(ProductoDAO pDAO, TicketDAO tDAO) {
		
		Ticket ticket = new Ticket();
		ticket.setTicketCerrado(false);
		
		while(true) {
			
			System.out.println("Inserte ID del producto (o pulse 0 para terminar): ");
			int id = sc.nextInt();
			
			if (id == 0) break;
			
			Producto p = pDAO.getById(id);
			
			if (p==null) {
				System.out.println("Producto no encontrado");
				break;
			}
			
			System.out.println("Cantidad: ");
			int cantidad = sc.nextInt();
			
			LineaTicket linea = new LineaTicket();
			linea.setCantidad(cantidad);
			linea.setProducto(p);
			linea.setPrecioVenta(p.getPrecio()*cantidad);
			
			ticket.anadirLinea(linea);
		}
		
		
		if (ticket.getLineas().isEmpty()) {
			System.out.println("Venta cancelada (sin producots)");
			return;
		}
		
		System.out.println("Cerrar ticket? (T/F): ");
		String cerrar = sc.next();
		
		ticket.setTicketCerrado(cerrar.equalsIgnoreCase("T"));
		
		int idGenerado = tDAO.insert(ticket);
		
		System.out.println("Ticket guardado con ID: " + idGenerado);
	}
	
	
	/**
	 * Método para continuar una venta ya empezada (tickets no cerrados)
	 * @param objeto de la clase {@link ProductoDAO}
	 * @param objeto de la clase {@link TicketDAO}
	 */
	public static void continuarVenta(ProductoDAO pDAO, TicketDAO tDAO, LineaTicketDAO ltDAO) {
		
		//listar y mostrar tickets abiertos
		List<Ticket> abiertos = tDAO.getTicketsAbiertos();
		
		if (abiertos.isEmpty()) {
			System.out.println("No hay ningún ticket abierto.");
			return;
		}
		
		for (Ticket t : abiertos) {
			System.out.println("Ticket ID: " + t.getTicketId());
		}
		
		
		//elegir el ticket por su ID
		System.out.println("ID del ticket: ");
		int ticketId = sc.nextInt();
		
		
		//listar y mostrar lineas actuales
		List<LineaTicket> lineasActuales = ltDAO.getByTicketId(ticketId);

	    for (LineaTicket l : lineasActuales) {
	        System.out.println(l.getProducto().getNombre() + " x " + l.getCantidad());
	    }
	
		
		//añadir nuevas lineas
		List<LineaTicket> nuevas = new ArrayList<>();
		
		while (true) {
			System.out.println("Inserte ID del producto (o pulse 0 para terminar): ");
			int id = sc.nextInt();
			
			if (id == 0) break;
			
			Producto p = pDAO.getById(id);
			
			if (p==null) {
				System.out.println("Producto no encontrado");
				break;
			}
			
			System.out.println("Cantidad: ");
			int cantidad = sc.nextInt();
			
			LineaTicket linea = new LineaTicket();
			linea.setCantidad(cantidad);
			linea.setProducto(p);
			linea.setPrecioVenta(p.getPrecio()*cantidad);
			
			nuevas.add(linea);
		}
		
		
		//añadir nuevas lineas
		if (!nuevas.isEmpty()) {
			tDAO.addLineas(ticketId, nuevas);
			System.out.println("Lineas añadidas");
			
		} else {
			System.out.println("No se añadió ninguna linea.");
			}
		
		//preguntar para cerrar el ticket
		System.out.print("¿Cerrar ticket? (T/F): ");
	    String cerrar = sc.next();

	    if (cerrar.equalsIgnoreCase("T")) {
	        tDAO.cerrarTicket(ticketId);
	    }
		
	}
	
	
	
	/**
	 * Método para consultar un ticket y advertir si no está cerrado
	 * @param objeto de la clase {@link TicketDAO}
	 * @param objeto de la clase {@link LineaTicketDAO}
	 */
	public static void consultarTicket(TicketDAO tDAO, LineaTicketDAO ltDAO) {
		
		System.out.println("ID del ticket: ");
		int id = sc.nextInt();
		
		//obtener ticket
		Ticket t = tDAO.getById(id);
		
		if (t==null) {
			System.out.println("Ticket no encontrado");
	        return;
		}
		
		//mostrar datos del ticket
		System.out.println("Ticket ID: " + t.getTicketId());
	    System.out.println("Fecha: " + t.getFechaHora());
	    System.out.println("Cerrado: " + (t.isTicketCerrado() ? "Sí" : "No"));
	    
	    if (!t.isTicketCerrado()) {
	        System.out.println("Advertencia: Ticket aún abierto");
	    }
		    
	    //obtener lineas
	    List<LineaTicket> lineas = ltDAO.getByTicketId(id);
	    
	    //mostrar lineas y precio final
	    double precioFinal = 0;
	    
	    for (LineaTicket l : lineas) {
	    	double precioLinea = l.getPrecioVenta() * l.getCantidad();
	    	precioFinal += precioLinea;
	    	
	    	System.out.println(l.getProducto().getNombre() + " x " + l.getCantidad() + " = " + precioLinea);
	    }
	    
	    System.out.println("\nTotal: " + precioFinal);
	}
	
	
	/**
	 * Método para borrar un ticket identificado por su ID. Sólo si está cerrado
	 * @param objeto de la clase {@return TicketDAO}
	 */
	public static void devolverCompra(TicketDAO tDAO) {
		
		System.out.println("ID del ticket: ");
		int id = sc.nextInt();
		
		//obtener ticket
		Ticket t = tDAO.getById(id);
		
		if (t==null) {
			System.out.println("Ticket no encontrado");
	        return;
		}
		
		if (t.isTicketCerrado()) {
			tDAO.delete(id);
		} else {
			System.out.println("No se puede borrar el ticket hasta estar cerrado.");
		}
		
		System.out.println("Ticket borrado correctamente");
	}
}
