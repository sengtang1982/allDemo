package com.ebitg.slf4j;

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

	@Test
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

	@Test
	public void test01() {
		LOG.debug("TEST");
	}

	/**
	 * 添加考勤点
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Test
	@Ignore
	public void testPost() throws ClientProtocolException, IOException {
		String[] addressInfo = { "62001", "37", "27", "山东烟台阳光宝贝拓展组考勤点2", "考勤点名称1" };
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost request = new HttpPost("http://10.3.2.200:89/manager/savePoint.do");
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setHeader("Cookie",
				"ecology_JSessionId=abcuXuZhilgzW97r66Hbw; userid=4; userKey=5ff6db22-10e7-461c-97aa-e280d18dd3d4; ClientUDID=; ClientToken=; ClientVer=6.5; ClientType=Webclient; ClientLanguage=zh; ClientCountry=CN; ClientMobile=; setClientOS=; setClientOSVer=; Pad=false; testBanCookie=test; loginfileweaver=%2Fwui%2Ftheme%2Fecology8%2Fpage%2Flogin.jsp%3FtemplateId%3D3%26logintype%3D1%26gopage%3D; loginidweaver=1; languageidweaver=7; JSESSIONID=abcoy_K6yynLm5OX7Xacw");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("orgid", addressInfo[0]));
		params.add(new BasicNameValuePair("lng", addressInfo[1]));
		params.add(new BasicNameValuePair("lat", addressInfo[2]));
		params.add(new BasicNameValuePair("addr", addressInfo[3]));
		params.add(new BasicNameValuePair("type", "1"));
		params.add(new BasicNameValuePair("name", addressInfo[4]));
		HttpEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
		request.setEntity(entity);
		CloseableHttpResponse response = client.execute(request);
		InputStream content = response.getEntity().getContent();
		String result = IOUtils.toString(content, "UTF-8");
		LOG.info(result);
	}

	@Test
	@Ignore
	public void readFile() throws IOException, ClassNotFoundException, SQLException {
		List<String> lines = FileUtils.readLines(new File("E:\\立健\\坐标.txt"), "UTF-8");
		for (int i = 0; i < lines.size(); i++) {
			System.out.println(i + "===" + lines.get(i));
		}
	}

	@Test
	public void testGetDept() throws SQLException, ClassNotFoundException {
		String deptName = "山东立健采购中心";
		String driverClass = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@10.3.2.200:1521:orcl";
		String username = "ecology2";
		String password = "ecology2";
		Class.forName(driverClass);
		Connection connection = DriverManager.getConnection(url, username, password);
		String result;
		try {
			List<String> lines = FileUtils.readLines(new File("E:\\立健\\坐标.txt"), "UTF-8");
			for (int i = 0; i < lines.size(); i++) {
				String s = lines.get(i).trim();
				String[] infos = s.split("----");
				if (infos.length != 4) {
					System.out.println(infos.length + "*******" + (i + 1));
				} else {
					System.out.println(infos[0] + "--" + infos[1] + "--" + infos[2] + "--" + infos[3]);
					result = getDept(connection, infos[0]).toString();
					System.out.println(result);
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
