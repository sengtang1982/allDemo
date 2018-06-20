package com.ebitg.lang.jdbc;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 两个线程 通过id 对同一行加锁会等待锁释放
 * 
 * @author 王波
 *
 */
public class JdbcTest13 {
	public static void main(String[] args) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "启动");
		Connection conn = null;
		try {
			conn = DbUtils.getConnection();
			conn.setAutoCommit(false);
			queryAll(conn);
		} catch (ClassNotFoundException | SQLException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void queryAll(Connection conn) throws ClassNotFoundException, SQLException, InterruptedException {
		String sql = "select id,name1 n1,name2,updateTime,round(updateCount) updateCount from T1 limit 5 ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ResultSetMetaData metaData = rs.getMetaData();

		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			String columnName = metaData.getColumnName(i);
			String columnLabel = metaData.getColumnLabel(i);
			int columnType = metaData.getColumnType(i);
			String columnClassName = metaData.getColumnClassName(i);
			System.out.println("columnName:" + columnName + ",columnLabel:" + columnLabel + ",columnType:" + columnType
					+ ",columnClassName" + columnClassName);
		}
		System.out.println("..............");
	}
}