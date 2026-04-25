package dao;

import java.util.List;

import entities.Producto;

public interface InterfazProductoDAO {

	List<Producto> getAll();
	
	Producto getById(int id);
	
	void insert(Producto p);
	
	void delete(int id);
	
}
