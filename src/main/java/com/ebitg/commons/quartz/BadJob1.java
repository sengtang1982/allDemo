package com.ebitg.commons.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BadJob1 implements Job {
	private static final Logger LOG = LoggerFactory.getLogger(BadJob1.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		LOG.error("任务key: " + jobKey + " ,执行时间: " + new Date());

		JobExecutionException e2 = new JobExecutionException();
//		// this job will refire immediately
		e2.setRefireImmediately(true);
		throw e2;

//		LOG.error("---" + jobKey + " completed at " + new Date());
	}

	@Test
	public void test01() throws SchedulerException, InterruptedException {
		String className = "com.ebitg.commons.quartz.BadJob1";
		String jobName = "任务名称";
		String jogGroup = "任务分组";
		String triggerName = "触发器名称";
		String cronExpression = "0 0 8 0 0 ?";
		Map<String, Object> map = new HashMap<String, Object>();
		JobDataMap jobDataMap = new JobDataMap(map);
		jobDataMap.put("jobName", jobName);
		jobDataMap.put("jogGroup", jogGroup);
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
			JobDetail job = newJob(jobClazz).withIdentity(jobName, jogGroup).usingJobData(jobDataMap).build();
			scheduler.scheduleJob(job, trigger);
			return scheduler;
		} catch (Exception e) {
			throw new RuntimeException("计划任务创建失败", e);
		}
	}
}
