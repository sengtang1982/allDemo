package com.ebitg.spring.prop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

@Configuration
@ComponentScan("com.ebitg.spring.prop")
@PropertySources({ @PropertySource(name = "mysql", value = "classpath:com/ebitg/spring/prop/mysql.properties"),
		@PropertySource(name = "sqlserver", value = "classpath:dbcp.properties") })
public class Config {

	@Bean
	public PropertySourcesPlaceholderConfigurer propertyConfiger() {
		PropertySourcesPlaceholderConfigurer propCfger = new PropertySourcesPlaceholderConfigurer();
		return propCfger;
	}

}
