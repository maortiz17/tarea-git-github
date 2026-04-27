package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entities.LineaTicket;
import entities.Ticket;

public interface ITicketDAO {
	
	long insertar(Connection con, Ticket ticket) throws SQLException;
	void cerrarTicket(Connection con, long id, String estado) throws SQLException;
	Ticket buscarPorId(Connection con, long id) throws SQLException;
	List<Ticket> listarTicket(Connection con) throws SQLException;
	boolean borrar(Connection con, long id) throws SQLException;

}
