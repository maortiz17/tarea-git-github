package tarea.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
	
	private int id;
	private LocalDateTime fechaHora;
	private String ticketCerrado;
	
	private List<LineaTicket> lTicket;

	public Ticket()
	{
		this.fechaHora= LocalDateTime.now();
		this.ticketCerrado="F";
		this.lTicket = new ArrayList<>();
	}
	
	public Ticket (int id, LocalDateTime date, String ticketCerrado)
	{
		this.id = id;
		this.fechaHora = date;
		this.ticketCerrado= ticketCerrado;
		
		this.lTicket= new ArrayList<>();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	public String getTicketCerrado() {
		return ticketCerrado;
	}

	/**
	 * @param ticketCerrado the ticketCerrado to set
	 */
	public void setTicketCerrado(String ticketCerrado) {
		this.ticketCerrado = ticketCerrado;
	}

	/**
	 * @return the lTicket
	 */
	public List<LineaTicket> getlTicket() {
		return lTicket;
	}

	/**
	 * @param lTicket the lTicket to set
	 */
	public void setlTicket(List<LineaTicket> lTicket) {
		this.lTicket = lTicket;
	}
	
	public void anadirLinea(LineaTicket linea)
	{
		this.lTicket.add(linea);
		
	}

	@Override
	public String toString() {
		return "Ticket #" + id + " [" + fechaHora + "] Cerrado: " + ticketCerrado + " (Líneas: " + lTicket.size() + ")";
	}
	
	
	
	

}
