package tarea.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tarea.entities.LineaTicket;

public interface ILineaTicketDao {
	
	void insertar(Connection con , LineaTicket lTicket) throws SQLException;
	
	List<LineaTicket> listarPorTicket(Connection con, int idTicket) throws SQLException;

}
