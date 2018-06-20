package com.ebitg.spring.resourceloaderaware;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ComponentScan("com.ebitg.spring.resourceloaderaware")
public class MyResourceLoader implements ResourceLoaderAware {

	private ResourceLoader resourceLoader;
	private static final Logger LOG = LoggerFactory.getLogger(MyResourceLoader.class);

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void getResource() throws Exception {
		String location = "classpath:com/ebitg/spring/resourceloaderaware/mysql.properties";
		Resource resource = resourceLoader.getResource(location);
		
		Properties props = new Properties();
		props.load(resource.getInputStream());
		LOG.debug(props.getProperty("test1"));
		DataSource ds = BasicDataSourceFactory.createDataSource(props);
		System.out.println(ds.getConnection());
	}

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyResourceLoader.class);
		MyResourceLoader bean = context.getBean(MyResourceLoader.class);
		bean.getResource();
	}

}
