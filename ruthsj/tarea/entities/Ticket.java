package tarea.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
	
	private long id;
	private LocalDateTime fechaHora;
	private String ticketCerrado;
	private List<LineaTicket> lineas;
	
	public Ticket(LocalDateTime fechaHora, String ticketCerrado) {
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
		this.lineas = new ArrayList<>();
	}
	
	public Ticket(long id, LocalDateTime fechaHora, String ticketCerrado) {
		this.id = id;
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
		this.lineas = new ArrayList<>();
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getTicketCerrado() {
		return ticketCerrado;
	}

	public void setTicketCerrado(String ticketCerrado) {
		this.ticketCerrado = ticketCerrado;
	}

	public List<LineaTicket> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaTicket> lineas) {
		this.lineas = lineas;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("Ticket #%d  Fecha: %s  Estado: %s\n", 
				id, 
				fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
				ticketCerrado.equals("T") ? "Cerrado" : "Abierto");
	}
	
}
