package com.ebitg.lang;

import java.net.URL;

public class ResourceLoaderTest {
	public static void main(String[] args) {
		URL u1 = ResourceLoaderTest.class.getResource(".");
		URL u2 = ResourceLoaderTest.class.getResource("/");

		URL u3 = ResourceLoaderTest.class.getClassLoader().getResource(".");
		URL u4 = ResourceLoaderTest.class.getClassLoader().getResource("/");
		System.out.println(u1);
		System.out.println(u2);
		System.out.println(u3);
		System.out.println(u4);
	}
}
