package com.ebitg.commons.collections;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.junit.Test;

public class BidMapTest {
	@Test
	public void test01() {
		BidiMap<String, String> map = new DualHashBidiMap<String, String>();
		map.put("A1", "B1");

		String v1 = map.get("A1");
		String k1 = map.getKey("B1");
		System.out.println(k1 + "=" + v1);
	}
}
