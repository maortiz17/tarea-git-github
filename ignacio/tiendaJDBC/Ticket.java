package tienda;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

	private int id;
	private Timestamp fechahora;
	private boolean ticketcerrado;
	private List<LineaTicket> lineaTicket;

	// Constructores

	// constructor con ID para hacer consultas
	public Ticket(int id, Timestamp fechahora, boolean ticketcerrado) {
		this.fechahora = fechahora;
		this.id = id;
		this.ticketcerrado = ticketcerrado;
		this.lineaTicket = new ArrayList<>();
	}

	// constructor sin id para crear el ticket e ir rellenandolo
	public Ticket(Timestamp fechahora, boolean ticketcerrado) {
		this.fechahora = fechahora;
		this.ticketcerrado = ticketcerrado;
		this.lineaTicket = new ArrayList<>();
	}
	//metodos...
	public void añadirLinea(LineaTicket lt) {
		lineaTicket.add(lt);
	}
	public void borrarLinea(LineaTicket lt) {
		lineaTicket.remove(lt);
	}
	@Override
	public String toString() {
		return "ticket [id=" + id + ", fechahora=" + fechahora + ", ticketcerrado=" + ticketcerrado + ", lineaTicket="
				+ lineaTicket + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getFechahora() {
		return fechahora;
	}

	public void setFechahora(Timestamp fechahora) {
		this.fechahora = fechahora;
	}

	public boolean isTicketcerrado() {
		return ticketcerrado;
	}

	public void setTicketcerrado(boolean ticketcerrado) {
		this.ticketcerrado = ticketcerrado;
	}

	public List<LineaTicket> getLineaTicket() {
		return lineaTicket;
	}

	public void setLineaTicket(List<LineaTicket> lineaTicket) {
		this.lineaTicket = lineaTicket;
	}
}
