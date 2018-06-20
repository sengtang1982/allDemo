package com.ebitg.lang.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 两个线程 通过id 对同一行加锁会等待锁释放
 * 
 * @author 王波
 *
 */
public class JdbcTest7 {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new T7_1());
		Thread t2 = new Thread(new T7_2());
		t1.start();
		Thread.sleep(1000);
		t2.start();
	}
}

class T7_1 implements Runnable {
	@Override
	public void run() {
		System.out.println("线程1启动");
		try {
			Connection conn = DbUtils.getConnection();
			conn.setAutoCommit(false);
			String sql = "update T1 set updateTime=CURRENT_TIME() where id=1";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int row = stmt.executeUpdate();
			System.out.println("更新结果" + row);
			Thread.sleep(10000);
			conn.commit();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class T7_2 implements Runnable {
	@Override
	public void run() {
		System.out.println("线程2启动");
		try {
			Connection conn = DbUtils.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from T1 where updateTime is null ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String col1 = rs.getString(2);
				String col2 = rs.getString(3);
				System.out.println(id + "," + col1 + "," + col2);
			}
			conn.commit();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
