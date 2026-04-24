package entities;

import java.sql.Timestamp;

public class Ticket {

	private Timestamp fechaHora;
	private char ticketCerrado;
	
	
	/**
	 * @param fechaHora
	 * @param ticketCerrado
	 */
	public Ticket(Timestamp fechaHora, char ticketCerrado) {
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
	}


	/**
	 * @return the fechaHora
	 */
	public Timestamp getFechaHora() {
		return fechaHora;
	}


	/**
	 * @return the ticketCerrado
	 */
	public char getTicketCerrado() {
		return ticketCerrado;
	}
	
	
	
	
}
