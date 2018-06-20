package com.ebitg.lang.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * �����߳� ͨ��id ��ͬһ�м�����ȴ����ͷ�
 * 
 * @author ����
 *
 */
public class JdbcTest8 {
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new T8_1());
			t.start();
			Thread.sleep(100);
		}
	}
}

class T8_1 implements Runnable {
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "����");
		Connection conn = null;
		try {
			conn = DbUtils.getConnection();
			conn.setAutoCommit(false);
			ArrayList<Integer> ids = queryAll(conn);
			while (ids.size() > 0) {
				Integer id = ids.remove(0);
				boolean lockSuccess = lockRow(conn, id);
				if (lockSuccess) {
					update(conn, id);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<Integer> queryAll(Connection conn) throws ClassNotFoundException, SQLException {
		String sql = "select * from T1 where updateTime is null ORDER BY RAND() limit 50";

		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		while (rs.next()) {
			int id = rs.getInt(1);
			ids.add(id);
		}
		System.out.println(Thread.currentThread().getName() + "��ѯ���е����ݣ�" + "\n" + ids);
		return ids;
	}

	private boolean lockRow(Connection conn, int id) {
		try {
			System.out.println(Thread.currentThread().getName() + "��ʼ����id" + id);
			String sqlLockRowByid = "select * from T1 where id=? for update";
			PreparedStatement lockRowStmt = conn.prepareStatement(sqlLockRowByid);
			lockRowStmt.setInt(1, id);
			ResultSet lockRowRs = lockRowStmt.executeQuery();
			if (lockRowRs.next() && lockRowRs.getString("updateTime") == null) {
				System.out.println(Thread.currentThread().getName() + "����" + id + "�ɹ�.");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "����" + id + "ʧ��");
		return false;
	}

	private int update(Connection conn, int id) {
		try {
			String updateSql = "update T1 set updateTime=current_time() where id=?";
			PreparedStatement stmt = conn.prepareStatement(updateSql);
			stmt.setInt(1, id);
			int row = stmt.executeUpdate();
			conn.commit();
			System.out.println(Thread.currentThread().getName() + "����" + id + "���=" + row);
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}
}
