package practica.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import practica.dao.ITicketDAO;
import practica.entities.Ticket;

public class TicketDAOOracle implements ITicketDAO {
	
	@Override
	public Ticket crear(Connection con, LocalDateTime fechaHora, String ticketCerrado) throws SQLException {
		String consulta = "INSERT INTO TICKET(FECHAHORA, TICKETCERRADO) VALUES(?, ?)";
		String[] primaryKey = {"id"};
		try(PreparedStatement ps = con.prepareStatement(consulta, primaryKey)) {
			ps.setTimestamp(1, java.sql.Timestamp.valueOf(fechaHora));
			ps.setString(2, ticketCerrado);
			
			int filasAfectadas = ps.executeUpdate();
			if(filasAfectadas > 0) {
				try(ResultSet rs = ps.getGeneratedKeys()) {
					if(rs.next()) {
						long id = rs.getLong(1);
						return new Ticket(id, fechaHora, ticketCerrado);
					}
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean modificar(Connection con, long id, String ticketCerrado) throws SQLException {
		// TODO Auto-generated method stub
		String consulta = "UPDATE TICKET SET TICKETCERRADO = ? WHERE ID = ?";
		try(PreparedStatement ps = con.prepareStatement(consulta)) {
			ps.setString(1, ticketCerrado);
			ps.setLong(2, id);
			
			int filasAfectadas = ps.executeUpdate();
			if(filasAfectadas > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Ticket buscar(Connection con, long id) throws SQLException {
		// TODO Auto-generated method stub
		String consulta = "SELECT * FROM TICKET WHERE ID = ?";
		try(PreparedStatement ps = con.prepareStatement(consulta)) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				long ticketId = rs.getLong("ID");
                LocalDateTime fechaHora = rs.getTimestamp("FECHAHORA").toLocalDateTime();
                String ticketCerrado = rs.getString("TICKETCERRADO");

                return new Ticket(ticketId, fechaHora, ticketCerrado);

			}
		}
		return null;
	}

	@Override
	public List<Ticket> listar(Connection con) throws SQLException {
		String consulta = "SELECT * FROM TICKET";
		List<Ticket> tickets = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement(consulta)) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				tickets.add(new Ticket(rs.getLong("ID"), rs.getTimestamp("FECHAHORA").toLocalDateTime(), rs.getString("TICKETCERRADO")));
			}
		}
		return tickets;
	}

	@Override
	public boolean eliminar(Connection con, long id) throws SQLException {
		// TODO Auto-generated method stub
		String consulta = "DELETE FROM TICKET WHERE ID = ?";
		try(PreparedStatement ps = con.prepareStatement(consulta)) {
			ps.setLong(1, id);
			int filasAfectadas = ps.executeUpdate();
			if(filasAfectadas > 0) {
				return true;
			}
		}
		return false;
	}
	
	

}
