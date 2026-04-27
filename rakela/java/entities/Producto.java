package entities;

public class Producto {

	
	private  long id;
	private  String barcode;
	private  String nombre;
	private  double precio;
	
	 public Producto(String barcode, String nombre, double precio) {
	        this.barcode = barcode;
	        this.nombre = nombre;
	        this.precio = precio;
	    }
	
	public Producto(long id, String barcode, String nombre, double precio) {
		
		this.id = id;
		this.barcode = barcode;
		this.nombre = nombre;
		this.precio = precio;
	}

	public long getId() {
		return id;
	}

	public String getBarcode() {
		return barcode;
	}

	public String getNombre() {
		return nombre;
	}

	public double getPrecio() {
		return precio;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", barcode=" + barcode + ", nombre=" + nombre + ", precio=" + precio + "]";
	}
	
	
	
	
	
	
	
}
