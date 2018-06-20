package com.ebitg.commons.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.junit.Test;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest implements Job {
	@Test
	public void test01() {
		try {
			// ����scheduler
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			// ����һ��Trigger
			Trigger trigger = newTrigger().withIdentity("trigger1", "group1") // ����name/group
					.startNow()// һ������scheduler��������Ч
					.withSchedule(simpleSchedule() // ʹ��SimpleTrigger
							.withIntervalInSeconds(1) // ÿ��һ��ִ��һ��
							.repeatForever()) // һֱִ�У����ڵ��ϲ�ͣЪ
					.build();
			// ����һ��JobDetail
			JobDetail job = newJob(QuartzTest.class) // ����Job��ΪHelloQuartz�࣬����������ִ���߼�����
					.withIdentity("job1", "group1") // ����name/group
					.usingJobData("name", "quartzAAAA") // ��������
					.build();
			// �����������
			scheduler.scheduleJob(job, trigger);
			// ����֮
			scheduler.start();
			// ����һ��ʱ���ر�
			Thread.sleep(Long.MAX_VALUE);
			scheduler.shutdown(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(new Date().toString() + context);
		System.out.println("OK");
	}
}
