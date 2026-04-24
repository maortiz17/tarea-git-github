package entities;


public class LineaTicket {
	
	private int cantidad;
	private double precioVenta;
	private int productoId;
	private int ticketId;
	
	
	/**
	 * @param cantidad
	 * @param precioVenta
	 * @param productoId
	 * @param ticketId
	 */
	public LineaTicket(int cantidad, double precioVenta, int productoId, int ticketId) {
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.productoId = productoId;
		this.ticketId = ticketId;
	}


	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}


	/**
	 * @return the precioVenta
	 */
	public double getPrecioVenta() {
		return precioVenta;
	}




}
