package com.ebitg.lang;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class NIOTest {
	@Test
	public void test01() throws IOException {
		InputStream in = NIOTest.class.getResourceAsStream("/logback.xml");
		String str = IOUtils.toString(in, "UTF-8");
		System.out.println(str);
	}
}
