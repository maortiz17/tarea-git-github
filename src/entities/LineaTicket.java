package entities;


public class LineaTicket {
	
	private int lineaId;
	private int cantidad;
	private double precioVenta;
	private Producto producto;
	private Ticket ticket;
	
	
	/**
	 * Constructor vacío para rellenarlo con el ResultSet
	 */
	public LineaTicket() {};
	
	
	/**
	 * @param cantidad
	 * @param precioVenta
	 * @param productoId
	 * @param ticketId
	 */
	public LineaTicket(int cantidad, double precioVenta, Producto producto) {
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.producto = producto;
    }


	/**
	 * @return the lineaId
	 */
	public int getLineaId() {
		return lineaId;
	}


	/**
	 * @param lineaId the lineaId to set
	 */
	public void setLineaId(int lineaId) {
		this.lineaId = lineaId;
	}


	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}


	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	/**
	 * @return the precioVenta
	 */
	public double getPrecioVenta() {
		return precioVenta;
	}


	/**
	 * @param precioVenta the precioVenta to set
	 */
	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}


	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}


	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	/**
	 * @return the ticket
	 */
	public Ticket getTicket() {
		return ticket;
	}


	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}



}
