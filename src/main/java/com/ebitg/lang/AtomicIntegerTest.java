package com.ebitg.lang;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

public class AtomicIntegerTest {
	@Test
	public void test01() {
		AtomicInteger atomicInteger = new AtomicInteger(0);
		Assert.assertEquals(0, atomicInteger.get());
		Assert.assertEquals(1, atomicInteger.incrementAndGet());
		Assert.assertEquals(0, atomicInteger.decrementAndGet());
	}
}
