package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Producto;
import util.ConexionDB;

public class ProductoDAO implements InterfazProductoDAO{

	/**
	 * Método para listar todos los productos de la BD
	 * @return lista con todos los productos 
	 */
	@Override
	public List<Producto> getAll() {
		
		List<Producto> productos = new ArrayList<>();
		
		String sql = "SELECT * FROM PRODUCTO";
		
		try (Connection con = ConexionDB.getConnection()) {
			
			PreparedStatement ps = con.prepareStatement(sql); {
				
				ResultSet rs = ps.executeQuery(sql); {
					
					while(rs.next()) {
						Producto p = new Producto();
						p.setId(rs.getInt("ID"));
						p.setBarcode(rs.getString("BARCODE"));
						p.setNombre(rs.getString("NOMBRE"));
						p.setPrecio(rs.getDouble("PRECIO"));
						
						productos.add(p);
					}
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return productos;
	}
	
	
	
	/**
	 * Método para buscar un producto por su ID
	 * @param ID del producto
	 * @return producto basado en una ID
	 */
	@Override
	public Producto getById(int id) {	
		
		Producto p = new Producto();
		
		String sql = "SELECT * FROM PRODUCTO WHERE ID = ?";
		
		try (Connection con = ConexionDB.getConnection()) {
			
			PreparedStatement ps = con.prepareStatement(sql); {
				
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery(); 
				
				//si existe, lo llenamos
				if (rs.next()) {
					p.setId(rs.getInt("ID"));
					p.setBarcode(rs.getString("BARCODE"));
					p.setNombre(rs.getString("NOMBRE"));
					p.setPrecio(rs.getDouble("PRECIO"));
				} else {
					p = null; //y si no lo dejamos vacío
				}	
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return p;
	}
	
	
	/**
	 * Método para insertar un nuevo prodcuto en la tabla Producto de la BD
	 * @param objeto de la clase {@link Producto}
	 */
	@Override
	public void insert(Producto p) {
		
		String sql = """
				INSERT INTO PRODUCTO (BARCODE, NOMBRE, PRECIO)
				""";
		
		try (Connection con = ConexionDB.getConnection()) {
			
			PreparedStatement ps = con.prepareStatement(sql); {
				
				ps.setString(1, p.getBarcode());
				ps.setString(2, p.getNombre());
				ps.setDouble(3, p.getPrecio());
				
				ps.executeUpdate();
				System.out.println("Producto añadido correctamente.");
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Método para eliminar un producto de la tabla Producto de la BD mediante su ID
	 * @param ID del producto
	 */
	@Override
	public void delete (int id) {
		
		String sql = """
				DELETE FROM PRODUCTO WHERE ID = ?
				""";
		
		try (Connection con = ConexionDB.getConnection()) {
			
			PreparedStatement ps = con.prepareStatement(sql); {
				
				ps.setInt(1, id);
				ps.executeUpdate();
			}
			
		} catch (SQLException e) {
			System.out.println("No se pudo borrar el producto (puede tener ventas asociadas)");
		}
	}


	
}
