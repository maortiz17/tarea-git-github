package tarea.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tarea.dao.ITicketDAO;
import tarea.entities.Ticket;

public class TicketDAOOracle implements ITicketDAO {

	private static final String QUERY_CREAR = "INSERT INTO Ticket (fechaHora, ticketCerrado) VALUES (?, ?)";
	private static final String QUERY_BUSCAR_POR_ID = "SELECT id, fechaHora, ticketCerrado FROM Ticket WHERE id = ?";
	private static final String QUERY_LISTAR_ABIERTOS = "SELECT id, fechaHora, ticketCerrado FROM Ticket WHERE ticketCerrado = 'F'";
	private static final String QUERY_CERRAR = "UPDATE Ticket SET ticketCerrado = 'T' WHERE id = ?";
	private static final String QUERY_BORRAR = "DELETE FROM Ticket WHERE id = ?";
	private static final String QUERY_LISTAR_TODOS = "SELECT id, fechaHora, ticketCerrado FROM Ticket";
	
	@Override
	public long crear(Connection con, Ticket ticket) throws SQLException {
		
		String[] pk = {"id"}; //Crear array para guardar el campo autoincremental
		
		try(PreparedStatement pstmt = con.prepareStatement(QUERY_CREAR, pk)){
			pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setString(2, ticket.getTicketCerrado());
			
			int filasAfectadas = pstmt.executeUpdate();
			
			if(filasAfectadas > 0) {
				try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
					if(generatedKeys.next()) {
						long id = generatedKeys.getLong(1);
						return id;
					}
				}
			}
		}
		return 0;
	}

	@Override
	public Ticket buscarPorId(Connection con, long id) throws SQLException {
		try(PreparedStatement pstmt = con.prepareStatement(QUERY_BUSCAR_POR_ID)){
			pstmt.setLong(1, id);
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return new Ticket(rs.getLong("id"), rs.getTimestamp("fechaHora").toLocalDateTime(), rs.getString("ticketCerrado"));
				}
			}
		}
		return null;
	}

	@Override
	public List<Ticket> listarAbiertos(Connection con) throws SQLException {
		List<Ticket> tickets = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(QUERY_LISTAR_ABIERTOS)){
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					tickets.add(new Ticket(
							rs.getLong("id"),
							rs.getTimestamp("fechaHora").toLocalDateTime(),
							rs.getString("ticketCerrado")
							));
				}
			}
		}
		return tickets;
	}

	@Override
	public void cerrar(Connection con, long id) throws SQLException {
		try(PreparedStatement pstmt = con.prepareStatement(QUERY_CERRAR)){
			pstmt.setLong(1, id);
			int filasAfectadas = pstmt.executeUpdate();
			if(filasAfectadas == 0) {
				throw new SQLException("No se encontró ningún ticket con id: " + id);
			}
		}
	}

	@Override
	public void borrar(Connection con, long id) throws SQLException {
		try(PreparedStatement pstmt = con.prepareStatement(QUERY_BORRAR)){
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		}
	}
	
	@Override
	public List<Ticket> listarTodos(Connection con) throws SQLException{
		List<Ticket> tickets = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(QUERY_LISTAR_TODOS)){
			try (ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					tickets.add(new Ticket(
							rs.getLong("id"),
							rs.getTimestamp("fechaHora").toLocalDateTime(),
							rs.getString("ticketCerrado")));
				}				
			}			
		}			
		return tickets;
	}
}
