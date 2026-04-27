package tarea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import tarea.dao.IProductoDAO;
import tarea.dao.impl.ProductoDAOOracle;
import tarea.entities.Producto;

public class Programa {
	private static Scanner sc = new Scanner(System.in);
	private static IProductoDAO productoDAO = new ProductoDAOOracle();
	// Utiliza aquí el nombre del esquema y contraseña que tú hayas utilizado
	private static final String URL = "jdbc:oracle:thin:@localhost:50000/XEPDB1";
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

	private static void menu(Connection con) {
		int opcion;
		do {
			pintarMenu();
			opcion = Utilidades.leerEntero(sc, 0, 2, "Seleccione una opción: ");
			switch (opcion) {
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
		} while (opcion != 0);
	}

	private static void menuProductos(Connection con) {
		int opcion;
		do {
			pintarOpciones();
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
				menu(con);
				break;
			}
		} while (opcion != 0);
	}

	private static void menuVentas(Connection con) {
		int opcion;
		do {
			pintarVentas();
			opcion = Utilidades.leerEntero(sc, 0, 4, "Seleccione una opción: ");
			switch (opcion) {
			case 1:

				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;
			case 0:
				menu(con);
				break;
			}
		} while (opcion != 0);
	}

	private static void pintarOpciones() {
		System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
		System.out.println("1. Buscar producto");
		System.out.println("2. Listar productos");
		System.out.println("3. Crear producto");
		System.out.println("4. Actualizar producto");
		System.out.println("5. Borrar producto");
		System.out.println("0. Volver al menú principal");
	}

	private static void pintarVentas() {

		System.out.println("\n--- GESTIÓN DE VENTAS ---");
		System.out.println("1. Iniciar nueva venta");
		System.out.println("2. Continuar venta");
		System.out.println("3. Consultar ticket");
		System.out.println("4. Devolver compra");
		System.out.println("0. Volver al menú principal");

	}

	private static void pintarMenu() {
		System.out.println("\n--- MENÚ PRINCIPAL ---");
		System.out.println("1. Gestion de productos");
		System.out.println("2. Gestión de ventas");
		System.out.println("0. Salir");
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
			if (productoDAO.borrar(con, id)) {
				System.out.println("Producto eliminado correctamente");
				return;
			}
			System.out.println("Producto no encontrado");
		} catch (SQLException e) {
			System.out.println("Error al leer de la base de datos: " + e.getMessage());
		}
	}
}
