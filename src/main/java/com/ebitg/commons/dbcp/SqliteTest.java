package com.ebitg.commons.dbcp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;

public class SqliteTest {
	private static final String DRIVER = "org.sqlite.JDBC";
	private static final String URL = "jdbc:sqlite:test.db";

	@Test
	@Ignore
	public void test01() throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		String sql = "select * from MyTable";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet resultSet = pstmt.executeQuery();
		while (resultSet.next()) {
			String c1 = resultSet.getString(1);
			System.out.println(c1);
		}
		closeAll(connection, pstmt, resultSet);
	}

	private void closeAll(Connection connection, PreparedStatement stmt, ResultSet resultSet) throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (connection != null) {
			connection.close();
		}
	}

	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(URL);
		return connection;
	}

	@Test
	public void test02() throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		String sql = "CREATE TABLE COMPANY " + "(ID INT PRIMARY KEY     NOT NULL,"
				+ " NAME           TEXT    NOT NULL, " + " AGE            INT     NOT NULL, "
				+ " ADDRESS        CHAR(50), " + " SALARY         REAL)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		int result = pstmt.executeUpdate();
		System.out.println(result);
		closeAll(connection, pstmt, null);
	}
}
