package tienda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Programa {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		final String ELIGE = "Elige opción";
		final int MIN = 1;
		final int MAX = 999999999;
		final String[] MENU_PRIN = { "0- Salir", "1- Mantenimiento de productos", "2- Gestión de ventas" };
		final String[] MENU_PRO = { "1- Añdair producto", "2- borrar producto", "3- lista de productos",
				"4- Elegir producto", "5- Actualizar producto" };
		final String[] MENU_VENTAS = { "1- Iniciar ventas", "2- continuar venta", "3- Consultar venta",
				"4- Devolver compra", "5- volver al menu" };
		String user = "java";
		String pas = "oracle123";
		String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";

		Connection con = null;

		try {
			con = DriverManager.getConnection(url, user, pas);
			con.setAutoCommit(false);
			ProductoDAO pDAO = new ProductoDAO(con);
			TicketDAO tDAO = new TicketDAO(con);
			LineaTicketDAO ltDAO = new LineaTicketDAO(con);

			boolean salir1 = false;
			

			while (!salir1) {
				leerMenu(MENU_PRIN);

				switch (utiles.leerNumLimites(ELIGE, sc, 0, MENU_PRIN.length - 1)) {
				case 0:
					salir1 = true;
					System.out.println("Fin");
					break;
				case 1:
					//// Para los productos
					leerMenu(MENU_PRO);
					switch (utiles.leerNumLimites(ELIGE, sc, MIN, MENU_PRO.length)) {
					case 1:
						boolean b1 = pDAO.insert(utiles.leerString("Barcode:", sc), utiles.leerString("Nombre:", sc),
								utiles.leerDouLimites("Precio:", sc, MIN, MAX));
						if (!b1)
							System.err.println("Error");
						break;
					case 2:
						boolean b2 = pDAO.delete(utiles.leerNum("Id", sc));
						if (!b2)
							System.err.println("Error");
						break;
					case 3:
						List<Producto> productos = pDAO.getAll();
						for (Producto p : productos) {
							System.out.println(p.toString());
						}
						break;
					case 4:
						System.out.println(pDAO.getById(utiles.leerNumLimites("Id", sc, MIN, MAX)).toString());
						break;
					case 5:
						boolean b3 = pDAO.update(utiles.leerNum("Id:", sc), utiles.leerString("Barcode:", sc),
								utiles.leerString("Nombre:", sc), utiles.leerDouLimites("Precio:", sc, MIN, MAX));
						if (!b3)
							System.err.println("Error");
						break;

					}
					break;
				case 2:
				    boolean salir2 = false;
				    while (!salir2) {

				        leerMenu(MENU_VENTAS);

				        switch (utiles.leerNumLimites(ELIGE, sc, MIN, MENU_VENTAS.length - 1)) {


				        case 1:

				            Ticket t = new Ticket(Timestamp.valueOf(LocalDateTime.now()), false);

				            if (!tDAO.insert(t)) {
				                System.err.println("Error creando ticket");
				                break;
				            }

				            int idTicket = tDAO.getLastId();

				            boolean masLineas = true;
				            while (masLineas) {
				                int idProd = utiles.leerNum("ID producto:", sc);
				                int cant = utiles.leerNum("Cantidad:", sc);

				                Producto prod = pDAO.getById(idProd);
				                if (prod == null) {
				                    System.err.println("Producto no existe");
				                    continue;
				                }

				                LineaTicket lt = new LineaTicket(0, cant, prod.getPrecio(), idProd, idTicket);
				                ltDAO.insert(lt);

				                if(utiles.leerNumLimites("1 seguir, 2 salir", sc, MIN, 2) == 2) masLineas = false;				            }

				            if(utiles.leerNumLimites("¿Cerrar tiquet? 1- si, 2- no", sc, 1,2) == 1)tDAO.cerrarTicket(idTicket);
				            break;

				        case 2:
				            int idCont = utiles.leerNum("ID ticket:", sc);

				            Ticket tI = tDAO.getById(idCont);
				            if (tI == null) {
				                System.err.println("Ticket no existe");
				                break;
				            }
				            if (tI.isTicketcerrado()) {
				                System.err.println("El ticket ya está cerrado");
				                break;
				            }

				            boolean seguir = true;
				            while (seguir) {
				                int idProd = utiles.leerNum("ID producto:", sc);
				                int cant = utiles.leerNum("Cantidad:", sc);

				                Producto prod = pDAO.getById(idProd);
				                if (prod == null) {
				                    System.err.println("Producto no existe");
				                    continue;
				                }

				                LineaTicket lt = new LineaTicket(0, cant, prod.getPrecio(), idProd, idCont);
				                ltDAO.insert(lt);

				                if(utiles.leerNumLimites("1 seguir, 2 salir", sc, MIN, 2) == 2) seguir = false;
				            }

				            tDAO.cerrarTicket(idCont);
				            System.out.println("Ticket cerrado.");
				            break;


				        case 3:
				            
				            List <Ticket> ticketCerrados = tDAO.getAllCerrado("F");
				            for(Ticket tc : ticketCerrados) {System.out.println(tc.toString());}
				            int idCons = utiles.leerNum("Elige id de los tiquet abiertos:", sc);
				            Ticket tc = tDAO.getById(idCons);
				            if (tc == null) {
				                System.err.println("Ticket no existente");
				                break;
				            }

				            System.out.println(tc);

				            List<LineaTicket> lineas = tDAO.getAllLineas(tc);
				            for (LineaTicket l : lineas) {
				                System.out.println(l);
				            }

				            
				            break;

		
				        case 4:

				            int idDev = utiles.leerNum("ID:", sc);

				            if (tDAO.delete(idDev)) {
				                System.out.println("Ticket devuelto correctamente.");
				            } else {
				                System.err.println("Error devolviendo ticket.");
				            }
				            break;

				        case 5:
				            salir2 = true;
				            break;
				        }
				    }
				    break;


				}
			}
			con.commit();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					System.err.println("Error en rollback: " + ex.getMessage());
				}
			}
		} finally {
			if (con != null) {
				try {
					con.setAutoCommit(true);
				} catch (SQLException ex) {
					System.err.println("Error restaurando autoCommit: " + ex.getMessage());
				}
				try {
					con.close();
				} catch (SQLException ex) {
					System.err.println("Error cerrando conexión: " + ex.getMessage());
				}
			}
		}
	}

	public static <T> void leerMenu(T[] arr) {
		for (T a : arr) {
			System.out.println(a);
		}
	}
}
