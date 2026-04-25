package dao;

import java.util.List;

import entities.Ticket;

public interface InterfazTicketDAO {

	int insert(Ticket ticket); //devuelve el ID generado
	
	List<Ticket> getTicketsAbiertos();
	
	Ticket getById(int id);
	
	void delete(int id);
	
}
