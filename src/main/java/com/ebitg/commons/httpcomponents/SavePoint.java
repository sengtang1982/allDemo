package com.ebitg.commons.httpcomponents;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SavePoint {
	private static final Logger LOG = LoggerFactory.getLogger(SavePoint.class);

	public static void main(String[] args) throws IOException {
		LOG.info("-------------------开始导入-------------------");
		List<String> lines = FileUtils.readLines(new File("E:\\zuobiao.txt"), "GBK");
		for (int i = 0; i < lines.size(); i++) {
			String l = lines.get(i);
			String[] p = l.split("\t");
			if (p.length != 5) {
				LOG.info("{}行格式错误", i + 1);
			} else {
				String r = sendAddress(new String[] { p[0], p[2], p[1], p[3], p[4], p[4] });
				LOG.debug("{}行保存结果{}---->0{}-1{}-2{}-3{}-4{}-5{}", i + 1, r, p[0], p[1], p[2], p[3], p[4], p[4]);
			}
		}
	}

	private static String sendAddress(String[] addressInfo) throws IOException, ClientProtocolException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost request = new HttpPost("http://118.190.216.42:8089/manager/savePoint.do");// 阿里云
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setHeader("Cookie",
				"ecology_JSessionId=abck0h845NHXp4Q90Tscw; EBRIDGE_JSESSIONID=F8C335E58ECF57AD3E5C638F3A8474F9; testBanCookie=test; loginfileweaver=%2Fwui%2Ftheme%2Fecology8%2Fpage%2Flogin.jsp%3FtemplateId%3D3%26logintype%3D1%26gopage%3D; loginidweaver=29844; languageidweaver=7; JSESSIONID=abcb0xTOXJsts7kbNQDcw");
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
		return result;
	}
}
