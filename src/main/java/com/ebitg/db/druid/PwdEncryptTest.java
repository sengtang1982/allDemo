package com.ebitg.db.druid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.druid.pool.DruidDataSource;

public class PwdEncryptTest {
	public static void main(String[] args) throws SQLException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		DataSource ds = (DruidDataSource) ctx.getBean("dataSource");
		Connection conn = ds.getConnection();
		PreparedStatement stmt = conn.prepareStatement("select 1234 from dual");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int r = rs.getInt(1);
			System.out.println(r);
		}
		conn.close();
		System.out.println(ds);
	}
}
