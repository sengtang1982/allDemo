package com.ebitg.commons.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {
	@Test
	public void test01() {
		Logger LOG = LoggerFactory.getLogger(this.getClass());
		LOG.debug("logbackTest...");
	}
}
