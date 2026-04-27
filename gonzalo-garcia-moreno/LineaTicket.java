package tarea.entities;

public class LineaTicket {
	private long id;
	private int cantidad;
	private double precioVenta;
	private Producto producto;
	
	public LineaTicket(long id, int cantidad, double precioVenta, Producto producto) {
		
		this.id = id;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.producto = producto;
	}

	public LineaTicket(int cantidad, double precioVenta, Producto producto) {
		
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.producto = producto;
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "LineaTicket [id=" + id + ", cantidad=" + cantidad + ", precioVenta=" + precioVenta + ", producto="
				+ producto + "]";
	}
	
	
	
	
	

}
