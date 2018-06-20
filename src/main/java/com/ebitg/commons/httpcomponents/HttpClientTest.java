package com.ebitg.commons.httpcomponents;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientTest {
	private static final Logger LOG = LoggerFactory.getLogger(HttpClientTest.class);

	@Ignore
	public void testUpload() throws ClientProtocolException, IOException {

		CloseableHttpClient client = HttpClients.createDefault();
		HttpUriRequest request = new HttpGet(
				"http://search.jd.com/Search?keyword=%E5%9B%9B%E5%AD%A3%E6%84%9F%E5%86%92&enc=utf-8");
		CloseableHttpResponse response = client.execute(request);
		InputStream content = response.getEntity().getContent();
		String result = IOUtils.toString(content, "UTF-8");
		System.out.println(result);
	}

	/**
	 * 添加考勤点
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Test
	public void testPost() throws ClientProtocolException, IOException {
		String[] addressInfo = { "17377", "117.249", "36.4114", "济南市历城区山大路2号彼岸新都7号楼519室", "鲁中门管部", "鲁中门管部" };
		sendAddress(addressInfo);
	}

	private void sendAddress(String[] addressInfo) throws IOException, ClientProtocolException {
		CloseableHttpClient client = HttpClients.createDefault();
		// HttpPost request = new HttpPost("http://10.3.2.200:89/manager/savePoint.do");
		HttpPost request = new HttpPost("http://118.190.216.42:8089/manager/savePoint.do");// 阿里云
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setHeader("Cookie",
				"ecology_JSessionId=abck0h845NHXp4Q90Tscw; EBRIDGE_JSESSIONID=F8C335E58ECF57AD3E5C638F3A8474F9; loginfileweaver=%2Fwui%2Ftheme%2Fecology8%2Fpage%2Flogin.jsp%3FtemplateId%3D3%26logintype%3D1%26gopage%3D; loginidweaver=29844; languageidweaver=7; testBanCookie=test; JSESSIONID=abcb0xTOXJsts7kbNQDcw");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("orgid", addressInfo[0]));
		params.add(new BasicNameValuePair("lng", addressInfo[1]));
		params.add(new BasicNameValuePair("lat", addressInfo[2]));
		params.add(new BasicNameValuePair("addr", addressInfo[3]));
		params.add(new BasicNameValuePair("type", "1"));
		params.add(new BasicNameValuePair("name", addressInfo[4]));
		params.add(new BasicNameValuePair("orgname", addressInfo[5]));
		HttpEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
		request.setEntity(entity);
		CloseableHttpResponse response = client.execute(request);
		InputStream content = response.getEntity().getContent();
		String result = IOUtils.toString(content, "GBK");
		LOG.info("保存门店地址{}", result);
	}

	@Test
	@Ignore
	public void readFile() throws IOException, ClassNotFoundException, SQLException {
		List<String> lines = FileUtils.readLines(new File("E:\\temp\\坐标_源数据.txt"), "UTF-8");
		String driverClass = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@10.3.2.200:1521:orcl";
		String username = "ecology2";
		String password = "ecology2";
		Class.forName(driverClass);
		Connection connection = DriverManager.getConnection(url, username, password);
		try {
			for (int i = 0; i < lines.size(); i++) {
				LOG.info("读取第{}行数据={}", i + 1, lines.get(i));
				String s = lines.get(i).trim();
				String[] infos = s.split("----");
				if (infos.length != 4) {
					System.out.println(infos.length + "*******" + (i + 1));
					LOG.error("************{}行不能解析", i + 1);
				} else {
					System.out.println(infos[0] + "--" + infos[1] + "--" + infos[2] + "--" + infos[3]);
					// result = getDept(connection, infos[0]).toString();
					// System.out.println(result);
				}
			}
		} catch (Exception e) {
		}
		connection.close();
	}

	@Test
	@Ignore
	public void testGetDept() throws SQLException, ClassNotFoundException {
		String driverClass = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@10.3.2.200:1521:orcl";
		String username = "ecology2";
		String password = "ecology2";
		Class.forName(driverClass);
		Connection connection = DriverManager.getConnection(url, username, password);
		String result;
		try {
			List<String> lines = FileUtils.readLines(new File("E:\\temp\\坐标_源数据.txt"), "UTF-8");
			for (int i = 0; i < lines.size(); i++) {
				String s = lines.get(i).trim();
				String[] infos = s.split("\t");
				if (infos.length != 4) {
					LOG.info("第{}行=读取文件的内容{}", (i + 1), s);
				} else {
					Map<String, String> deptInfo = getDept(connection, infos[0]);
					if (!StringUtils.isBlank(deptInfo.get("id"))) {
						String[] addressInfo = new String[] { deptInfo.get("id"), infos[1], infos[2], infos[3],
								deptInfo.get("deptName"), deptInfo.get("deptName") };
						LOG.info("参数={}", StringUtils.join(addressInfo, ","));
						sendAddress(addressInfo);
					} else {
						LOG.info("没有找到第{}行部门|门店({})", i + 1, infos[0]);
					}
				}
			}
		} catch (Exception e) {
			result = e.getClass().getName() + e.getMessage();
		}
		connection.close();
	}

	public Map<String, String> getDept(Connection connection, String deptName) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			String sql = "select id,departmentcode hrDeptId,departmentname deptName from HrmDepartment where departmentname=?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, deptName);
			LOG.debug("要查找的门店为:{}", deptName);
			rs = stmt.executeQuery();
			int count = 0;
			Map map = new HashMap<String, String>();
			while (rs.next()) {
				String id = rs.getString("id");
				String hrDeptId = rs.getString("hrDeptId");
				String deptName1 = rs.getString("deptName");
				map.put("id", id);

				map.put("hrDeptId", hrDeptId);
				map.put("deptName", deptName1);
				if (count == 1) {
					throw new RuntimeException(deptName + "不是唯一的");
				}
				count++;
			}
			return map;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
