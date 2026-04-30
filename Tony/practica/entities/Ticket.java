package practica.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
	
	private long idTicket;
	private LocalDateTime fechaHora;
	private String ticketCerrado;
	private List<LineaTicket> lineas;
	
	
	public Ticket(LocalDateTime fechaHora, String ticketCerrado) {
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
	}

	public Ticket(long idTicket, LocalDateTime fechaHora, String ticketCerrado) {
		this.idTicket = idTicket;
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
		this.lineas = new ArrayList<>();
	}

	public long getIdTicket() {
		return idTicket;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public String getTicketCerrado() {
		return ticketCerrado;
	}
	
	public List<LineaTicket> getLineas() {
		return lineas;
	}

	@Override
	public String toString() {
		return idTicket + " - " + fechaHora + " - " + ticketCerrado;
	}

	
	
	
	

}
