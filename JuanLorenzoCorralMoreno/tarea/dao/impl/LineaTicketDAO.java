package tarea.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tarea.dao.ILineaTicketDAO;
import tarea.entities.LineaTicket;

public class LineaTicketDAO implements ILineaTicketDAO {
	private static final String selectById = "Select * from lineaTicket where id = ?";
	private static final String insert = "Insert into lineaTicket (cantidad,precioventa,producto_id,ticket_id) values (?,?,?,?)";
	private static final String selectByIdTicket = "Select CANTIDAD, PRECIOVENTA, PRODCUTO_ID FROM lineaTicket where ticket_id = ?";
	String[] pk = {"ID"};
	/*
	 * Inserccion de una nueva linea de ticket
	 * */
	@Override
	public LineaTicket crear(Connection con, long cantidad, double precioVenta, long productoID, long ticketId)
			throws SQLException {
		try (PreparedStatement pstmnt = con.prepareStatement(insert, pk)) {
			pstmnt.setLong(1, cantidad);
			pstmnt.setDouble(2, precioVenta);
			pstmnt.setLong(3, productoID);
			pstmnt.setLong(4, ticketId);
			if (pstmnt.executeUpdate() > 0) {
				try (ResultSet generatedKeys = pstmnt.getGeneratedKeys()) {
					if(generatedKeys.next()) {
					long id = generatedKeys.getLong(1);
					return new LineaTicket(id, cantidad, precioVenta, productoID, ticketId);
					}
				}
			}
		}
		return null;
	}
	/*
	 * Busqueda de una linea de ticket en la bd por id
	 * */
	@Override
	public LineaTicket buscar(Connection con, long ticketId) throws SQLException {
		try(PreparedStatement pstmnt = con.prepareStatement(selectById)) {
			pstmnt.setLong(1, ticketId);
			try(ResultSet rs = pstmnt.executeQuery()){
				if (rs.next()) {
					return new LineaTicket(rs.getLong("cantidad"), rs.getDouble("precioventa"), rs.getLong("producto_id"),rs.getLong("ticket_id"));
				}
			}	
		}
		return null;
	}
	/*
	 * Metodo para recuperar una list de lineaticket dado una idTicket ( no se utiliza en el programa 
	 * se decidio realizar un metodo en ticketDao que ya devuelve este campo como parte del ticket al que
	 * que corresponde
	 * */
	@Override
	public List<LineaTicket> recuperar(Connection con, long idTicket) throws SQLException {
		List<LineaTicket> list = new ArrayList<>();
		try(PreparedStatement pstmnt = con.prepareStatement(selectByIdTicket)){
			pstmnt.setLong(1, idTicket);
			try(ResultSet rs = pstmnt.executeQuery()){
				while(rs.next()) {
					list.add(new LineaTicket(rs.getLong("CANTIDAD"), rs.getDouble("PRECIOVENTA"), rs.getLong("PRODCUTO_ID"), idTicket));
				}
			return list;
			}
		}
	}
	
	/*
	 * Metodo que nos devuelve las distintas id de productos que han tenido alguna venta
	 * usado para evitar borrar productos con historico de ventas
	 * */
	
	@Override
	public List<Long> listarId(Connection con) throws SQLException {
		String query = ("Select distinct(PRODUCTO_ID) as id_distintos from lineaTicket");
		List<Long> idProductos = new ArrayList<>();
		try (PreparedStatement pstmnt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmnt.executeQuery()) {
				while (rs.next()) {
					idProductos.add(rs.getLong("id_distintos"));
				}
				return idProductos;
			}
		}
	}
	
}
