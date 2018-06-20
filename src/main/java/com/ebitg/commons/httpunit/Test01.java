package com.ebitg.commons.httpunit;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class Test01 {
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String host = "10.3.2.200";
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.addRequestHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		webClient.addRequestHeader("Accept-Encoding", "gzip, deflate");
		webClient.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		webClient.addRequestHeader("Connection", "keep-alive");
		webClient.addRequestHeader("Upgrade-Insecure-Requests", "1");
		webClient.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		webClient.addRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
		String cookieStr = " ecology_JSessionId=abcVgDI4TCW27hfFFyrcw;loginfileweaver=%2Fwui%2Ftheme%2Fecology8%2Fpage%2Flogin.jsp%3FtemplateId%3D3%26logintype%3D1%26gopage%3D;loginidweaver=103307; languageidweaver=7; testBanCookie=test;JSESSIONID=abcFGFc3LKsh5eZRsG0cw";

		webClient.getCookieManager().addCookie(new Cookie(host, "ecology_JSessionId", "abcVgDI4TCW27hfFFyrcw"));
		webClient.getCookieManager().addCookie(new Cookie(host, "JSESSIONID", "abcFGFc3LKsh5eZRsG0cw"));
		webClient.getCookieManager().addCookie(new Cookie("", "loginfileweaver", "103307"));
		webClient.getCookieManager().addCookie(new Cookie("", "loginfileweaver",
				"%2Fwui%2Ftheme%2Fecology8%2Fpage%2Flogin.jsp%3FtemplateId%3D3%26logintype%3D1%26gopage%3D"));
		webClient.getCookieManager().addCookie(new Cookie("", "languageidweaver", "7"));
		webClient.getCookieManager().addCookie(new Cookie("", "testBanCookie", "test"));
		String baseUrl = "http://10.3.2.200:89/manager/attendancePointList.do?orgid=0&type=&name=&addr=&pageNo=";
		HtmlPage page = webClient.getPage(baseUrl);
		List<String> uidList = getPointIdList(page);
		HtmlParagraph lastPage = page.querySelector(".pagenav");
		String lastPageContent = lastPage.getTextContent();
		// System.out.println(lastPageContent);
		Pattern p = Pattern.compile("共(\\d+)页");
		Matcher matcher = p.matcher(lastPageContent);
		boolean find = matcher.find();
		int pageCount = 1;
		if (find) {
			pageCount = Integer.parseInt(matcher.group(1));
			System.out.println(pageCount);
		} else {
			throw new RuntimeException("没有找到该页面.");
		}
		System.out.println(uidList);
		String content = page.getWebResponse().getContentAsString();
		String backHtmlPath = "E:\\temp\\";
		String suffix = ".html";
		FileUtils.writeStringToFile(new File(backHtmlPath + 1 + suffix), content, "UTF-8");
		for (int i = 2; i < pageCount; i++) {
			String url = baseUrl + i;
			page = webClient.getPage(url);
			List<String> pointIdList = getPointIdList(page);
			System.out.println(pointIdList);
			content = page.getWebResponse().getContentAsString();
			FileUtils.writeStringToFile(new File(backHtmlPath + i + suffix), content, "UTF-8");
		}
		webClient.close();
	}

	private static List<String> getPointIdList(HtmlPage page) {
		List<String> uidList = new ArrayList<String>();
		DomNodeList<DomNode> btnDelList = page.querySelectorAll(".delbtn");
		btnDelList.forEach(btnNode -> {
			HtmlButton btn = (HtmlButton) btnNode;
			String uid = btn.getAttribute("uid");
			uidList.add(uid);
		});
		return uidList;
	}

	public static void main1(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
	}
}
