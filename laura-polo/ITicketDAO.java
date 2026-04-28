package tarea.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tarea.entities.Ticket;

public interface ITicketDAO {

	Ticket crear(Connection con, String ticketCerrado) throws SQLException;
	List<Ticket> buscarTicketPorEstado(Connection con, String ticketCerrado) throws SQLException;
	Ticket buscarPorId(Connection con, long id) throws SQLException;
	List<Ticket> getAll(Connection con) throws SQLException;
	Ticket modificar(Connection con, long id, String ticketCerrado) throws SQLException;
	boolean borrar(Connection con, long id) throws SQLException;
}
