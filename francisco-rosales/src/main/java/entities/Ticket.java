package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Ticket {
	private long id;
	private LocalDateTime fechaHora;
	private boolean ticketCerrado;
	private List<LineaTicket> lineaTicket;
	
	public Ticket(LocalDateTime fechaHora, boolean ticketCerrado, List<LineaTicket> lineaTicket) {
		super();
		this.fechaHora = fechaHora;
		this.ticketCerrado = ticketCerrado;
		this.lineaTicket = lineaTicket;
	}
	
	public Ticket(long id, LocalDateTime fechaHora, boolean ticketCerrado, List<LineaTicket> lineaTicket) {
		this(fechaHora,ticketCerrado,lineaTicket);
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public boolean isTicketCerrado() {
		return ticketCerrado;
	}
	
	public List<LineaTicket> getLineaTicket() {
		return lineaTicket;
	}

	@Override
	public String toString() {
		return "Ticket id: " + id + ". Fecha: " + fechaHora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ". Estado " 
	+ (ticketCerrado ? "cerrado" : "abierto") + ".";
	}
	
	
}
