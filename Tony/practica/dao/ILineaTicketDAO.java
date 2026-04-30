package practica.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import practica.entities.LineaTicket;

public interface ILineaTicketDAO {
	
	boolean insertar(Connection con, LineaTicket lt) throws SQLException;
	List<LineaTicket> obtenerLineasPorTicketId(Connection con, long id) throws SQLException;

}
