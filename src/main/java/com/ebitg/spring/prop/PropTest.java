package com.ebitg.spring.prop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

public class PropTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		PropertySourcesPlaceholderConfigurer propCfger = (PropertySourcesPlaceholderConfigurer) context
				.getBean("propertyConfiger");
		PropertySources propSrcs = propCfger.getAppliedPropertySources();

		ResourcePropertySource propSrc = (ResourcePropertySource) ((StandardEnvironment) propSrcs
				.get(PropertySourcesPlaceholderConfigurer.ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME).getSource())
						.getPropertySources().get("mysql");

		Object t1 = propSrc.getProperty("password");
		Object t2 = propSrc.getProperty("test2");

		System.out.println(t1 + "------" + t2);

		// Map<String, Object> source = propSrc.getSource();
		//
		// Properties props = new Properties();
		// props.putAll(source);

	}
}
