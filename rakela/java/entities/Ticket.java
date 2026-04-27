package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
	
	private long id;
	private LocalDateTime fechaHora;
	private String ticketCerrado;
	private List<LineaTicket> lineas;
	
	public Ticket(long id, LocalDateTime fechaHora, String ticketCerrado, List<LineaTicket> lineas) {
		
		this.id = id;
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
		this.lineas = lineas;
	}
	
	public Ticket() {
		this.fechaHora = LocalDateTime.now();
		this.ticketCerrado = "F";
		this.lineas = new ArrayList<>();
	}

	public long getId() {
		return id;
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
	
	
	

	public void setId(long id) {
		this.id = id;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public void setTicketCerrado(String ticketCerrado) {
		this.ticketCerrado = ticketCerrado;
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
