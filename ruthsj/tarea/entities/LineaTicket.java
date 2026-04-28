package tarea.entities;

public class LineaTicket {
	private long id;
	private int cantidad;
	private double precioVenta;
	private long productoId;
	private long ticketId;
	
	public LineaTicket(int cantidad, double precioVenta, long productoId, long ticketId) {
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.productoId = productoId;
		this.ticketId = ticketId;
	}
	
	public LineaTicket(long id, int cantidad, double precioVenta, long productoId, long ticketId) {
		this.id = id;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.productoId = productoId;
		this.ticketId = ticketId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public long getProductoId() {
		return productoId;
	}

	public void setProductoId(long productoId) {
		this.productoId = productoId;
	}

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	@Override
	public String toString() {
		return String.format("\tProducto #%d  Cantidad: %d  Precio unidad: %.2f€\t\t%.2f€", 
				productoId,
				cantidad,
				precioVenta,
				cantidad * precioVenta);
	}
	

}
