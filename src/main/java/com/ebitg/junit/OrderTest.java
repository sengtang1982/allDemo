package com.ebitg.junit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderTest {
	@Test
	public void test00_Submit() throws InterruptedException {
		System.out.println("test00_Submit");
		Thread.sleep(4000);
		System.out.println("test00_Submit OVER");
	}

	@Test
	public void test02() throws InterruptedException {
		System.out.println("test02");
		Thread.sleep(3000);
		System.out.println("test02 OVER");
	}

	@Test
	public void test01() throws InterruptedException {
		System.out.println("test01");
		Thread.sleep(3000);
		System.out.println("test01 OVER");
	}

	@Test
	public void test04() throws InterruptedException {
		System.out.println("test04");
		Thread.sleep(1000);
		System.out.println("test04 OVER");
	}
}
