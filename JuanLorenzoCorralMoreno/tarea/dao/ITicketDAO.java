package tarea.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


import tarea.entities.LineaTicket;
import tarea.entities.Ticket;

public interface ITicketDAO {
	Ticket crear(Connection con,String ticketCerrado, List<LineaTicket> lineaTicket) throws SQLException;
	boolean continuarVenta(Connection con, long id)throws SQLException;
	Ticket consultarTicket(Connection con, long id)throws SQLException;
	List<Ticket> ticketsAbiertos(Connection con)throws SQLException ;
	boolean borrarTicket(Connection con, long id)throws SQLException;
	boolean cambiarTicketCerrado(Connection con, long id, String ticketCerrado)throws SQLException;

}
