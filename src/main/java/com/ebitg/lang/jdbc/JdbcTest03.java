package com.ebitg.lang.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * �����߳� ͨ��id ��ͬһ�м�����ȴ����ͷ�
 * 
 * @author ����
 *
 */
public class JdbcTest03 {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new T3_1());
		Thread t2 = new Thread(new T3_2());
		t1.start();
		Thread.sleep(1000);
		t2.start();
	}
}

class T3_1 implements Runnable {
	@Override
	public void run() {
		System.out.println("�߳�1����");
		try {
			Connection conn = DbUtils.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from T1 where id=12 for update";
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

class T3_2 implements Runnable {
	@Override
	public void run() {
		System.out.println("�߳�2����");
		try {
			Connection conn = DbUtils.getConnection();
			conn.setAutoCommit(false);
			String sql = "select * from T1 where id=13 for update";
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