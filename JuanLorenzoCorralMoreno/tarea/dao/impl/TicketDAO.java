package tarea.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tarea.dao.ITicketDAO;
import tarea.entities.LineaTicket;
import tarea.entities.Ticket;

public class TicketDAO implements ITicketDAO {
	private static final String selectById = ("""
			select FECHAHORA, TICKETCERRADO,lt.id as id_lineaticket, lt.CANTIDAD,PRECIOVENTA,PRODUCTO_ID
			from TICKET t
			left join LINEATICKET lt on lt.TICKET_ID = t.id
			where t.id = ?
			""");
	private static final String insert = ("Insert into ticket(FECHAHORA,TICKETCERRADO) values (?,?)");
	private static final String delete = ("delete from ticket where id = ?");
	private static final String selectCerradoById = ("Select TICKETCERRADO from ticket where id =?");
	private static final String updateCerradoByID = ("Update ticket SET TICKETCERRADO = ? WHERE ID = ?");
	private static final String selectAbieros = ("""
			select t.ID as id_ticket, FECHAHORA, TICKETCERRADO,lt.id as id_lineaticket, lt.CANTIDAD,PRECIOVENTA,PRODUCTO_ID
			from TICKET t
			left join LINEATICKET lt on lt.TICKET_ID = t.id
			where TICKETCERRADO = 'F'
			order by id_ticket
			""");
	String[] pk = { "ID" };

	@Override
	/*
	 * Metodo de creacion de ticket devuelve el objeto Ticket si se ha producido la
	 * inserccion en la bd
	 */
	public Ticket crear(Connection con, String ticketCerrado, List<LineaTicket> lineaTicket) throws SQLException {
		try (PreparedStatement pstmnt = con.prepareStatement(insert, pk)) {
			Timestamp now = Timestamp.valueOf(LocalDateTime.now());
			pstmnt.setTimestamp(1, now);
			pstmnt.setString(2, ticketCerrado);
			if (pstmnt.executeUpdate() > 0) {
				try (ResultSet rs = pstmnt.getGeneratedKeys()) {
					if (rs.next()) {
						long id = rs.getLong(1);
						return new Ticket(id, now, ticketCerrado, lineaTicket);
					}
				}
			}
		}
		return null;
	}
	/*
	 * Metodo para continuar la venta de un ticket devolvera true o false si el
	 * ticket esta o no cerrado
	 */

	@Override
	public boolean continuarVenta(Connection con, long id) throws SQLException {
		try (PreparedStatement pstmnt = con.prepareStatement(selectCerradoById)) {
			pstmnt.setLong(1, id);
			try (ResultSet rs = pstmnt.executeQuery()) {
				if (rs.next()) {
					return "T".equals(rs.getString("TICKETCERRADO"));
				}
			}
		}
		return false;
	}
	
	/*
	 * Metodo para consultar un ticket por id devolvemos el ticket
	 *  con la coleccion de lineaTicket que le corresponden
	 * o null
	 * */
	@Override
	public Ticket consultarTicket(Connection con, long id) throws SQLException {
		try (PreparedStatement pstmnt = con.prepareStatement(selectById)) {
			pstmnt.setLong(1, id);
			try (ResultSet rs = pstmnt.executeQuery()) {
				ArrayList<LineaTicket> lineaTicket = new ArrayList<>();
				if (!rs.next()) {
					return null;
				}
				Timestamp fechaHora = rs.getTimestamp("FECHAHORA");
				String estado = rs.getString("TICKETCERRADO");
				do {
					lineaTicket.add(new LineaTicket(rs.getLong("id_lineaticket"), rs.getLong("CANTIDAD"),
							rs.getDouble("PRECIOVENTA"), rs.getLong("PRODUCTO_ID"), id));
				} while (rs.next());
				return new Ticket(id, fechaHora, estado, lineaTicket);
			}
		}
	}
	
	/*
	 * Borramos un ticket recibiendo su id como parametro
	 * */

	@Override
	public boolean borrarTicket(Connection con, long id) throws SQLException {
		try (PreparedStatement pstmnt = con.prepareStatement(delete)) {
			pstmnt.setLong(1, id);
			return pstmnt.executeUpdate() > 0;
		}
	}
	
	/*
	 * Cambia el estado de un ticket y devolvemos un booleano
	 * */
	@Override
	public boolean cambiarTicketCerrado(Connection con, long id, String ticketCerrado) throws SQLException {
		try (PreparedStatement pstmnt = con.prepareStatement(updateCerradoByID)) {
			pstmnt.setString(1, ticketCerrado);
			pstmnt.setLong(2, id);
			return pstmnt.executeUpdate() > 0;
		}
	}

	/*
	 * Metodo que devuelve una coleccion de tickets ordenados por id y sus correspondientes 
	 * lineaticket para ello comprobamos en cada iteraccion de rs si el id del ticket es nuevo
	 * o seguimos en el mismo. En caso de ser nuevo creamos un objeto nuevo de ticket con sus datos
	 * y lo añadimos a la coleccion
	 * */
	@Override
	public List<Ticket> ticketsAbiertos(Connection con) throws SQLException {
		try (PreparedStatement pstmnt = con.prepareStatement(selectAbieros)) {
			try (ResultSet rs = pstmnt.executeQuery()) {

				Ticket actual = null;
				long idActual = -1;
				ArrayList<Ticket> tickets = new ArrayList<>();
				while (rs.next()) {
					Timestamp fecha;
					String estado;
					long ticketId = rs.getLong("id_ticket");
					if (actual == null || ticketId != idActual) {
						fecha = rs.getTimestamp("FECHAHORA");
						estado = rs.getString("TICKETCERRADO");
						idActual = ticketId;
						actual = new Ticket(idActual, fecha, estado, new ArrayList<>());
						tickets.add(actual);
					}
					actual.getLineaTicket().add(new LineaTicket(rs.getLong("id_lineaticket"), rs.getLong("CANTIDAD"),
							rs.getDouble("PRECIOVENTA"), rs.getLong("PRODUCTO_ID"), idActual));
				}
				return tickets;
			}
		}

	}

}
