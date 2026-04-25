package entities;

import java.util.Objects;

public class Producto {
	
	private int productoId;
	private String barcode;
	private String nombre;
	private double precio;
	
	
	/**
	 * Constructor vacío para rellenarlo con el ResultSet
	 */
	public Producto() {};
	
	/**
	 * Constructor por defecto
	 * @param barcode
	 * @param nombre
	 * @param precio
	 */
	public Producto(int productoId, String barcode, String nombre, double precio) {
		this.productoId = productoId;
		this.barcode = barcode;
		this.nombre = nombre;
		this.precio = precio;
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

	
	/**
	 * @return the id
	 */
	public int getId() {
		return productoId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int productoId) {
		this.productoId = productoId;
	}

	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	
	
	
	@Override
	public String toString() {
		return "Producto [barcode=" + barcode + ", nombre=" + nombre + ", precio=" + precio + "]";
	}
	
	
	
}
