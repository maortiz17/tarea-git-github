package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import entities.LineaTicket;
import entities.Producto;
import entities.Ticket;

public interface ILineaTicketDAO {
	// crear, buscar, listar, modificar, borrar
	/**
	 * Name                                      Null?    Type
 ----------------------------------------- -------- ----------------------------
 ID                                        NOT NULL NUMBER(9)
 CANTIDAD                                  NOT NULL NUMBER(5)
 PRECIOVENTA                               NOT NULL NUMBER(8,2)
 PRODUCTO_ID                                        NUMBER(9)
 TICKET_ID                                          NUMBER(9)
 
 	private long id;
	private int cantidad;
	private double precioVenta;
	private Producto producto;
	 */
		// int cantidad, double precioVenta, int idProducto, int idTicket, Connection con
		LineaTicket crear(Connection con, int cantidad, double precioVenta, Producto producto, long idTicket) throws SQLException;
		List<LineaTicket> listarPorTicketId(Connection con, long idTicket) throws SQLException;

}
