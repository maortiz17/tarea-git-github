package tarea.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tarea.entities.Producto;

public interface IProductoDAO {
    Producto crear(Connection con, String barcode, String nombre, double precio) throws SQLException;
    Producto buscar(Connection con, long id) throws SQLException;
    List<Producto> listar(Connection con) throws SQLException;
    Producto modificar(Connection con, long id, String barcode, String nombre, double precio) throws SQLException;
    boolean borrar(Connection con, long id) throws SQLException;
}	