package tarea.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tarea.dao.ITicketDao;
import tarea.entities.Ticket;

public class TicketDaoOracle implements ITicketDao {

	@Override
	public int insertar(Connection con, Ticket t) throws SQLException {
		
		String[] colRecu = {"ID"};
		String query= "insert into Ticket (fechaHora, ticketcerrado) values (?,?)";
		try (PreparedStatement ps = con.prepareStatement(query, colRecu))
		{
			ps.setTimestamp(1, Timestamp.valueOf(t.getFechaHora()));
			ps.setString(2, t.getTicketCerrado());
			
			ps.executeUpdate();
			
			try(ResultSet rs = ps.getGeneratedKeys())
			{
				if(rs.next())
				{
					int idGenerado = rs.getInt(1);
					t.setId(idGenerado);
					return idGenerado;
				}
			}
		}
		throw new SQLException("Error al obtener el ID del ticket insertado.");
		
		
	}

	@Override
	public List<Ticket> listarAbierta(Connection con) throws SQLException {
		// TODO Auto-generated method stub
		
		String query="select * from ticket where ticketcerrado='F'";
		List<Ticket> tickets= new ArrayList<>();
		
		try(Statement st = con.createStatement();
			ResultSet rS = st.executeQuery(query))
		{
			while(rS.next())
			{
				int id = rS.getInt("ID");
				LocalDateTime fechaHora=rS.getTimestamp("fechaHora").toLocalDateTime();
				String cerrado= rS.getString("ticketCerrado");
				Ticket t = new Ticket(id, fechaHora, cerrado);
				
				tickets.add(t);
			}
			
			
		}
		return tickets;
		
	}

	@Override
	public Ticket buscarId(Connection con, int id) throws SQLException {
		String query ="SELECT FECHAHORA, TICKETCERRADO FROM TICKET WHERE ID=?";
		
		try(PreparedStatement st = con.prepareStatement(query);)
		{
				st.setInt(1, id);
			try(ResultSet rSet= st.executeQuery())
				{
					if (rSet.next())
					{
						
						LocalDateTime fechaHora=rSet.getTimestamp("fechaHora").toLocalDateTime();
						String cerrado= rSet.getString("ticketCerrado");
						Ticket t = new Ticket(id, fechaHora, cerrado);
						return t;
					}
				}
		}
		return null;
	}

	@Override
	public boolean borrar(Connection con, int id) throws SQLException {
		String query = "DELETE FROM TICKET WHERE ID=?";
		try (PreparedStatement pSt = con.prepareStatement(query))
		{
			pSt.setInt(1, id);
			int afectadas=pSt.executeUpdate();
			
			if (afectadas != 0)
				return true;
		}
		return false;
	}

	@Override
	public void cerrarTicket(Connection con, int id) throws SQLException {
		
		String query = "update ticket set ticketcerrado ='T' where id=?";
		
		try (PreparedStatement pSt = con.prepareStatement(query))
		{
			pSt.setInt(1, id);
			int afectados = pSt.executeUpdate();
			
			if(afectados==0)
				throw new SQLException("No se a generado ningun cambio");
		}
		
	}

}
