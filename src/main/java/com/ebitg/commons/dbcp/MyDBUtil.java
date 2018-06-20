package com.ebitg.commons.dbcp;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

public class MyDBUtil {
	public static void main(String[] args) {
		MyDBUtil myDBUtil = new MyDBUtil();
		String sql = "select * from department";
		myDBUtil.query(sql, null, new MyDbCallback() {
			@Override
			public void execute(ResultSet rs) {
				try {
					while (rs.next()) {
						System.out.println(rs.getString(1) + rs.getString(2));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	static DataSource ds;
	static {
		try {
			InputStream in = MyDBUtil.class.getClassLoader().getResourceAsStream("db_mysql.properties");
			Properties dsProps = new Properties();
			dsProps.load(in);
			ds = BasicDataSourceFactory.createDataSource(dsProps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			Connection connection = ds.getConnection();
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void query(String sql, Object[] params, MyDbCallback callBack) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					stmt.setObject(i + 1, params[i]);
				}
			}
			rs = stmt.executeQuery();
			callBack.execute(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, stmt, conn);
		}
	}

	public int update(String sql, Object[] params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int effectRow;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					stmt.setObject(i + 1, params[i]);
				}
			}
			effectRow = stmt.executeUpdate();
			return effectRow;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(null, stmt, conn);
		}
		return -1;
	}

	public void closeAll(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
