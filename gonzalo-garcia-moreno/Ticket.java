package tarea.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
	
	private long id;
	private LocalDateTime fechaHora;
	private boolean ticketCerrado;
	private List<LineaTicket> lineas;
	
	public Ticket(long id, LocalDateTime fechaHora, boolean ticketCerrado) {
		
		this.id = id;
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
		this.lineas = new ArrayList<LineaTicket>();
	}

	public Ticket(LocalDateTime fechaHora, boolean ticketCerrado) {
		
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
		this.lineas = new ArrayList<LineaTicket>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public boolean isTicketCerrado() {
		return ticketCerrado;
	}

	public void setTicketCerrado(boolean ticketCerrado) {
		this.ticketCerrado = ticketCerrado;
	}

	public List<LineaTicket> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaTicket> lineas) {
		this.lineas = lineas;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", fechaHora=" + fechaHora + ", ticketCerrado=" + ticketCerrado + ", lineas="
				+ lineas + "]";
	}
	
	
	
	
	
	
	

}
