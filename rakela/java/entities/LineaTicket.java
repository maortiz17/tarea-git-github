package entities;

public class LineaTicket {
	
	private long id;
	private  int cantidad;
	private  double precioVenta;
	private  int productoId;
	private  int ticketId;
	private Producto producto;
	
	
	public LineaTicket(long id, int cantidad, double precioVenta, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.producto = producto;
    }

    // segundo constructor para las lineas, el id lo añade oracle
    public LineaTicket(int cantidad, double precioVenta, Producto producto) {
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.producto = producto;
        this.productoId = (int) producto.getId();
    }
    
    


	public Producto getProducto() {
		return producto;
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


	public int getProductoId() {
		return productoId;
	}


	public int getTicketId() {
		return ticketId;
	}


	@Override
	public String toString() {
	    return "Línea " + id + ": " + producto.getNombre() + " x" + cantidad + " - " + precioVenta + "€";
	}
	
	
	
	

}
