package entities;

import java.util.List;

public class Producto{
    private long id;
    private String barcode;
    private String nombre;
    private double precio;
    

    public Producto(String barcode, String nombre, double precio) {
        this.barcode = barcode;
        this.nombre = nombre;
        this.precio = precio;

    }

    public Producto(long id, String barcode, String nombre, double precio) {
        this(barcode, nombre, precio);
        this.id = id;
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

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return String.format("%s(%d):\t%s\t%.2f€", nombre, id, barcode, precio);
    }
    
}
