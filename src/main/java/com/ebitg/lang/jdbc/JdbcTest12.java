package com.ebitg.lang.jdbc;

import java.sql.Array;
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
public class JdbcTest12 {
	public static void main(String[] args) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "����");
		Connection conn = null;
		try {
			conn = DbUtils.getConnection();
			conn.setAutoCommit(false);
			ArrayList<Integer> ids = queryAll(conn);
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

	private static ArrayList<Integer> queryAll(Connection conn)
			throws ClassNotFoundException, SQLException, InterruptedException {
		String sql = "select * from T1 limit 5 ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		while (rs.next()) {
			Array array = rs.getArray(1);
			System.out.println(array);
		}
		System.out.println(Thread.currentThread().getName() + "��ѯ���е����ݣ�" + "\n" + ids);
		Thread.sleep(10000);
		return ids;
	}
}