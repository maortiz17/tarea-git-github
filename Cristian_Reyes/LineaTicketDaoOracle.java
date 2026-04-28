package tarea.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tarea.dao.ILineaTicketDao;
import tarea.entities.LineaTicket;
import tarea.entities.Producto;

public class LineaTicketDaoOracle implements ILineaTicketDao{

	@Override
	public void insertar(Connection con, LineaTicket lTicket) throws SQLException {
		String query = "INSERT INTO LINEATICKET (CANTIDAD, PRECIOVENTA, PRODUCTO_ID, TICKET_ID) VALUES (?,?,?,?) ";
		
		try( PreparedStatement pSt = con.prepareStatement(query))
		{
			pSt.setInt(1, lTicket.getCantidad());
			pSt.setDouble(2, lTicket.getPrecioVenta());
			pSt.setInt(3, (int)lTicket.getProducto().getId());
			pSt.setInt(4, lTicket.getIdTicket());
			
			pSt.execute();
			
		}
	}

	@Override
	public List<LineaTicket> listarPorTicket(Connection con, int idTicket) throws SQLException {
		
		String queryLTicket = "SELECT * FROM LINEATICKET JOIN PRODUCTO ON PRODUCTO_ID = PRODUCTO.ID WHERE TICKET_ID =? ";
		
		
		List<LineaTicket> resultLineaTicket = new ArrayList<LineaTicket>();
		
		try(PreparedStatement pSt= con.prepareStatement(queryLTicket);)
		{
			pSt.setInt(1, idTicket);
			
			try(ResultSet rSet= pSt.executeQuery())
			{
				while (rSet.next())
				{
					int idLinea= rSet.getInt("ID");
					int cant=rSet.getInt("cantidad");
					double pVenta = rSet.getDouble("precioVenta");
					int idProd= rSet.getInt("PRODUCTO_ID");
					String barcode = rSet.getString("barcode");
					String nombre = rSet.getString("nombre");
					Double precio = rSet.getDouble("precio");
					Producto p =new Producto(idProd, barcode, nombre, precio);
					
					LineaTicket lTicket = new LineaTicket(idLinea, cant, pVenta, p, idTicket);
					resultLineaTicket.add(lTicket);
					
				}
			}
			return resultLineaTicket;
			
		}
	}

}
