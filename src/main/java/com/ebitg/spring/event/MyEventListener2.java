package com.ebitg.spring.event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventListener2 implements ApplicationListener<MyEvent> {
	private static final Logger LOG = LoggerFactory.getLogger(MyEventListener2.class);
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void onApplicationEvent(MyEvent myEvent) {
		LOG.debug(this.getClass() + "事件处理程序" + DF.format(new Date()));
	}

}
