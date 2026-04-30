package practica.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import practica.dao.ILineaTicketDAO;
import practica.entities.LineaTicket;

public class LineaTicketDAO implements ILineaTicketDAO {
	
	@Override
	public boolean insertar(Connection con, LineaTicket lt) throws SQLException {
		String consulta = "INSERT INTO LINEATICKET(CANTIDAD, PRECIOVENTA, PRODUCTO_ID, TICKET_ID) VALUES(?, ?, ?, ?)";
		try(PreparedStatement ps = con.prepareStatement(consulta)) {
			ps.setInt(1, lt.getCantidad());
			ps.setDouble(2, lt.getPrecioVenta());
			ps.setLong(3, lt.getProductoId());
			ps.setLong(4, lt.getTicketId());
			int filasAfectadas = ps.executeUpdate();
			if(filasAfectadas > 0) {
				return true;
			}
			return false;
		}
	}

	@Override
	public List<LineaTicket> obtenerLineasPorTicketId(Connection con, long id) throws SQLException {
		String consulta = "SELECT lt.ID, lt.CANTIDAD, lt.PRECIOVENTA, lt.PRODUCTO_ID, lt.TICKET_ID FROM LINEATICKET lt WHERE lt.TICKET_ID = ?";
		List<LineaTicket> listaLinea = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement(consulta)) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				listaLinea.add(new LineaTicket(rs.getLong("ID"), rs.getInt("CANTIDAD"), rs.getDouble("PRECIOVENTA"), rs.getLong("PRODUCTO_ID"), rs.getLong("TICKET_ID")));
			}
		}
		return listaLinea;
	}

}
