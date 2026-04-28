package tarea.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tarea.entities.Ticket;

public interface ITicketDAO {
	long crear(Connection con, Ticket ticket) throws SQLException;
	Ticket buscarPorId (Connection con, long id) throws SQLException;
	List<Ticket> listarAbiertos (Connection con) throws SQLException;
	List<Ticket> listarTodos (Connection con) throws SQLException;
	void cerrar (Connection con, long id) throws SQLException;
	void borrar (Connection con, long id) throws SQLException;
}
