package com.ebitg.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {
	private static final Logger LOG = LoggerFactory.getLogger(Slf4jTest.class);

	@Test
	public void test01() {
		LOG.debug("<<{}>>","  abc ");
	}
	@Test
	public void test02() {
		LOG.debug("TEST");
	}
}
