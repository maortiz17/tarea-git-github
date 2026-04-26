package tienda;

public class Producto {
	private int id;
	private String barcode;
	private String nombre;
	private float precio;
	
	
	public Producto(int id, String barcode, String nombre, float precio) {
		this.barcode = barcode;
		this.id= id;
		this.nombre = nombre;
		this.precio = precio;
	}


	@Override
	public String toString() {
		return "Producto [id=" + id + ", barcode=" + barcode + ", nombre=" + nombre + ", precio=" + precio + "]";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getBarcode() {
		return barcode;
	}


	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public float getPrecio() {
		return precio;
	}


	public void setPrecio(float precio) {
		this.precio = precio;
	}

}
