package tarea.entities;

import java.util.Objects;

public class LineaTicket {
	
	private long id;
	private long cantidad;
	private double precioVenta;
	private long productoId;
	private long ticketId;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the cantidad
	 */
	public long getCantidad() {
		return cantidad;
	}
	/**
	 * @return the precioVenta
	 */
	public double getPrecioVenta() {
		return precioVenta;
	}
	/**
	 * @return the productoId
	 */
	public long getProductoId() {
		return productoId;
	}
	/**
	 * @return the ticketId
	 */
	public long getTicketId() {
		return ticketId;
	}
	public LineaTicket(long id, long cantidad, double precioVenta, long productoId, long ticketId) {
		this.id = id;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.productoId = productoId;
		this.ticketId = ticketId;
	}
	public LineaTicket( long cantidad, double precioVenta, long productoId, long ticketId) {
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.productoId = productoId;
		this.ticketId = ticketId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineaTicket other = (LineaTicket) obj;
		return id == other.id;
	}
	@Override
	public String toString() {
		double total = cantidad*precioVenta;
		return String.format("\t\tp/u %.2f\t\tCantidad: %d\t\ttotal: \t%.2f\t*",precioVenta, cantidad,total);
	}
	
	
	
	
}
