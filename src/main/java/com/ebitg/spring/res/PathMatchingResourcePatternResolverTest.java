package com.ebitg.spring.res;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;

public class PathMatchingResourcePatternResolverTest {
	private static final String DB_PREFIX = "db_";

	@Test
	public void test01() throws Exception {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath*:*.properties");
		Map<String, Properties> propsMap = new HashMap<String, Properties>();
		Properties props = new Properties();
		Map<String, DataSource> dsMap = new HashMap<String, DataSource>();
		for (int i = 0; i < resources.length; i++) {
			String fileName = resources[i].getFilename();
			if (fileName.startsWith(DB_PREFIX)) {
				String key = FilenameUtils.getBaseName(fileName).substring(DB_PREFIX.length());
				props = PropertiesLoaderUtils.loadProperties(resources[i]);
				propsMap.put(key, props);
				BasicDataSource ds = BasicDataSourceFactory.createDataSource(props);
				dsMap.put(key, ds);
			}
		}
		System.out.println(dsMap.get("sqlserver").getConnection());
	}
}
