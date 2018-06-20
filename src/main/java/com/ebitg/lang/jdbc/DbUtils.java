package com.ebitg.lang.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		String className = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/lj";
		String username = "root";
		String pwd = "root";
		Class.forName(className);
		Connection conn = DriverManager.getConnection(url, username, pwd);
		return conn;
	}
}
