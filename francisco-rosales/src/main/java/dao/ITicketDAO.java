package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import entities.Ticket;

public interface ITicketDAO {
	// crear, buscar, listar, modificar, borrar
	//**
	/**
 Name                                      Null?    Type
 ----------------------------------------- -------- ----------------------------
 ID                                        NOT NULL NUMBER(9)
 FECHAHORA                                 NOT NULL TIMESTAMP(6)
 TICKETCERRADO                             NOT NULL VARCHAR2(1)

	 * @param con
	 * @param fechaHora
	 * @param ticketCerrado
	 * @return
	 * @throws SQLException
	 */
	
	Ticket crear(Connection con, LocalDateTime fechaHora, boolean ticketCerrado) throws SQLException;
	Ticket buscar(Connection con, long id) throws SQLException;
	List<Ticket> listar(Connection con) throws SQLException;
	List<Ticket> listarAbierto(Connection con) throws SQLException;
	boolean modificar(Connection con, long id, boolean ticketCerrado) throws SQLException;
	boolean eliminar(Connection con, long id) throws SQLException;
}
