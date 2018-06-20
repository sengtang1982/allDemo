package com.ebitg.spring.mybatis_multiple_ds;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MdsTest3 {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config3.class);
		String[] bdns = context.getBeanDefinitionNames();
		Arrays.asList(bdns).forEach(bdn -> {
			System.out.println(">>>" + bdn);
		});
		Object bean = context.getBean("ssf_mysql");
		System.out.println("ssf_mysql:" + bean);
		Object sst_mysql = context.getBean("sst_mysql");
		System.out.println("sst_mysql" + sst_mysql);
	}
}
