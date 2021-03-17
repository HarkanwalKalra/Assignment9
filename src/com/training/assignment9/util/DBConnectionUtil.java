package com.training.assignment9.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
	static Connection connection = null;
	private DBConnectionUtil() {
		super();
	}
	static String driverClass = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/hk";
	static String username = "root";
	static String password = "root";
	
	public static Connection getConnection() {
		try {
			Class.forName(driverClass);
			connection = DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
