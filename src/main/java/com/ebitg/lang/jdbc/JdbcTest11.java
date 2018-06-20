package com.ebitg.lang.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 两个线程 通过id 对同一行加锁会等待锁释放
 * 
 * @author 王波
 *
 */
public class JdbcTest11 {
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new T10_1());
			t.start();
		}
	}
}

class T11_1 implements Runnable {
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "启动");
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

	private ArrayList<Integer> queryAll(Connection conn)
			throws ClassNotFoundException, SQLException, InterruptedException {
		String sql = "select * from T1 where id in(select id from T1  where updateCount=0 ORDER BY RAND() limit 50) for update";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		while (rs.next()) {
			int id = rs.getInt(1);
			ids.add(id);
		}
		System.out.println(Thread.currentThread().getName() + "查询所有的数据：" + "\n" + ids);
		Thread.sleep(10000);
		return ids;
	}

}
