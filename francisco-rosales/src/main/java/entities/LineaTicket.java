package entities;

public class LineaTicket {
	private long id;
	private int cantidad;
	private double precioVenta;
	private Producto producto;
	
	public LineaTicket(int cantidad, double precioVenta, Producto producto) {
		super();
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.producto = producto;
	}
	
	public LineaTicket(long id, int cantidad, double precioVenta, Producto producto) {
		this(cantidad, precioVenta, producto);
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

	public Producto getProducto() {
		return producto;
	}

	@Override
	public String toString() {
		return String.format("(%d) Cantidad: %d. Precio: %.2f. %s", id, cantidad, precioVenta, producto.toString());
	}
	
	
	
	
}
