package tarea.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import tarea.dao.ITicketDAO;
import tarea.entities.Ticket;

public class TicketDAOOracle implements ITicketDAO {

	public Ticket crear(Connection conexion, LocalDateTime fechaHora, boolean ticketCerrado) throws SQLException {

		return null;
	}

	public Ticket buscar(Connection conexion, long id) throws SQLException {

		return null;
	}

	public List<Ticket> listarAbiertos(Connection conexion) throws SQLException {

		return null;
	}

	public boolean borrar(Connection conexion, long id) throws SQLException {

		String query = "delete from ticket where id = ?";

		try (PreparedStatement pr = conexion.prepareStatement(query)) {

			pr.setLong(1, id);

			return pr.executeUpdate() > 0;
		}

	}

	public Ticket cerrar(Connection conexion, long id) throws SQLException {

		String query = "update ticket set ticketcerrado = 'T' where id = ?";

		try (PreparedStatement pr = conexion.prepareStatement(query)) {
			pr.setLong(1, id);

			if (pr.executeUpdate() > 0) {

				return buscar(conexion, id);

			}

		}
		return null;

	}

}
