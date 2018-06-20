package com.ebitg.commons.quartz;

import java.util.Properties;

import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class SpringQuartzTest {
	@Test
	public void test01() throws InterruptedException {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		Properties quartzProperties = new Properties();
		quartzProperties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		quartzProperties.setProperty("org.quartz.threadPool.threadCount", "20");
		schedulerFactory.setQuartzProperties(quartzProperties);
		schedulerFactory.setAutoStartup(true);
		Trigger triggers = buildTrigger();
		schedulerFactory.setTriggers(triggers);
		Thread.sleep(Long.MAX_VALUE);
	}

	private Trigger buildTrigger() {
		CronTriggerFactoryBean cronTriggerFactory = new CronTriggerFactoryBean();
		JobDetail jobDetail = buileJobDetail();
		cronTriggerFactory.setJobDetail(jobDetail);
		cronTriggerFactory.setCronExpression("10 0/1 * * * ?");
		return cronTriggerFactory.getObject();
	}

	private JobDetail buileJobDetail() {
		MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactory = new MethodInvokingJobDetailFactoryBean();
		methodInvokingJobDetailFactory.setTargetClass(MyJob01.class);
		methodInvokingJobDetailFactory.setTargetMethod("ABC");
		return methodInvokingJobDetailFactory.getObject();
	}
}
