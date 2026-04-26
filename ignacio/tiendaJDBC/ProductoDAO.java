package tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
	Connection con;

	public ProductoDAO(Connection con) {
		this.con = con;
	}

	private String SELECT_ALL = "SELECT * FROM PRODUCTO";
	private String SELECT_ID = "SELECT * FROM PRODUCTO WHERE ID = ?";
	private String DELETE = "DELETE FROM PRODUCTO WHERE ID = ?";
	private String INSET = "INSERT INTO PRODUCTO(BARCODE,NOMBRE,PRECIO) VALUES(?,?,?)";
	private String UPDATE = "UPDATE PRODUCTO SET BARCODE = ?,NOMBRE = ? ,PRECIO =? WHERE ID = ?";
	// ----SELECTS---------------
	//ALL
	public List<Producto> getAll() throws SQLException {
		List<Producto> productos = new ArrayList<>();
		try (PreparedStatement stm = con.prepareStatement(SELECT_ALL); ResultSet rs = stm.executeQuery();) {

			while (rs.next()) {
				productos.add(new Producto(rs.getInt("ID"), rs.getString("BARCODE"), rs.getString("NOMBRE"),
						rs.getFloat("PRECIO")));
			}
		}
		return productos;
	}
// Por id
	public Producto getById(int Id) throws SQLException {
		try (PreparedStatement stm = con.prepareStatement(SELECT_ID)) {
			stm.setInt(1, Id);
			try (ResultSet rs = stm.executeQuery()) {
				if (rs.next()) {
					return new Producto(rs.getInt("ID"), rs.getString("BARCODE"), rs.getString("NOMBRE"),
							rs.getFloat("PRECIO"));
				}

				else {
					return null;
				}
			}
		}

	}
	//_____________________DELETE----------
	public boolean delete(int ID) throws SQLException{
		try(PreparedStatement stm = con.prepareStatement(DELETE)){
			stm.setInt(1, ID);
			int registro = stm.executeUpdate();
			if(registro > 0) {return true;}
			else {return false;}
		}
	}
	//--------------INSERT --------------
	public boolean insert(String barcode, String nombre, float precio) throws SQLException{
		try(PreparedStatement stm = con.prepareStatement(INSET)){
			stm.setString(1, barcode);
			stm.setString(2, nombre);
			stm.setFloat(3, precio);
			return stm.executeUpdate() > 0;
		}
	}
	//-----UPDATE-----
	public boolean update(int Id,String barcode, String nombre, float precio) throws SQLException{
		try(PreparedStatement stm = con.prepareStatement(UPDATE)){
			stm.setInt(4, Id);
			stm.setString(1, barcode);
			stm.setString(2, nombre);
			stm.setFloat(3, precio);
			return stm.executeUpdate() > 0;
		}
	}
}
