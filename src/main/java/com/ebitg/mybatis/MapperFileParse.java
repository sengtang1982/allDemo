package com.ebitg.mybatis;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;;

public class MapperFileParse {
	protected static final String SUFFIX = "_sql.xml";

	public static void main(String[] args) {
		String path = "com/ebitg/mybatis";
		URL url = ClassLoader.getSystemClassLoader().getResource(path);
		File file = new File(url.getFile());
		File[] listFiles = file.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return StringUtils.endsWithIgnoreCase(name, SUFFIX);
			}
		});
		for (int i = 0; i < listFiles.length; i++) {
			System.out.println(listFiles[i]);
		}
	}
}
