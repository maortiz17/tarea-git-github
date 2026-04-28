package tarea.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

	private long id;
	private LocalDateTime fechaHora;
	private String ticketCerrado;
	
	public Ticket(LocalDateTime fechaHora, String ticketCerrado) {
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
	}
	
	public Ticket(long id, LocalDateTime fechaHora, String ticketCerrado) {
		this(fechaHora, ticketCerrado);
		this.id = id;
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
	

	public void setId(long id) {
		this.id = id;
	}

	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = fechaHora.format(formatter);
		return String.format("%5d%25s", id, fechaFormateada);
	}

	
}
