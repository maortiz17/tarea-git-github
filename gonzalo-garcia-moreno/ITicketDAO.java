package tarea.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import tarea.entities.Ticket;

public interface ITicketDAO {
	
	Ticket crear(Connection conexion, LocalDateTime fechaHora, boolean ticketCerrado)throws SQLException;
	Ticket buscar(Connection conexion, long id) throws SQLException;
	List<Ticket> listarAbiertos(Connection conexion) throws SQLException;
	boolean borrar(Connection conexion, long id) throws SQLException;
	Ticket cerrar(Connection conexion, long id) throws SQLException;

}
