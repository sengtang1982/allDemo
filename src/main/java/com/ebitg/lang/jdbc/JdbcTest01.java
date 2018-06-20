package com.ebitg.lang.jdbc;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import com.gargoylesoftware.htmlunit.javascript.host.file.FileReader;

public class JdbcTest01 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		Connection connection = DbUtils.getConnection();
		connection.setAutoCommit(false);
		String sql = "insert into t1(name1,name2) " + "values(?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		for (int i = 100; i < 1000; i++) {
			stmt.setString(1, "col1_" + i);
			stmt.setString(2, "col2_" + i);
			stmt.addBatch();
		}
		int[] result = stmt.executeBatch();
		for (int i = 0; i < result.length; i++) {
			System.out.println("第" + i + "的结果:" + result[i]);
		}
		connection.commit();
		
		
	}
}
