package com.ebitg.commons.dbcp;

import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import oracle.jdbc.xa.client.OracleXADataSource;

public class Jta {
	private static final String URL1 = "jdbc:oracle:thin:@192.168.0.108:1521:orcl";
	// private static final String URL2 =
	// "jdbc:Mysql:thin:@192.168.0.108:1521:orcl";
	// private static final String URL1 = "jdbc:mysql://localhost:3306/buema";
	private static final String URL2 = "jdbc:mysql://localhost:3306/buema";

	public static void main(String[] args) throws NamingException, SQLException, XAException {
		OracleXADataSource dataSource1 = new OracleXADataSource();
		MysqlXADataSource dataSource2 = new MysqlXADataSource();
		dataSource1.setURL(URL1);
		dataSource2.setURL(URL2);
		// dataSource2.setURL(URL2);
		XAConnection xaConnection1 = dataSource1.getXAConnection("lj", "lj");
		XAConnection xaConnection2 = dataSource2.getXAConnection("root", "root");
		XAResource resource1 = xaConnection1.getXAResource();
		XAResource resource2 = xaConnection2.getXAResource();
		Xid xid1 = new MyXid(0, new byte[] { 0x01 }, new byte[] { 0x02 });
		Xid xid2 = new MyXid(0, new byte[] { 0x01 }, new byte[] { 0x03 });
		resource1.start(xid1, XAResource.TMNOFLAGS);
		resource2.start(xid2, XAResource.TMNOFLAGS);
		String sql1 = "insert into MyTemp_JTA values(19,'TEST2')";
		Statement pstmt1 = xaConnection1.getConnection().createStatement();
		int effectedRowCount1 = pstmt1.executeUpdate(sql1);
		pstmt1.close();
		String sql2 = "insert into MyTemp_JTA values(20,'TEST2')";
		Statement pstmt2 = xaConnection2.getConnection().createStatement();
		int effectedRowCount2 = pstmt2.executeUpdate(sql2);
		pstmt1.close();
		pstmt2.close();
		resource1.end(xid1, XAResource.TMSUCCESS);
		resource2.end(xid2, XAResource.TMSUCCESS);

		int prp1 = resource1.prepare(xid1);
		int prp2 = resource2.prepare(xid2);
		if (prp1 == XAResource.XA_OK || prp1 == XAResource.XA_RDONLY) {
			resource1.commit(xid1, false);
			resource2.commit(xid2, false);
		}
		xaConnection1.close();
		xaConnection2.close();
	}
}
