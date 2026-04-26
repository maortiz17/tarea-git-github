package tarea.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;



public class Ticket {
	private long id;
	private Timestamp fechaHora;
	private String ticketCerrado;
	private List<LineaTicket> lineaTicket;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
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
	public String getTicketCerrado() {
		return ticketCerrado;
	}
	/**
	 * @return the lineaTicket
	 */
	public List<LineaTicket> getLineaTicket() {
		return lineaTicket;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param fechaHora the fechaHora to set
	 */
	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}
	/**
	 * @param ticketCerrado the ticketCerrado to set
	 */
	public void setTicketCerrado(String ticketCerrado) {
		this.ticketCerrado = ticketCerrado;
	}
	/**
	 * @param lineaTicket the lineaTicket to set
	 */
	public void setLineaTicket(List<LineaTicket> lineaTicket) {
		this.lineaTicket = lineaTicket;
	}
	
	public Ticket(long id, Timestamp fechaHora, String ticketCerrado, List<LineaTicket> lineaTicket) {

		this.id = id;
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
		this.lineaTicket = lineaTicket;
	}
	
	public Ticket( Timestamp fechaHora, String ticketCerrado, List<LineaTicket> lineaTicket) {
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
		this.lineaTicket = lineaTicket;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return id == other.id;
	}
	/*
	 * Metodo que devuelve true si el ticket esta cerrado 
	 * */
	public boolean estado() {
		return this.ticketCerrado.equals("T");
	}
}
