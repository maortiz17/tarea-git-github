package tarea.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tarea.entities.LineaTicket;

public interface ILineaTicketDAO {
	LineaTicket crear(Connection con, long cantidad, double precioVenta, long productoID, long ticketId) throws SQLException;
	LineaTicket buscar(Connection con, long id)throws SQLException;
	List<LineaTicket> recuperar(Connection con, long idTicket) throws SQLException;
	List<Long> listarId(Connection con)throws SQLException;
}
