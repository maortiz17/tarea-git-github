package tarea.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tarea.dao.ITicketDAO;
import tarea.entities.Ticket;

public class TicketDAOOracle implements ITicketDAO{
	private final static String INSERT = "insert into ticket (fechaHora, ticketCerrado) values (?,?)";
	private final static String GET_TICKETS_ABIERTOS = "select id, fechaHora, ticketCerrado from ticket where ticketCerrado = ? order by fechahora";
	private final static String GET_BY_ID = "select id, fechaHora, ticketCerrado from ticket where id = ?";
	private final static String GET_ALL = "select id, fechaHora, ticketCerrado from ticket";
	private final static String UPDATE = "update ticket set fechahora = ?, ticketcerrado = ? where id = ?";
	private final static String DELETE = "delete from ticket where id = ?";

	@Override
	public Ticket crear(Connection con, String ticketCerrado) throws SQLException {
		String[] pk = {"id"};
		try(PreparedStatement pstmt = con.prepareStatement(INSERT, pk)){
			pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setString(2, ticketCerrado);

			int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
            	try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
            		if (generatedKeys.next()) {
                        long id = generatedKeys.getLong(1);
                        return new Ticket(id, LocalDateTime.now(), ticketCerrado);
            		}
            	}	
            }
		}
		
		return null;
	}

	@Override
	public List<Ticket> buscarTicketPorEstado(Connection con, String ticketCerrado) throws SQLException {
		List<Ticket> listaTickets = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(GET_TICKETS_ABIERTOS)){
			pstmt.setString(1, ticketCerrado);
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					long id = rs.getLong("id");
					LocalDateTime fecha = rs.getTimestamp("FECHAHORA").toLocalDateTime();
					listaTickets.add(new Ticket(id, fecha, ticketCerrado)); 
				}
			}
		}
		return listaTickets;
	}
	
	@Override
	public Ticket buscarPorId(Connection con, long id) throws SQLException {
		try(PreparedStatement pstmt = con.prepareStatement(GET_BY_ID)){
			pstmt.setLong(1, id);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					LocalDateTime fecha = rs.getTimestamp("FECHAHORA").toLocalDateTime();
					String ticketCerrado = rs.getString("ticketcerrado");
					return new Ticket(id, fecha, ticketCerrado);
				}
			}
		}
		return null;
	}

	@Override
	public List<Ticket> getAll(Connection con) throws SQLException {
		List<Ticket> listaTickets = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(GET_ALL)){
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					long id = rs.getLong("ID");
					LocalDateTime fecha = rs.getTimestamp("FECHAHORA").toLocalDateTime();
					String ticketCerrado = rs.getString("TICKETCERRADO");
					
					listaTickets.add(new Ticket(id, fecha, ticketCerrado));
				}
				return listaTickets;
			}
		}
	}

	@Override
	public Ticket modificar(Connection con, long id, String ticketCerrado) throws SQLException {
		try(PreparedStatement pstmt = con.prepareStatement(UPDATE)){
			//acualizo la hora cuando el ticket pasa de abierto a cerrado, asi la hora oficial es cuando esta pagado, no cuando se abrió el ticket. 
			LocalDateTime fechaHora = LocalDateTime.now();
			pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(fechaHora));
			pstmt.setString(2, ticketCerrado);
			pstmt.setLong(3, id);
			
			int filasAfectadas = pstmt.executeUpdate();
			
			if(filasAfectadas > 0) {	
				return new Ticket(id, fechaHora, ticketCerrado);
			}
		}
		return null;
	}

	@Override
	public boolean borrar(Connection con, long id) throws SQLException {
		try(PreparedStatement pstmt = con.prepareStatement(DELETE)){
			pstmt.setLong(1, id);
			
			 return pstmt.executeUpdate() > 0;
		}
	}

	

}
