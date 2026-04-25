package dao;

import java.util.List;

import entities.LineaTicket;

public interface InterfazLineaTicketDAO {

	void insert(LineaTicket linea);
	List<LineaTicket> getByTicketId(int ticketId);
}
