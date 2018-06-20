package com.ebitg.commons.lang3;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {
	@Test
	public void leftPad() {
		String leftPad = StringUtils.leftPad("mylog", 20);
		System.out.println(leftPad);
	}

	@Test
	public void testEmpty() {
		boolean empty1 = StringUtils.isEmpty("");
		boolean empty2 = StringUtils.isEmpty(null);
		boolean blank1 = StringUtils.isBlank("");
		boolean blank2 = StringUtils.isBlank(null);
		System.out.println(empty1 + "" + empty2);
		System.out.println(blank1 + "" + blank2);
	}

	@Test
	public void jieQu() {
		String abbreviate = StringUtils.abbreviate("0123456789ABC", 10);
		System.out.println("abbreviate£º" + abbreviate);
	}
}
