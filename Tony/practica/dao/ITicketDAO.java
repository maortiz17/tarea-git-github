package practica.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import practica.entities.Ticket;

public interface ITicketDAO {
	
	Ticket crear(Connection con, LocalDateTime fechaHora, String ticketCerrado) throws SQLException;
	boolean modificar(Connection con, long id, String ticketCerrado) throws SQLException;
	Ticket buscar(Connection con, long id) throws SQLException;
	List<Ticket> listar(Connection con) throws SQLException;
	boolean eliminar(Connection con, long id) throws SQLException;

}
