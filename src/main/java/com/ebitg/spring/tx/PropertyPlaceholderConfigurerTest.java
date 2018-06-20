package com.ebitg.spring.tx;

import java.util.Properties;

import org.junit.Test;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyPlaceholderConfigurerTest extends PropertyPlaceholderConfigurer {
	@Test
	public void test1() {
		Properties props = new Properties();
		String placeholder = "classpath:db_mysql.properties";
		String resolvePlaceholder = this.resolvePlaceholder(placeholder, props);
		System.out.println(resolvePlaceholder);
		System.out.println(props);
	}
}
