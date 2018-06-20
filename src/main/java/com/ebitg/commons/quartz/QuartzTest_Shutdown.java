package com.ebitg.commons.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest_Shutdown implements Job {
	protected Scheduler scheduler1;
	protected Scheduler scheduler2;
	private Scheduler scheduler;

	@Test
	public void test01() throws SchedulerException, InterruptedException {

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					scheduler1 = StdSchedulerFactory.getDefaultScheduler();
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					scheduler2 = StdSchedulerFactory.getDefaultScheduler();
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
		});
		Thread.sleep(5000);
		String jobName = "任务名称";
		String jogGroup = "任务分组";
		String triggerName = "触发器名称";
		String cronExpression = "0/3 * * * * ? ";
		String className = "com.ebitg.commons.quartz.QuartzTest_Shutdown";
		Scheduler schedule = schedule(className, jobName, jogGroup, triggerName, cronExpression, null);
		schedule.start();
		Thread.sleep(Long.MAX_VALUE);
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		scheduler = context.getScheduler();
		Scheduler scheduler3 = null;
		try {
			scheduler3 = StdSchedulerFactory.getDefaultScheduler();
			System.out.println("--->");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(scheduler == scheduler1);
		System.out.println(scheduler == scheduler2);
		System.out.println(scheduler1 == scheduler2);
		System.out.println(scheduler1 == scheduler3);
	}

	// @Test
	public void test02() throws SchedulerException, InterruptedException {
		String className = "com.ebitg.commons.quartz.QuartzTest_Shutdown";
		String jobName = "任务名称";
		String jogGroup = "任务分组";
		String triggerName = "触发器名称";
		String cronExpression = "0/3 * * * * ? ";
		Map<String, Object> map = new HashMap<String, Object>();
		JobDataMap jobDataMap = new JobDataMap(map);
		Scheduler schedule = schedule(className, jobName, jogGroup, triggerName, cronExpression, jobDataMap);
		schedule.start();
		Thread.sleep(Long.MAX_VALUE);
	}

	public Scheduler schedule(String className, String jobName, String jogGroup, String triggerName,
			String cronExpression, JobDataMap jobDataMap) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			Trigger trigger = newTrigger().withIdentity(triggerName, jogGroup).startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
			Class<Job> jobClazz = (Class<Job>) Class.forName(className);
			JobBuilder jobBuilder = newJob(jobClazz).withIdentity(jobName, jogGroup);
			if (jobDataMap != null) {
				jobBuilder = jobBuilder.usingJobData(jobDataMap);
			}
			JobDetail job = jobBuilder.build();
			scheduler.scheduleJob(job, trigger);
			return scheduler;
		} catch (Exception e) {
			throw new RuntimeException("计划任务创建失败", e);
		}
	}
}
