package tarea.entities;

public class LineaTicket {

	private long id;
	private int cantidad;
	private double precioVenta;
	long productoId;
	long ticketId;
	
	public LineaTicket(int cantidad, double precioVenta, long productoId, long ticketId) {
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.productoId = productoId;
		this.ticketId = ticketId;
	}
	public LineaTicket(long id, int cantidad, double precioVenta, long productoId, long ticketId) {
		this(cantidad, precioVenta, productoId, ticketId);
		this.id = id;
	}
	public long getId() {
		return id;
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
	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
