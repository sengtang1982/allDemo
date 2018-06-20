package com.ebitg.lang.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.JdbcType;

public class JdbcTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection conn = DbUtils.getConnection();
		String sql = "select id, foodName, foodPrice, foodUnit FoodUNIT1 from FOOD ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.execute();

		ResultSet rs = stmt.getResultSet();

		while (rs == null) {
			if (stmt.getMoreResults()) {
				rs = stmt.getResultSet();
			} else {
				if (stmt.getUpdateCount() == -1) {
					break;
				}
			}
		}
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		List<String> columnNames = new ArrayList<String>();
		List<JdbcType> jdbcTypes = new ArrayList<JdbcType>();
		List<String> classNames = new ArrayList<String>();
		for (int i = 1; i <= columnCount; i++) {
			columnNames.add(metaData.getColumnLabel(i) + "_" + metaData.getColumnName(i));
			jdbcTypes.add(JdbcType.forCode(metaData.getColumnType(i)));
			classNames.add(metaData.getColumnClassName(i));
		}
		conn.close();
		System.out.println("OVER");
		System.out.println(columnNames);
		System.out.println(jdbcTypes);
		System.out.println(classNames);
	}

}
