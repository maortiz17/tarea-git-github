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
	
	private static final String QUERY_CREAR = "INSERT INTO LineaTicket (cantidad, precioVenta, producto_id, ticket_id) VALUES (?, ?, ?, ?)";
	private static final String QUERY_LISTAR = "SELECT id, cantidad, precioVenta, producto_id, ticket_id FROM LineaTicket WHERE ticket_id = ?";

	@Override
	public void crear(Connection con, LineaTicket lineaTicket) throws SQLException {
		try(PreparedStatement pstmt = con.prepareStatement(QUERY_CREAR)){
			pstmt.setInt(1, lineaTicket.getCantidad());
			pstmt.setDouble(2, lineaTicket.getPrecioVenta());
			pstmt.setLong(3, lineaTicket.getProductoId());
			pstmt.setLong(4, lineaTicket.getTicketId());
			
			pstmt.executeUpdate();
		}
	}

	@Override
	public List<LineaTicket> listarPorTicket(Connection con, long idTicket) throws SQLException {
		List<LineaTicket> lineas = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(QUERY_LISTAR)){
			pstmt.setLong(1, idTicket);
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					lineas.add(new LineaTicket(
							rs.getLong("id"),
							rs.getInt("cantidad"),
							rs.getDouble("precioVenta"),
							rs.getLong("producto_id"),
							rs.getLong("ticket_id")));
				}
			}
		}
		return lineas;
	}
}
