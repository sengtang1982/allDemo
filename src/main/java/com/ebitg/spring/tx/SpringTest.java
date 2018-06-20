package com.ebitg.spring.tx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ebitg.spring.tx.service.MyService;

public class SpringTest {
	private static final int THREAD_COUNT_NUM = 10;

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("com/ebitg/spring/tx/spring_txText.xml");

		String[] bdns = ctx.getBeanDefinitionNames();
		for (int i = 0; i < bdns.length; i++) {
			System.out.println(bdns[i]);
		}
		long begin = System.currentTimeMillis();
		// CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT_NUM);
		for (int i = 0; i < THREAD_COUNT_NUM; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					MyService myService = (MyService) ctx.getBean("myService");
					myService.doSome();
					// countDownLatch.countDown();
				}
			});
			t.start();
			Thread.sleep(1000);
		}
		// countDownLatch.await();
		long end = System.currentTimeMillis();
		System.out.println(end - begin + "ms");
	}
}
