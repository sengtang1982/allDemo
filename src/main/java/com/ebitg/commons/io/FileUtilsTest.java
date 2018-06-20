package com.ebitg.commons.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtilsTest {
	public static void main(String[] args) throws IOException {
		File file = new File("D:\\a\\b\\c.txt");
		FileUtils.write(file, "lllllll", "UTF-8", true);
	}
}
