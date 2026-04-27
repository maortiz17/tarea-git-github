package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entities.LineaTicket;

public interface ILineaTicketDAO {
	
	void insertar(Connection conn, LineaTicket linea, long idTicket) throws SQLException;
	
	List<LineaTicket> listarPorTicket(Connection con, long idTicket) throws SQLException;
	
}
