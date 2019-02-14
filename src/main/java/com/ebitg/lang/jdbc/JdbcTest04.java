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
public class JdbcTest04 {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new T4_1());
		Thread t2 = new Thread(new T4_2());
		t1.start();
		Thread.sleep(1000);
		t2.start();
	}
}

class T4_1 implements Runnable {
	@Override
	public void run() {
		System.out.println("线程1启动");
		try {
			Connection conn = DbUtils.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from T1 where name1='col1_0' for update";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String col1 = rs.getString(2);
				String col2 = rs.getString(3);
				System.out.println(id + "," + col1 + "," + col2);
			}
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

class T4_2 implements Runnable {
	@Override
	public void run() {
		System.out.println("线程2启动");
		try {
			Connection conn = DbUtils.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from T1 where name1='col1_0' for update";
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
