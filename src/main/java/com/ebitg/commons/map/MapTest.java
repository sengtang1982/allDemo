package com.ebitg.commons.map;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.Assert;
import org.junit.Test;

public class MapTest {
	@Test
	public void test1() {
		MultiValuedMap map = new ArrayListValuedHashMap();
		map.put("A", "A1");
		map.put("A", "A2");
		Assert.assertArrayEquals(new String[] { "A1", "A2" }, map.get("A").toArray());
	}
}
