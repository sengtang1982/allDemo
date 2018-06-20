package com.ebitg.commons.commonscodec;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class Md5Test {
	@Test
	public void test01() {
		String md5Hex = DigestUtils.md5Hex("wangbo");
		System.out.println(md5Hex);
	}
}
