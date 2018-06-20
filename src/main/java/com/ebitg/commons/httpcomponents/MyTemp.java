package com.ebitg.commons.httpcomponents;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class MyTemp {
	@Test
	public void test01() throws IOException {
		List<String> lines = FileUtils.readLines(new File("D:\\name.txt"), "UTF-8");
		for (String l : lines) {
			System.out.printf("'%s',", l);
		}
		System.out.println("\n"+lines.size());
	}
}
