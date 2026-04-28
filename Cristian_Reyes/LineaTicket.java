package tarea.entities;

public class LineaTicket {
	
	private int id;
	private int cantidad;
	private double precioVenta;
	private Producto p;
	private int idTicket;
	private boolean esNueva= false;
	
	public LineaTicket (int cantidad, double precioVenta, Producto p)
	{
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.p = p;
		this.esNueva=true;
	}

	
	public LineaTicket(int id, int cantidad, double precioVenta, Producto p, int idTicket) {
		this.id = id;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.p=p;
		this.idTicket = idTicket;
	}


	public boolean isEsNueva() {
		return esNueva;
	}


	public void setEsNueva(boolean esNueva) {
		this.esNueva = esNueva;
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


	public double getPrecioVenta() {
		return precioVenta;
	}


	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}


	public Producto getProducto() {
		return p;
	}


	public void setProducto(Producto p) {
		this.p = p;
	}


	public int getIdTicket() {
		return idTicket;
	}


	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}


	@Override
	public String toString() {
		return "Línea [Producto: " +p.getNombre() + ", Cantidad: " + cantidad + ", Precio: " + precioVenta + "]";
	}
	
	
	
	

}
