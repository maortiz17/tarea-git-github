package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.LineaTicket;
import entities.Producto;
import util.ConexionDB;

public class LineaTicketDAO implements InterfazLineaTicketDAO{

	/**
	 * Método para insertar una linea de ticket en la tabla LineaTicket de la BD
	 * @param objeto de la clase {@link LineaTicket}
	 */
	@Override
	public void insert(LineaTicket linea) {

		String sql = """
				INSERT INTO LINEATICKET (CANTIDAD, PRECIOVENTA, PRODUCTO_ID, TICKET_ID)
				VALUES (?, ?, ?, ?)
				""";
		
		try (Connection con = ConexionDB.getConnection()) {
			
			PreparedStatement ps = con.prepareStatement(sql); {
				ps.setInt(1, linea.getCantidad());
				ps.setDouble(2, linea.getPrecioVenta());
				ps.setInt(3, linea.getProducto().getId());
				ps.setInt(4, linea.getTicket().getTicketId());
				
				ps.executeUpdate();
				System.out.println("Línea de ticket insertada correctamente");
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	
	/**
	 * Método para obtener las lineas de un ticket basado en su ID
	 * @return lista con todas las lineas
	 */
	@Override
	public List<LineaTicket> getByTicketId(int ticketId) {
		
		List<LineaTicket> lista = new ArrayList<>();
		
		String sql = """
				SELECT LT.ID, LT.CANTIDAD, LT.PRECIOVENTA,
				 	P.ID AS PRODUCTO_ID, P.BARCODE, P.NOMBRE, P.PRECIO
				FROM LINEATICKET LT
				JOIN PRODUCTO P ON P.ID = LT.PRODUCTO_ID
				WHERE LT.TICKET_ID = ?
				""";
		
		try (Connection con = ConexionDB.getConnection()) {
			
			PreparedStatement ps = con.prepareStatement(sql); {
				
				ps.setInt(1, ticketId);
				ResultSet rs = ps.executeQuery(); {
					
					while (rs.next()) {
						//producto
						Producto p = new Producto();
						p.setId(rs.getInt("PRODUCTO_ID"));
						p.setBarcode(rs.getString("BARCODE"));
						p.setNombre(rs.getString("NOMBRE"));
						p.setPrecio(rs.getDouble("PRECIO"));
						
						//linea
						LineaTicket lt = new LineaTicket();
						lt.setLineaId(rs.getInt("ID"));
						lt.setCantidad(rs.getInt("CANTIDAD"));
						lt.setPrecioVenta(rs.getDouble("PRECIOVENTA"));
						lt.setProducto(p);
						
						lista.add(lt);
					}
					
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return lista;
	}

}
