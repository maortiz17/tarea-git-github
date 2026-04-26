package tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineaTicketDAO {

	private String SELECT_ALL = "SELECT * FROM LINEATICKET";
	private String SELECT_ID = "SELECT * FROM LINEATICKET WHERE ID = ?";
	private String DELETE = "DELETE FROM LINEATICKET WHERE ID = ?";
	private String INSERT = "INSERT INTO LINEATICKET(CANTIDAD,PRECIOVENTA, PRODUCTO_ID,TICKET_ID) VALUES(?,?,?,?)";

	Connection con;

	public LineaTicketDAO(Connection con) throws SQLException {
		this.con = con;
	}

	// -------------SELECT *----
	public List<LineaTicket> getAll() throws SQLException {
		List<LineaTicket> lineas = new ArrayList<>();
		try (PreparedStatement pst = con.prepareStatement(SELECT_ALL); ResultSet rs = pst.executeQuery()) {
			while (rs.next()) {
				LineaTicket lt = new LineaTicket(rs.getInt("ID"), rs.getInt("CANTIDAD"), rs.getFloat("PRECIOVENTA"),
						rs.getInt("PRODUCTO_ID"), rs.getInt("TICKET_ID"));
				lineas.add(lt);
			}
		}
		return lineas;
	}

	// -----SELECT por id
	public LineaTicket getById(int id) throws SQLException {
		try (PreparedStatement pst = con.prepareStatement(SELECT_ID);) {
			pst.setInt(1, id);
			try(ResultSet rs = pst.executeQuery()){
				if(rs.next())return new LineaTicket(rs.getInt("ID"), rs.getInt("CANTIDAD"), rs.getFloat("PRECIOVENTA"),
						rs.getInt("PRODUCTO_ID"), rs.getInt("TICKET_ID"));
				else {return null;}
			}
		}
	}
	//---BORRAR
	public boolean delete(int id) throws SQLException{
		try(PreparedStatement pst = con.prepareStatement(DELETE)){
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;
		
		}
	}
	//----Insert
	public boolean insert(LineaTicket lt) throws SQLException{
		try(PreparedStatement pst = con.prepareStatement(INSERT)){
			pst.setInt(1, lt.getCantidad());
			pst.setFloat(2, lt.getPrecioVenta());
			pst.setInt(3,lt.getProductoId());
			pst.setInt(4,lt.getTicketId());
			return pst.executeUpdate() > 0;
		}
	}
}
