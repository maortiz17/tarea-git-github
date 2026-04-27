package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.LineaTicket;
import entities.Producto;

public class LineaTicketDAO implements ILineaTicketDAO {

	@Override
	public void insertar(Connection conn, LineaTicket linea, long idTicket) throws SQLException {
		String sql = "INSERT INTO LINEATICKET (CANTIDAD, PRECIOVENTA, PRODUCTO_ID, TICKET_ID) VALUES (?,?,?,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1,  linea.getCantidad());
			ps.setDouble(2, linea.getPrecioVenta());
			ps.setLong(3, linea.getProducto().getId());
			ps.setLong(4,  idTicket);
			ps.executeUpdate();
		}
		
	}

	@Override
    public List<LineaTicket> listarPorTicket(Connection con, long idTicket) throws SQLException {
        String query = "SELECT l.*, P.BARCODE, P.NOMBRE, P.PRECIO FROM LINEATICKET L  JOIN PRODUCTO P ON  L.PRODUCTO_ID = P.ID WHERE TICKET_ID = ?";
        List<LineaTicket> lineas = new ArrayList<>();
        
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, idTicket);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                	
                	Producto p = new Producto(
                			
                			rs.getLong("PRODUCTO_ID"),
                			rs.getString("BARCODE"),
                			rs.getString("NOMBRE"),
                			rs.getDouble("PRECIO"));
                   
                    lineas.add(new LineaTicket(
                        rs.getLong("ID"),
                        rs.getInt("CANTIDAD"),
                        rs.getDouble("PRECIOVENTA"),
                        p 
                    ));
                }
            }
        }
        return lineas;
    }

}
