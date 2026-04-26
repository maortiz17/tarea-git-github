package tarea.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tarea.dao.IProductoDAO;
import tarea.entities.Producto;

public class ProductoDAOOracle implements IProductoDAO {
	public Producto crear(Connection con, String barcode, String nombre, double precio) throws SQLException {
		String query = "INSERT INTO producto (barcode, nombre, precio) VALUES (?, ?, ?)";
		String[] pk = { "id" };
		// En la llamada al método prepareStatement pasamos un Array con los campos que
		// componen la PD de la tabla
		// Así tendremos disponible el método getGeneratedKeys con la PK generada en el
		// INSERT
		try (PreparedStatement pstmt = con.prepareStatement(query, pk)) {
			pstmt.setString(1, barcode);
			pstmt.setString(2, nombre);
			pstmt.setDouble(3, precio);

			int filasAfectadas = pstmt.executeUpdate();

			if (filasAfectadas > 0) {
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						long id = generatedKeys.getLong(1);
						return new Producto(id, barcode, nombre, precio);
					}
				}
			}
		}
		return null;
	}

	public Producto buscar(Connection con, long id) throws SQLException {
		String query = "SELECT id, barcode, nombre, precio FROM producto WHERE id = ?";
		// Devolvemos un objeto Producto cuyo id se recibe por parámetro
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setLong(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Producto(rs.getLong("id"), rs.getString("barcode"), rs.getString("nombre"),
							rs.getDouble("precio"));
				}
			}
		}
		return null;
	}

	public List<Producto> listar(Connection con) throws SQLException {
		String query = "SELECT id, barcode, nombre, precio FROM producto";
		List<Producto> productos = new ArrayList<>();
		// Creamos un ArrayList de objetos Producto con todos los productos de la base
		// de datos
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					productos.add(new Producto(rs.getLong("id"), rs.getString("barcode"), rs.getString("nombre"),
							rs.getDouble("precio")));
				}
			}
		}
		return productos;
	}

	public Producto modificar(Connection con, long id, String barcode, String nombre, double precio)
			throws SQLException {
		String query = "UPDATE producto SET barcode = ?, nombre = ?, precio = ? WHERE id = ?";
		// Actualizamos el producto con el id recibido como parámetro con el resto de
		// parámetros
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, barcode);
			pstmt.setString(2, nombre);
			pstmt.setDouble(3, precio);
			pstmt.setLong(4, id);

			if (pstmt.executeUpdate() > 0) {
				return new Producto(id, barcode, nombre, precio);
			}
		}
		return null;
	}

	public boolean borrar(Connection con, long id) throws SQLException {
		String query = "DELETE FROM producto WHERE id = ?";
		// Eliminamos el producto cuyo id se recibe como parámetro
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setLong(1, id);

			return pstmt.executeUpdate() > 0;
		}
	}
}
