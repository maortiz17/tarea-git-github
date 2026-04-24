package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XEPDB1";
	private static final String USER = "SAKILA";
	private static final String PASS = "oracle123";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
