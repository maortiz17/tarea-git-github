package tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
	private String SELECT_LAST  = "SELECT MAX(ID) FROM TICKET";
	private String UPDATE_CERRAR = "UPDATE TICKET SET TICKETCERRADO = 'T' WHERE ID = ?";
	private String SELECT_CERRADO = "SELECT * FROM TICKET WHERE TICKETCERRADO = ?";
	private String SELECT_ALL = "SELECT * FROM TICKET";
	private String SELECT_ID = "SELECT * FROM TICKET WHERE ID = ?";
	private String SELECT_LINEA = "SELECT * FROM LINEATICKET WHERE TICKET_ID = ?";
	private String DELETE = "DELETE FROM TICKET WHERE ID = ?";
	private String INSERT = "INSERT INTO TICKET(FECHAHORA,TICKETCERRADO) VALUES(?,?)";
	private String UPDATE = "UPDATE TICKET SET FECHAHORA = ?,TICKETCERRADO = ? WHERE ID = ?";

	Connection con;

	public TicketDAO(Connection con) {
		this.con = con;
	}

	// ----selects
	// --- para las lineas
	public List<LineaTicket> getAllLineas(Ticket t) throws SQLException {
		List<LineaTicket> lineas = new ArrayList<>();
		try (PreparedStatement pst = con.prepareStatement(SELECT_LINEA);) {
			pst.setInt(1, t.getId());
			try (ResultSet rs = pst.executeQuery();) {
				while (rs.next()) {
					lineas.add(new LineaTicket(rs.getInt("ID"), rs.getInt("CANTIDAD"), rs.getFloat("PRECIOVENTA"),
							rs.getInt("PRODUCTO_ID"), rs.getInt("TICKET_ID")));
				}
			}
		}
		return lineas;
	}
	//--- para el ultimo tiquect creado
	public int getLastId() throws SQLException {
	    
	    try (PreparedStatement pst = con.prepareStatement(SELECT_LAST);
	         ResultSet rs = pst.executeQuery()) {
	        if (rs.next()) return rs.getInt("ID");
	    }
	    return -1;
	}

	// ----Select *
	public List<Ticket> getAll() throws SQLException {
		List<Ticket> tickets = new ArrayList<>();
		try (PreparedStatement stm = con.prepareStatement(SELECT_ALL); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {
				boolean cerrado = rs.getString("TICKETCERRADO").equals("T");
				Ticket t = new Ticket(rs.getInt("ID"), rs.getTimestamp("FECHAHORA"),
						cerrado);
				t.setLineaTicket(getAllLineas(t));
				tickets.add(t);
			}
		}
		return tickets;
	}

	public List<Ticket> getAllCerrado(String C) throws SQLException {
		
		List<Ticket> tickets = new ArrayList<>();
		try (PreparedStatement stm = con.prepareStatement(SELECT_CERRADO)) {
			stm.setString(1, C);

			try (ResultSet rs = stm.executeQuery();) {
				while (rs.next()) {
					boolean cerrado = rs.getString("TICKETCERRADO").equals("T");
					Ticket t = new Ticket(rs.getInt("ID"), rs.getTimestamp("FECHAHORA"),
							cerrado);
					t.setLineaTicket(getAllLineas(t));
					tickets.add(t);
				}
			}

		}
		return tickets;
	}

	// por id
	public Ticket getById(int id) throws SQLException {

		try (PreparedStatement stm = con.prepareStatement(SELECT_ID)) {
			stm.setInt(1, id);
			try (ResultSet rs = stm.executeQuery()) {
				if (rs.next()) {
					boolean cerrado = rs.getString("TICKETCERRADO").equals("T");

					Ticket t = new Ticket(rs.getInt("ID"), rs.getTimestamp("FECHAHORA"),
							cerrado);
					t.setLineaTicket(getAllLineas(t));
					return t;
				} else {
					return null;
				}
			}
		}
	}
	// --- DELETE----

	public boolean delete(int id) throws SQLException {
		try (PreparedStatement pst = con.prepareStatement(DELETE)) {
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;

		}
	}

	// --- insert

	public boolean insert(Ticket t) throws SQLException {

		try (PreparedStatement pst = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

			pst.setTimestamp(1, t.getFechahora());
			pst.setString(2, t.isTicketcerrado() ? "T" : "F");
			pst.executeUpdate();

			try (ResultSet rs = pst.getGeneratedKeys()) {
				if (rs.next()) {
					t.setId(rs.getInt(1));
				}
			}
		}

		LineaTicketDAO lineaDAO = new LineaTicketDAO(con);

		for (LineaTicket lt : t.getLineaTicket()) {
			lt.setTicketId(t.getId());
			lineaDAO.insert(lt);
		}

		return true;
	}

	// ---update

	public boolean update(Timestamp fechahora, boolean ticketcerrado, int id) throws SQLException {
		try (PreparedStatement pst = con.prepareStatement(UPDATE)) {
			pst.setTimestamp(1, fechahora);
			pst.setString(2, ticketcerrado ? "T" : "F");
			pst.setInt(3, id);
			return pst.executeUpdate() > 0;
		}
	}
	//-- para cerrar el tiquet
	public boolean cerrarTicket(int id) throws SQLException {
	    
	    try (PreparedStatement pst = con.prepareStatement(UPDATE_CERRAR)) {
	        pst.setInt(1, id);
	        return pst.executeUpdate() > 0;
	    }
	}
}
