package tarea.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tarea.entities.LineaTicket;

public interface ILineaTicketDAO {

	LineaTicket crear(Connection con, int cantidad, double precioVenta, long productoId, long ticketId) throws SQLException;
	List<LineaTicket> getAllById(Connection con, long ticketId) throws SQLException;
	LineaTicket modificar(Connection con, long id, int cantidad, double precioVenta, long productoId, long ticketId) throws SQLException;
	boolean borrar(Connection con, long id) throws SQLException;
}
