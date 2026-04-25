package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

	private int ticketId;
	private LocalDateTime fechaHora;
	private boolean ticketCerrado;
	private List<LineaTicket> lineas;
	
	
	/**
	 * Constructor por defecto para rellenar con ResultSet
	 */
	public Ticket() {};
	
	
	/**
	 * Constructor por defecto
	 * @param fechaHora
	 * @param ticketCerrado
	 */
	public Ticket(int ticketId,LocalDateTime fechaHora, boolean ticketCerrado) {
		this.ticketId = ticketId;
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
		this.lineas = new ArrayList<>();
	}

	/**
	 * Método para añadir líneas de ticket al ticket
	 * @param objeto de la clase LineaTicket
	 */
	public void anadirLinea(LineaTicket linea) {
		lineas.add(linea);
	}


	/**
	 * @return the ticketId
	 */
	public int getTicketId() {
		return ticketId;
	}


	/**
	 * @param ticketId the ticketId to set
	 */
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}


	/**
	 * @return the fechaHora
	 */
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}


	/**
	 * @param fechaHora the fechaHora to set
	 */
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}


	/**
	 * @return the ticketCerrado
	 */
	public boolean isTicketCerrado() {
		return ticketCerrado;
	}


	/**
	 * @param ticketCerrado the ticketCerrado to set
	 */
	public void setTicketCerrado(boolean ticketCerrado) {
		this.ticketCerrado = ticketCerrado;
	}


	/**
	 * @return the lineas
	 */
	public List<LineaTicket> getLineas() {
		return lineas;
	}


	/**
	 * @param lineas the lineas to set
	 */
	public void setLineas(List<LineaTicket> lineas) {
		this.lineas = lineas;
	}
	




	
}
