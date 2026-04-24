package entities;

import java.util.Objects;

public class Producto {
	
	private int id;
	private String barcode;
	private String nombre;
	private double precio;
	
	
	/**
	 * @param barcode
	 * @param nombre
	 * @param precio
	 */
	public Producto(int id, String barcode, String nombre, double precio) {
		this.id = id;
		this.barcode = barcode;
		this.nombre = nombre;
		this.precio = precio;
	}
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}


	@Override
	public int hashCode() {
		return Objects.hash(barcode);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(barcode, other.barcode);
	}


	@Override
	public String toString() {
		return "Producto [barcode=" + barcode + ", nombre=" + nombre + ", precio=" + precio + "]";
	}
	
	
	
}
