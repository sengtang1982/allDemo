package com.ebitg.lang.datetime;

import org.joda.time.DateTime;
import org.junit.Test;

public class DateTimeTest {
	@Test
	public void test01() {
		DateTime dt = new DateTime("2018-1-13");
		System.out.println(dt.getDayOfMonth());
	}
}
