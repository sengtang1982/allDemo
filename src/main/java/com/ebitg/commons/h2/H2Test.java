package com.ebitg.commons.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class H2Test {
	private static final Logger LOG = LoggerFactory.getLogger(H2Test.class);
	private static final String DEFAULT_BASEHOST = "jdbc:h2:D:/temp/";

	@Test
	public void testCreateTable() throws ClassNotFoundException, SQLException {
		String tableStruct = "t1(A1,A2)";
		Connection conn = createConnection("");
		createTable(conn, tableStruct);
	}

	public Connection createConnection(String host) throws ClassNotFoundException, SQLException {
		if (StringUtils.isBlank(host)) {
			String pattern = "yyyyMMddHHmm";
			host = DEFAULT_BASEHOST + DateFormatUtils.format(new Date(), pattern);
			LOG.info("未设置Host,将采用默认的Host{}", host);
		}
		String driver = "org.h2.Driver";
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(host, "sa", "");
		return conn;
	}

	public int createTable(Connection conn, String tableStruct) {
		int beginIndex = tableStruct.indexOf("(");
		int endIndex = tableStruct.indexOf(")");
		String tableName = tableStruct.substring(0, beginIndex);
		String columnNameStr = tableStruct.substring(beginIndex + 1, endIndex);
		StringBuffer sb = new StringBuffer("CREATE TABLE if not exists ");
		sb.append(tableName);
		sb.append("(ID_" + DateFormatUtils.format(new Date(), "MMddHHmmss"));
		sb.append(" int PRIMARY KEY");
		String[] columnNames = columnNameStr.split(",");
		for (int i = 0; i < columnNames.length; i++) {
			sb.append(",");
			sb.append(columnNames[i]);
			sb.append(" varchar(255)");
		}
		sb.append(");");
		return update(conn, sb.toString());
	}

	public int update(Connection conn, String sql) {
		try {
			Statement stmt = conn.createStatement();
			int isOk = stmt.executeUpdate(sql);
			return isOk;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
