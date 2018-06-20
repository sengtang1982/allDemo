package com.ebitg.spring.taskscheduler;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SchedulerTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
	}
}
