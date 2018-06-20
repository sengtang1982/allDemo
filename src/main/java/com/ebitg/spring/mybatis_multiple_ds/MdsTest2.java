package com.ebitg.spring.mybatis_multiple_ds;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MdsTest2 {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config2.class);
		Person bean = (Person) context.getBean("ABC");
		System.out.println(bean);
	}
}
