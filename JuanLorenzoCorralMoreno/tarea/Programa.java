package tarea;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import tarea.dao.ILineaTicketDAO;
import tarea.dao.IProductoDAO;
import tarea.dao.ITicketDAO;
import tarea.dao.impl.LineaTicketDAO;
import tarea.dao.impl.ProductoDAOOracle;
import tarea.dao.impl.TicketDAO;
import tarea.entities.LineaTicket;
import tarea.entities.Producto;
import tarea.entities.Ticket;

public class Programa {
	private static Scanner sc = new Scanner(System.in);
	private static IProductoDAO productoDAO = new ProductoDAOOracle();
	private static ILineaTicketDAO lineaTicketDAO = new LineaTicketDAO();
	private static ITicketDAO ticketDAO = new TicketDAO();
	// Utiliza aquí el nombre del esquema y contraseña que tú hayas utilizado
	private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	private static final String USR = "java";
	private static final String PWD = "oracle123";

	public static void main(String[] args) {

		try (Connection con = DriverManager.getConnection(URL, USR, PWD)) {
			System.out.println("Conexión establecida");
			System.out.println("Esquema actual: " + con.getSchema());
			menu(con);

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			sc.close();
		}
	}

	private static void subMenu1(Connection con) {
		int opcion;
		do {
			pintarOpcionesSubMenu1();
			opcion = Utilidades.leerEntero(sc, 0, 5, "Seleccione una opción: ");
			switch (opcion) {
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
				System.out.println("Volviendo al menu principal");
				break;
			}
		} while (opcion != 0);
	}

	private static void subMenu2(Connection con) {
		int opcion;
		do {
			pintarOpcionesSubMenu2();
			opcion = Utilidades.leerEntero(sc, 1, 5, "Seleccione una opción: ");
			switch (opcion) {
			case 1:
				iniciarNuevaVenta(con);
				break;
			case 2:
				listarTicketAbierto(con);
				continuarVenta(con);
				break;
			case 3:
				imprimirTicketById(con,
						Utilidades.leerLongPositivo(sc, "Introduzca el id del ticket que desea imprimir: "));
				break;
			case 4:
				borrarTicket(con);
				break;
			case 5:
				System.out.println("Volviendo al menu principal");
				break;

			}
		} while (opcion != 5);
	}

	private static void menu(Connection con) {
		int opcion;
		do {
			pintarMenu();
			opcion = Utilidades.leerEntero(sc, 0, 2, "Seleccione una opción: ");
			switch (opcion) {
			case 1:
				subMenu1(con);
				break;
			case 2:
				subMenu2(con);
				break;
			case 0:
				System.out.println("GRACIAS POR USAR LA APLICACION");
				break;
			}
		} while (opcion != 0);
	}

	private static void pintarOpcionesSubMenu1() {
		System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
		System.out.println("1. Buscar producto");
		System.out.println("2. Listar productos");
		System.out.println("3. Crear producto");
		System.out.println("4. Actualizar producto");
		System.out.println("5. Borrar producto");
		System.out.println("0. Volver al menu principal");
	}

	private static void pintarOpcionesSubMenu2() {
		System.out.println("\n--- GESTION DE TICKETS ---");
		System.out.println("1. Iniciar nueva venta");
		System.out.println("2. Continuar venta");
		System.out.println("3. Consultar ticket");
		System.out.println("4. Devolver compra");
		System.out.println("5. Volver al menu principal");
	}

	private static void pintarMenu() {
		System.out.println("\n Bienvenido al programa de gestion de la tienda");
		System.out.println("1. GESTION DE PRODUCTOS");
		System.out.println("2. GESTION DE TICKETS");
		System.out.println("0. SALIR DE LA APLICACION");
	}

	private static void buscarProducto(Connection con) {
		long id = Utilidades.leerLongPositivo(sc, "Introduzca id del producto: ");
		try {
			Producto p = productoDAO.buscar(con, id);
			if (p != null) {
				System.out.println(p);
				return;
			}
			System.out.println("Producto no encontrado");
		} catch (SQLException e) {
			System.out.println("Error al leer de la base de datos: " + e.getMessage());
		}
	}

	private static void listarProductos(Connection con) {
		try {
			List<Producto> productos = productoDAO.listar(con);
			if (productos.isEmpty()) {
				System.out.println("No hay productos en la base de datos");
				return;
			}
			for (Producto p : productos) {
				System.out.println(p);
			}
		} catch (SQLException e) {
			System.out.println("Error al leer de la base de datos: " + e.getMessage());
		}
	}

	private static void crearProducto(Connection con) {
		String barcode = Utilidades.leerCadena(sc, "Barcode del producto: ");
		String nombre = Utilidades.leerCadena(sc, "Nombre del producto: ");
		Double precio = Utilidades.leerDoublePositivo(sc, "Precio del producto: ");
		try {
			Producto p = productoDAO.crear(con, barcode, nombre, precio);
			if (p != null) {
				System.out.println("Producto creado correctamente: " + p);
				return;
			}
			System.out.println("No se pudo crear el producto");
		} catch (SQLException e) {
			System.out.println("Error al crear el producto: " + e.getMessage());
		}
	}

	private static void modificarProducto(Connection con) {
		long id = Utilidades.leerLongPositivo(sc, "Introduzca id del producto: ");

		try {
			Producto p = productoDAO.buscar(con, id);
			if (p == null) {
				System.out.println("Producto no encontrado");
				return;
			}
			String barcode = Utilidades.leerCadena(sc, "Barcode del producto(" + p.getBarcode() + "): ");
			String nombre = Utilidades.leerCadena(sc, "Nombre del producto(" + p.getNombre() + "): ");
			Double precio = Utilidades.leerDoubleOpcional(sc, "Precio del producto(" + p.getPrecio() + "): ");
			if (barcode.isEmpty()) {
				barcode = p.getBarcode();
			}
			if (nombre.isEmpty()) {
				nombre = p.getNombre();
			}
			if (precio == null) {
				precio = p.getPrecio();
			}
			p = productoDAO.modificar(con, id, barcode, nombre, precio);
			if (p != null) {
				System.out.println("Producto modificado correctamente: " + p);
				return;
			}
			System.out.println("Producto no encontrado");
		} catch (SQLException e) {
			System.out.println("Error al modificar el producto: " + e.getMessage());
		}
	}

	private static void eliminarProducto(Connection con) {
		long id = Utilidades.leerLongPositivo(sc, "Introduzca id del producto: ");
		try {
			// Modificacion para evitar eliminar productos si se encuentran en algun ticket
			// Recibimos una list con los diferentes id de lineaTicket si el id se encuentra
			// en esta lista
			// imprimimos un mensaje de error y volvemos al menu
			if (lineaTicketDAO.listarId(con).contains(id)) {
				System.out.println(
						"El producto que esta tratando de eliminar se encuentra actualmente en un ticket asi que no es posible eliminarlo");
				return;
			}
			if (productoDAO.borrar(con, id)) {
				System.out.println("Producto eliminado correctamente");
				return;
			}
			System.out.println("Producto no encontrado");
		} catch (SQLException e) {
			System.out.println("Error al leer de la base de datos: " + e.getMessage());
		}
	}

//--------------------------------------------------------------------------------------------
	/*
	 * Iniciar nueva venta crea un nuevo ticket en la bd con el campo ticket Cerrado
	 * en "F", llamamos a los metodos anadirProductosLineaTicket y estaCerrado
	 */
	private static void iniciarNuevaVenta(Connection con) {
		String ticketCerrado = "F";
		Ticket t;
		List<LineaTicket> lineaTicket = new ArrayList<>();
		try {
			con.setAutoCommit(false);
			t = ticketDAO.crear(con, ticketCerrado, lineaTicket);
			anadirProductosLineaTicket(con, t);
			estaCerrado(con, t);
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Error al insertar el nuevo ticket operacion anulada");

		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/*
	 * anadirLineacompra recibe como parametro un producto y un ticket y añade a la
	 * bd la linea con las referencias al id de ambos objetos
	 */

	private static void anadirLineaCompra(Connection con, Producto p, Ticket t) throws SQLException {
		long cantidad;
		double precioVenta;
		long productoId;
		long ticketId;
		cantidad = Utilidades.leerLongPositivo(sc, "Introduzca la cantidad que desea añadir a la cesta: ");
		precioVenta = p.getPrecio();
		productoId = p.getId();
		ticketId = t.getId();
		lineaTicketDAO.crear(con, cantidad, precioVenta, productoId, ticketId);
	}

	/*
	 * Metodo que mediante consulta recibe solo los tickets abiertos y los imprime
	 */
	private static void listarTicketAbierto(Connection con) {
		try {
			TreeMap<Long, Producto> tm = productosActual(con);

			System.out.println("Estos son los tickets sobre con una venta abierta");
			List<Ticket> tickets = ticketDAO.ticketsAbiertos(con);
			for (Ticket ticket : tickets) {
				imprimirTicket(con, ticket, tm);
			}
		} catch (SQLException e) {
			System.out.println("Fallo al listar: " + e.getMessage());
		}
	}

	/*
	 * Continuar venta realiza primero una consulta a la bd para recuperar un ticket
	 * si existey lama al metodo anadirProductos para añadir nuevos productos y
	 * estaCerrado para cambiar el estado del ticket;
	 */
	private static void continuarVenta(Connection con) {
		try {
			con.setAutoCommit(false);

			Long id = Utilidades.leerLongPositivo(sc,
					"Introduzca el id del ticket sobre el que quiere continuar la venta: ");
			Ticket t = ticketDAO.consultarTicket(con, id);
			if (t != null) {
				if (!t.estado()) {
					anadirProductosLineaTicket(con, t);
					estaCerrado(con, t);
				} else {
					System.out.println("El ticket esta cerrado");
				}
			} else {
				System.out.println("El ticket no existe");
			}
			con.commit();
		} catch (SQLException e) {
			System.out.println("Fallo al realizar la inserccion");
			System.out.println(e.getMessage());

			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					con.setAutoCommit(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	/*
	 * Metodo para añadir productos a la bd con la referencia de un ticket, cuando
	 * se deja vacio el campo sale del bucle
	 */

	private static void anadirProductosLineaTicket(Connection con, Ticket t) throws SQLException {
		try {
			
		
		String idProducto;
		do {
			idProducto = Utilidades.leerCadena(sc, "Introduzca el codigo del producto: ");
			if (!idProducto.isEmpty()) {
				Producto p = productoDAO.buscar(con, Long.valueOf(idProducto));
				if (p == null) {
					System.out.println("El producto que quiere listar no se encuentra en la tienda");
				} else {
					anadirLineaCompra(con, p, t);
				}
			}
		} while (!idProducto.isEmpty());
		} catch (NumberFormatException e) {
			System.out.println("Introduzca un numero valido o deje el campo en blanco");
		}
	}

	/*
	 * Metodo para cambiar el estado del ticket en el campo estaCerrado a T o F.
	 */
	private static void estaCerrado(Connection con, Ticket t) throws SQLException {
		String ticketCerrado;
		do {
			ticketCerrado = Utilidades.leerCadena(sc,
					"Introduzca 'T' si desea dejar el ticket abierto o 'F'si desea dejar el ticket cerrado: ");
			if (ticketCerrado.equals("T")) {
				ticketDAO.cambiarTicketCerrado(con, t.getId(), ticketCerrado);
			}
		} while (!ticketCerrado.equalsIgnoreCase("T") && !ticketCerrado.equalsIgnoreCase("F"));
	}

	/*
	 * Metodo de impresion de tickets recibe un un treemap con los articulos de la
	 * tienda que usaremos como parte de la impresion del ticket
	 */
	private static void imprimirTicket(Connection con, Ticket t, TreeMap<Long, Producto> tm) throws SQLException {
		double total = 0;
		System.out.print("*".repeat(82));
		System.out.printf("\n*\t\t TICKET: %d Hora: %tT Fecha:%tF\t\t\t*\n", t.getId(), t.getFechaHora(),
				t.getFechaHora());
		System.out.println("*".repeat(82));
		for (LineaTicket lt : t.getLineaTicket()) {
			if (lt.getProductoId() == 0) {
				System.out.println("EL ticket esta abierto pero aun no se ha añadido productos al carrito");
				return;
			}
			System.out.printf("*\t%s", tm.get(lt.getProductoId()).getNombre());
			System.out.println(lt);
			total += lt.getPrecioVenta() * lt.getCantidad();
		}
		System.out.println("*".repeat(82));
		System.out.printf("Total: %s%.2f\n\n", "\t".repeat(9), total);
	}

	/*
	 * Metodo que devuelve en un map actualizado los productos de la tienda para
	 * comprobaciones o toma de datos
	 */
	private static TreeMap<Long, Producto> productosActual(Connection con) throws SQLException {
		TreeMap<Long, Producto> tm = new TreeMap<>();
		for (Producto p : productoDAO.listar(con)) {
			tm.put(p.getId(), p);
		}
		return tm;
	}

	/*
	 * Metodo para imprimir un ticket solicitando el id del ticket que quieres leer
	 */
	private static void imprimirTicketById(Connection con, long id) {
		try {

			Ticket t = ticketDAO.consultarTicket(con, id);
			if (t == null) {
				System.out.println("El tiquet no se encuentra en la base de datos");
				return;
			}
			TreeMap<Long, Producto> tm = productosActual(con);

			String estado = t.estado() ? "Cerrado" : "Abierto";
			System.out.printf("\nEl ticket se encuentra actualmente: %s \n", estado);
			imprimirTicket(con, t, tm);
		} catch (SQLException e) {
			System.out.println("Ticket no localizado:" + e.getMessage());
		}
	}

	/*
	 * Pedimos un id de de ticket en caso de estar cerrado procedemos a imprimirlo y
	 * borrarlo, en caso de estar abierto no lo borramos.
	 */
	private static void borrarTicket(Connection con) {
		try {
			long id = Utilidades.leerLongPositivo(sc, "Introduzca el numero de ticket que desea borrar : ");
			if (ticketDAO.consultarTicket(con, id).estado()) {
				imprimirTicketById(con, id);
				ticketDAO.borrarTicket(con, id);
				System.out.println("Ticket borrado con exito");
			} else {
				System.out.println("El ticket se encuentra abierto cierrelo para poder borrarlo");
			}

		} catch (SQLException e) {
			System.out.println("Fallo al borrar el ticket : " + e.getMessage());
		}
	}
}
