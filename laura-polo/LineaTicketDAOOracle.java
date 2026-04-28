package tarea.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tarea.dao.ILineaTicketDAO;
import tarea.entities.LineaTicket;


public class LineaTicketDAOOracle implements ILineaTicketDAO {

	private final static String INSERT = "insert into lineaticket (cantidad, precioventa, producto_id, ticket_id) values (?,?,?,?)";
	private final static String QUERY_GET_ALL_BY_ID = "select id, cantidad, precioventa, producto_id, ticket_id from lineaticket where ticket_id = ?";
	private final static String UPDATE = "update lineaticket set cantidad = ?, precioventa = ?, producto_id = ?, ticket_id = ? where id = ?";
	private final static String DELETE = "delete from lineaticket where id = ?";
	
	@Override
	public LineaTicket crear(Connection con, int cantidad, double precioVenta, long productoId, long ticketId) throws SQLException{
		String[] pk = {"id"};
		try(PreparedStatement pstmt = con.prepareStatement(INSERT, pk)){
			pstmt.setInt(1, cantidad);
			pstmt.setDouble(2, precioVenta);
			pstmt.setLong(3, productoId);
			pstmt.setLong(4, ticketId);
			
			int filasAfectadas = pstmt.executeUpdate();
			if(filasAfectadas > 0) {
				try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
					if (generatedKeys.next()) {
                        long id = generatedKeys.getLong(1);
						return new LineaTicket(id, cantidad, precioVenta, productoId, ticketId);
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<LineaTicket> getAllById(Connection con, long ticketId) throws SQLException{
		List<LineaTicket> resultadoLineas = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(QUERY_GET_ALL_BY_ID)){
			pstmt.setLong(1, ticketId);
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					long id = rs.getLong("id");
					int cantidad = rs.getInt("cantidad");
					double precioVenta = rs.getDouble("precioventa");
					long productoId = rs.getLong("producto_id");
			
					resultadoLineas.add(new LineaTicket(id, cantidad, precioVenta, productoId, ticketId));
				}
			}
		}
		return resultadoLineas;
	}

	@Override
	public LineaTicket modificar(Connection con, long id, int cantidad, double precioVenta, long productoId, long ticketId) throws SQLException{
		try(PreparedStatement pstmt = con.prepareStatement(UPDATE)){
			pstmt.setInt(1, cantidad);
			pstmt.setDouble(2, precioVenta);
			pstmt.setLong(3, productoId);
			pstmt.setLong(4, ticketId);
			pstmt.setLong(5, id);
			
			int filasAfectadas = pstmt.executeUpdate();
			if(filasAfectadas>0) {
				return new LineaTicket(id, cantidad, precioVenta, productoId, ticketId);
			}
		}
		return null;
	}

	@Override
	public boolean borrar(Connection con, long id) throws SQLException{
		try(PreparedStatement pstmt = con.prepareStatement(DELETE)){
			pstmt.setLong(1, id);
			
			 return pstmt.executeUpdate() > 0;
		}
	}

}
