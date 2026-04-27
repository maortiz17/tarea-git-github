package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Ticket;

public class TicketDAO implements ITicketDAO {

	@Override
	public long insertar(Connection con, Ticket ticket) throws SQLException {
		String query = "INSERT INTO TICKET (FECHAHORA, TICKETCERRADO) VALUES (?, ?)";
        String[] pk = {"ID"};  // el id se generá automatico y se guarda aquí 
        
        try (PreparedStatement pstmt = con.prepareStatement(query, pk)) {
          
            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(ticket.getFechaHora()));
            pstmt.setString(2, ticket.getTicketCerrado());
            
            pstmt.executeUpdate();

            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        }
        return -1;
	}

	@Override
	public void cerrarTicket(Connection con, long id, String estado)throws SQLException {
		
	    String query = "UPDATE TICKET SET TICKETCERRADO = ? WHERE ID = ?";
	    try (PreparedStatement pstmt = con.prepareStatement(query)) {
	        pstmt.setString(1, estado);
	        pstmt.setLong(2, id);
	        
	        int filasAfectadas = pstmt.executeUpdate();
	        
	        if (filasAfectadas > 0) {
	            System.out.println("Estado del ticket " + id + " actualizado a: " + estado);
	        } else {
	            System.out.println("No se encontró el ticket para actualizar.");
	        }
	    }
		
	}

	@Override
	public Ticket buscarPorId(Connection con, long id)throws SQLException {
		String query = "SELECT ID, FECHAHORA, TICKETCERRADO FROM TICKET WHERE ID = ?";
	    try (PreparedStatement pstmt = con.prepareStatement(query)) {
	        pstmt.setLong(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                
	                // las lineas las inicio vacías y las pong con set en el main
	                return new Ticket(
	                    rs.getLong("ID"),
	                    rs.getTimestamp("FECHAHORA").toLocalDateTime(),
	                    rs.getString("TICKETCERRADO"),
	                    new ArrayList<>() 
	                );
	            }
	        }
	    }
	    return null; // Si no encuentra el ticket, devuelve null
	}

	@Override
	public List<Ticket> listarTicket(Connection con)throws SQLException {
		String query = "SELECT ID, FECHAHORA, TICKETCERRADO FROM TICKET WHERE TICKETCERRADO = 'F'";
        List<Ticket> abiertos = new ArrayList<>();
        
        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
             
                abiertos.add(new Ticket(
                    rs.getLong("ID"),
                    rs.getTimestamp("FECHAHORA").toLocalDateTime(),
                    rs.getString("TICKETCERRADO"),
                    new ArrayList<>() 
                ));
            }
        }
        return abiertos;
	}

	@Override
	public boolean borrar(Connection con, long id)throws SQLException {
		String query = "DELETE FROM TICKET WHERE ID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, id);
           
            return pstmt.executeUpdate() > 0;
        }
    
	}

}
