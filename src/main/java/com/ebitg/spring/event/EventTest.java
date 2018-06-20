package com.ebitg.spring.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EventTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		MyEvent myEvent = new MyEvent("�¼�Դ��Ϣ");
		context.publishEvent(myEvent);

		String[] bdns = context.getBeanDefinitionNames();
		for (String name : bdns) {
			System.out.println(name);
		}
	}
}
