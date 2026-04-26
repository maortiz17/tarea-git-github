package tienda;

public class LineaTicket {
	private int id;
	private int cantidad;
	private float precioVenta;
	private int productoId;
	private int ticketId;
	
	public LineaTicket(int id, int cantidad, float precioVenta, int productoId, int ticketId) {
		this.id = id;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.productoId = productoId;
		this.ticketId = ticketId;
	}
	
	@Override
	public String toString() {
		return "LineaTicket [id=" + id + ", cantidad=" + cantidad + ", precioVenta=" + precioVenta + ", productoId="
				+ productoId + ", ticketId=" + ticketId + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(float precioVenta) {
		this.precioVenta = precioVenta;
	}

	public int getProductoId() {
		return productoId;
	}

	public void setProductoId(int productoId) {
		this.productoId = productoId;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	
}
