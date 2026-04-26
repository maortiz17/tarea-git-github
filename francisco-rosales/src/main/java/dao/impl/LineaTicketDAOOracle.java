package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ILineaTicketDAO;
import entities.LineaTicket;
import entities.Producto;
import entities.Ticket;

public class LineaTicketDAOOracle implements ILineaTicketDAO {
	// int cantidad, double precioVenta, int idProducto, int idTicket, Connection con
	@Override
	public LineaTicket crear(Connection con, int cantidad, double precioVenta, Producto producto, long idTicket) throws SQLException {
		// TODO Auto-generated method stub
		String query = "insert into lineaticket(cantidad, precioventa, producto_id, ticket_id) values(?,?,?,?)";
		String[] pk = {"id"};
		try(PreparedStatement pstmt = con.prepareStatement(query,pk)){
			pstmt.setInt(1, cantidad);
			pstmt.setDouble(2, precioVenta);
			pstmt.setLong(3, producto.getId());
			pstmt.setLong(4, idTicket);
			
			int filasAfectadas = pstmt.executeUpdate();
			
			if(filasAfectadas > 0) {
				try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
					if(generatedKeys.next()) {
						long id = generatedKeys.getLong(1);
						return new LineaTicket(id, cantidad, precioVenta, producto);
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<LineaTicket> listarPorTicketId(Connection con, long idTicket) throws SQLException {
		// TODO Auto-generated method stub
		String query = "SELECT L.ID, L.CANTIDAD, L.PRECIOVENTA, L.PRODUCTO_ID, "
								+ "P.BARCODE, P.NOMBRE, P.PRECIO " +
				                "FROM LINEATICKET L "
				                + "JOIN PRODUCTO P ON L.PRODUCTO_ID = P.ID " +
				       "WHERE L.TICKET_ID = ?";
		List<LineaTicket> lineas = new ArrayList<LineaTicket>();
		try(PreparedStatement pstmt = con.prepareStatement(query)){
			pstmt.setLong(1, idTicket);
			try(ResultSet result = pstmt.executeQuery()){
				while(result.next()) {
					
					long idProducto = result.getLong("PRODUCTO_ID");
					String barcode = result.getString("BARCODE");
					String nombre = result.getString("NOMBRE");
					double precio = result.getDouble("PRECIO");
					Producto producto = new Producto(idProducto, barcode, nombre, precio);
					
					long idLinea = result.getLong("ID");
					int cantidad = result.getInt("CANTIDAD");
					LineaTicket linea = new LineaTicket(idLinea, cantidad, precio, producto);
					
					lineas.add(linea);
				}
			}
		}
		return lineas;
		
	}

}
