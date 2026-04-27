package tarea.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tarea.entities.LineaTicket;
import tarea.entities.Producto;

public interface ILineaTicketDAO {
	LineaTicket crear(Connection conexion, int cantidad, long ticketId, double precioVenta, Producto producto) throws SQLException;
	List<LineaTicket> listarPorTicket(Connection conexion, long id) throws SQLException;
}
