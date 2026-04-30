package practica.entities;

public class LineaTicket {
	
	private long idLineaTicket;
	private int cantidad;
	private double precioVenta;
	private long productoId;
	private long ticketId;
	
	public LineaTicket(long idLineaTicket, int cantidad, double precioVenta, long productoId, long ticketId) {
		this.idLineaTicket = idLineaTicket;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.productoId = productoId;
		this.ticketId = ticketId;
	}

	public long getIdLineaTicket() {
		return idLineaTicket;
	}

	public int getCantidad() {
		return cantidad;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public long getProductoId() {
		return productoId;
	}

	public long getTicketId() {
		return ticketId;
	}
	
	@Override
	public String toString() {
		return idLineaTicket + " - " + cantidad + " - " + precioVenta + "€ - " + productoId + " - " + ticketId;
	}
	
	
	
	

}
