package com.ebitg.spring.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {
	private static final Logger LOG = LoggerFactory.getLogger(MyEvent.class);

	public MyEvent(Object source) {
		super(source);
		LOG.debug("�¼�Դ:" + source);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOG.debug("�¼�ִ�����");
	}

}
