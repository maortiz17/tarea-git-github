package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entities.LineaTicket;
import entities.Ticket;
import util.ConexionDB;

public class TicketDAO implements InterfazTicketDAO{

	/**
	 * Método para insertar tickets en la tabla Ticket de la BD, con transacción (o intento de)
	 * @param objeto de clase {@link Ticket}
	 * @return ID del ticket insertado
	 */
	@Override
	public int insert(Ticket ticket) {
		
		int ticketId = -1; //-1 porque no se puede poner a null, pero luego se cambia
		
		String sqlTicket = "INSERT INTO TICKET (FECHAHORA, TICKETCERRADO) VALUES (?, ?)";
		String sqlLinea = """
				INSERT INTO LINEATICKET (CANTIDAD, PRECIOVENTA, PRODUCTO_ID, TICEKT_ID)
				VALUES (?, ?, ?, ?)
				""";
		
		try (Connection con = ConexionDB.getConnection()) {
			
			con.setAutoCommit(false); //iniciamos la transacción
			
			//insertar el ticket
			try (PreparedStatement ps = con.prepareStatement(sqlTicket)) {
				
				ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now())); //no vale solo con LocalDateTime.now() porque en la BD, fechahora es de tipo timeStamp
				ps.setString(2, ticket.isTicketCerrado() ? "T" : "F");
				
				ps.executeUpdate();
				
				ResultSet rs = ps.getGeneratedKeys(); //guardamos los datos del ticket insertado en un ResultSet para recoger su id generado
				if(rs.next()) {
					ticketId = rs.getInt(1);
				}				
			}
			
			//insertar lineas
			try (PreparedStatement ps = con.prepareStatement(sqlLinea)) {
				
				for(LineaTicket linea : ticket.getLineas()) {
					
					ps.setInt(1, linea.getCantidad());
					ps.setDouble(2, linea.getPrecioVenta());
					ps.setInt(3, linea.getProducto().getId());
					ps.setInt(4, linea.getTicket().getTicketId());
					
					ps.executeUpdate();
				}
			}
			
			con.commit();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
			
			//ConexionDB.getConnection().rollback(); 
			//No sé cómo implementarlo
		}
		
		return ticketId;
	}
	

	/**
	 * Método para listar los tickets abiertos
	 * @return lista generada de objetos Ticket
	 */
	@Override
	public List<Ticket> getTicketsAbiertos() {

		List<Ticket> lista = new ArrayList<>();
		
		String sql = "SELECT * FROM TICKET WHERE TICKETCERRADO = 'F'";
		
		try (Connection con = ConexionDB.getConnection()) {
			
			PreparedStatement ps = con.prepareStatement(sql); 
			ResultSet rs = ps.executeQuery(); {
			
				while(rs.next()) {
					Ticket t = new Ticket();
					t.setTicketId(rs.getInt("ID"));
					t.setFechaHora(rs.getTimestamp("FECHAHORA").toLocalDateTime());
					t.setTicketCerrado(false);
					
					lista.add(t);
				}
					
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return lista;
	}
	

	/**
	 * Método para buscar un ticket por su ID
	 * @return objeto de clase {@link Ticket} (sin lineas)
	 */
	@Override
	public Ticket getById(int id) {
		
		Ticket t = null;
		
		String sql = "SELECT * FROM TICKET WHERE ID = ?";
		
		try (Connection con = ConexionDB.getConnection()) {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(); {
				
				if(rs.next()) {
					t = new Ticket();
					t.setTicketId(rs.getInt("ID"));
					t.setFechaHora(rs.getTimestamp("FECHAHORA").toLocalDateTime());
					t.setTicketCerrado("T".equals(rs.getString("TICKETCERRADO")));
				}				
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
		return t;
	}

	
	/**
	 * Método para borrar un ticket 
	 * @param ID del ticket
	 */
	@Override
	public void delete(int id) {

		String sql = "DELETE FROM TICKET WHERE ID = ?";
		
		try (Connection con = ConexionDB.getConnection()) {
			
			PreparedStatement ps = con.prepareStatement(sql); {
				
				ps.setInt(1, id);
				ps.executeUpdate();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	
	/**
	 * Método para añadir lineas un ticket existente
	 * @param ID del ticket
	 * @param lsita de objetos de la clase {@link LineaTicket}
	 */
	@Override
	public void addLineas(int ticketId, List<LineaTicket> lineas) {

	    String sql = "INSERT INTO LINEATICKET (CANTIDAD, PRECIOVENTA, PRODUCTO_ID, TICKET_ID) VALUES (?, ?, ?, ?)";

	    try (Connection con = ConexionDB.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        for (LineaTicket linea : lineas) {
	        	ps.setInt(1, linea.getCantidad());
	        	ps.setDouble(2, linea.getPrecioVenta());
	        	ps.setInt(3, linea.getProducto().getId());
	        	ps.setInt(4, ticketId);

	        	ps.executeUpdate();
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * Método para cerrar un ticket
	 * @param ID del ticket
	 */
	@Override
	public void cerrarTicket(int ticketId) {

	    String sql = "UPDATE TICKET SET TICKETCERRADO = 'T' WHERE ID = ?";

	    try (Connection con = ConexionDB.getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        pstmt.setInt(1, ticketId);
	        pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
