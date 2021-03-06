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
			// 创建scheduler
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			// 定义一个Trigger
			Trigger trigger = newTrigger().withIdentity("trigger1", "group1") // 定义name/group
					.startNow()// 一旦加入scheduler，立即生效
					.withSchedule(simpleSchedule() // 使用SimpleTrigger
							.withIntervalInSeconds(1) // 每隔一秒执行一次
							.repeatForever()) // 一直执行，奔腾到老不停歇
					.build();
			// 定义一个JobDetail
			JobDetail job = newJob(QuartzTest.class) // 定义Job类为HelloQuartz类，这是真正的执行逻辑所在
					.withIdentity("job1", "group1") // 定义name/group
					.usingJobData("name", "quartzAAAA") // 定义属性
					.build();
			// 加入这个调度
			scheduler.scheduleJob(job, trigger);
			// 启动之
			scheduler.start();
			// 运行一段时间后关闭
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
