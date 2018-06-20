package com.ebitg.lang;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JarFileTest {
	private static final Logger LOG = LoggerFactory.getLogger(JarFileTest.class);

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String path = "D:\\repo\\org\\apache\\httpcomponents\\httpclient\\4.5.3\\httpclient-4.5.3.jar";
		File file = new File(path);
		JarFile jarFile = new JarFile(file);
		String suffix = "class";
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = entries.nextElement();
			String name = jarEntry.getName();
			if (name.endsWith(suffix)) {
				name = StringUtils.left(name, name.length() - suffix.length() - 1);
				String className = name.replace('/', '.');
				Class<?> clazz = Class.forName(className);
				if (!ClassUtils.isInnerClass(clazz)) {
					LOG.debug("name:{},ClassUtils.getShortClassName(name):{},是否是内部类{}", className,
							ClassUtils.getShortClassName(className), ClassUtils.isInnerClass(clazz));
				}
			}
		}
		String packageName = "org.apache.http.auth";
		ZipEntry entry = jarFile.getEntry(packageName.replace('.', '/'));
		InputStream in = jarFile.getInputStream(entry);
		System.out.println(in.getClass());
		JarEntry jarEntry1 = jarFile.getJarEntry(packageName.replace('.', '/'));
		System.out.println(entry.getName() + "\t" + jarEntry1 + "\t" + jarEntry1.getClass());
		String path1 = DefaultSchemePortResolver.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		System.out.println(path1);

	}
}
