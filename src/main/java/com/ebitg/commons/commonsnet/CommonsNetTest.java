package com.ebitg.commons.commonsnet;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.whois.WhoisClient;
import org.junit.Test;

public class CommonsNetTest {
	@Test
	public void test01() throws SocketException, IOException {
		WhoisClient whois = new WhoisClient();
		whois.connect(WhoisClient.DEFAULT_HOST);
		System.out.println(whois.query("baidu.com"));
	}
}
