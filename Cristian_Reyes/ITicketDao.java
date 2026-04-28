package tarea.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tarea.entities.Ticket;

public interface ITicketDao {
	
	int insertar(Connection con, Ticket t) throws SQLException;
	
	List<Ticket> listarAbierta(Connection con) throws SQLException;
	Ticket buscarId (Connection con, int id) throws SQLException;
	boolean borrar(Connection con, int id) throws SQLException;
	
	void cerrarTicket(Connection con, int id) throws SQLException;

}
