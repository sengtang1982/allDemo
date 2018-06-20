package com.ebitg.lang;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SubListTest {
	@Test
	public void get() {
		List<String> list = Arrays.asList("0", "1", "2", "3", "4");
		List<String> subList = list.subList(0, 2);
		List<String> subList1 = list.subList(2, 4);
		System.out.println(subList);
		System.out.println(subList1);
	}
}
