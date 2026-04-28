package tarea.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tarea.entities.LineaTicket;

public interface ILineaTicketDAO {
	void crear (Connection con, LineaTicket lineaTicket) throws SQLException;
	List<LineaTicket> listarPorTicket(Connection con, long idTicket) throws SQLException;
}
