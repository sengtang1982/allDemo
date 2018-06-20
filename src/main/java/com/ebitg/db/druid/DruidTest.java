package com.ebitg.db.druid;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidTest {
	@Test
	public void test01() throws Exception {
		Map<String, String> druidConfig = new HashMap<String, String>();
		druidConfig.put("driverClassName", "com.mysql.jdbc.Driver");
		druidConfig.put("url", "jdbc:mysql://localhost:3306/ecology_test");
		druidConfig.put("username", "root");
		druidConfig.put("password", "root");
		druidConfig.put("initialSize", "5");
		druidConfig.put("minIdle", "2");
		DruidDataSource ds = (DruidDataSource) DruidDataSourceFactory.createDataSource(druidConfig);
		Connection connection = ds.getConnection();
		Connection connection1 = ds.getConnection();
		System.out.println(ds);
		System.out.println(connection);
		System.out.println("当前空闲数:" + ds.getMinIdle());
		connection.close();
		Map<String, String> druidConfigNew = new HashMap<String, String>();
		druidConfig.put("minIdle", "10");
		DruidDataSourceFactory.config(ds, druidConfigNew);
		
		int minIdle = ds.getMinIdle();
		System.out.println("当前空闲数:" + minIdle);
		System.out.println(ds);
		System.out.println(connection);
		ds.close();
	}
}
