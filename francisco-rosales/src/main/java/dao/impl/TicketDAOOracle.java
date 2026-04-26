package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dao.ITicketDAO;
import entities.LineaTicket;
import entities.Ticket;

public class TicketDAOOracle implements ITicketDAO {

	@Override
	public Ticket crear(Connection con, LocalDateTime fechaHora, boolean ticketCerrado) throws SQLException {
		String query = "insert into ticket(fechahora, ticketcerrado) values(?,?)";
		String[] pk = {"id"};
		try(PreparedStatement pstmt = con.prepareStatement(query, pk)){
			pstmt.setTimestamp(1, Timestamp.valueOf(fechaHora));
			pstmt.setString(2, (ticketCerrado ? "T" : "F"));
			
			int filasAfectadas = pstmt.executeUpdate();
			
			if(filasAfectadas > 0) {
				try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
					if(generatedKeys.next()) {
						long id = generatedKeys.getLong(1);
						return new Ticket(id, fechaHora, ticketCerrado, new LinkedList<LineaTicket>());
					}
				}
			}
			
		}
		return null;
	}

	@Override
	public Ticket buscar(Connection con, long idTicket) throws SQLException {
		String query = "select id, fechahora, ticketcerrado from ticket where id = ?";
		try(PreparedStatement pstmt = con.prepareStatement(query)){
			pstmt.setLong(1, idTicket);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					long id = idTicket;
					LocalDateTime fechaHora = rs.getTimestamp("fechahora").toLocalDateTime();
					boolean ticketCerrado = "T".equals(rs.getString("ticketcerrado"));
					return new Ticket(id, fechaHora, ticketCerrado, new ArrayList<LineaTicket>());
				}
			}
			
		}
		return null;
	}

	@Override
	public List<Ticket> listar(Connection con) throws SQLException {
		// TODO Auto-generated method stub
		String query = "select id, fechahora, ticketcerrado from ticket order by id asc";
		List<Ticket> lista = new ArrayList<Ticket>();
		try(PreparedStatement pstmt = con.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery()){
			while(rs.next()) {
				
				long id = rs.getLong("id");
				LocalDateTime fechaHora = rs.getTimestamp("fechahora").toLocalDateTime();
				boolean ticketCerrado = "T".equals(rs.getString("ticketcerrado"));
				
				Ticket ticket = new Ticket(id, fechaHora, ticketCerrado, new ArrayList<LineaTicket>());
				
				lista.add(ticket);
			}
			
		}
		return lista;
	}
	
	@Override
	public List<Ticket> listarAbierto(Connection con) throws SQLException {
		// TODO Auto-generated method stub
		String query = "select id, fechahora, ticketcerrado from ticket where ticketcerrado = 'F' order by id asc";
		List<Ticket> lista = new ArrayList<Ticket>();
		try(PreparedStatement pstmt = con.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery()){
			while(rs.next()) {
				
				long id = rs.getLong("id");
				LocalDateTime fechaHora = rs.getTimestamp("fechahora").toLocalDateTime();
				boolean ticketCerrado = "T".equals(rs.getString("ticketcerrado"));
				
				Ticket ticket = new Ticket(id, fechaHora, ticketCerrado, new ArrayList<LineaTicket>());
				
				lista.add(ticket);
			}
			
		}
		return lista;
	}

	@Override
	public boolean modificar(Connection con, long id, boolean ticketCerrado)
			throws SQLException {
		String query = "update TICKET set TICKETCERRADO = ? where id = ?";
		try(PreparedStatement pstmt = con.prepareStatement(query)){
			pstmt.setString(1, ticketCerrado ? "T" : "F");
			pstmt.setLong(2, id);
			int filas = pstmt.executeUpdate();
			System.out.println("bien ");
			if(filas > 0) {
				return true;
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
		
	}

	@Override
	public boolean eliminar(Connection con, long id) throws SQLException {
		// TODO Auto-generated method stub
		String query = "delete from ticket where id = ?";
		try(PreparedStatement pstmt = con.prepareStatement(query)){
			pstmt.setLong(1, id);
			return pstmt.executeUpdate() > 0;
		}
	}

}
