package com.ebitg.commons.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
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
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyJob implements Job {
	private static final Logger LOG = LoggerFactory.getLogger(MyJob.class);
	private static int count = 0;
	private String name;
	private String group;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOG.debug("{}=====================================================>begin", Thread.currentThread().getName());
		SchedulerMetaData metaData;
		try {
			metaData = context.getScheduler().getMetaData();
			LOG.debug("~~~~~~~~~~  执行了 {}个 jobs.", metaData.getNumberOfJobsExecuted());
		} catch (SchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//			if (count == 3) {
//				JobDataMap jobDataMap = context.getMergedJobDataMap();
//				String jobName = jobDataMap.getString("jobName");
//				String jobGroup = jobDataMap.getString("jobGroup");
//				// context.getScheduler().shutdown(true);
//				// context.getScheduler().deleteJob(JobKey.jobKey(jobName, jobGroup));
//				// context.getScheduler().pauseAll();
//			}
			int random = RandomUtils.nextInt(0, 9);
			LOG.debug("进行第{}次执行,随机值{}", ++count,random);
			if (random > 5) {
				JobExecutionException jobExecutionException = new JobExecutionException();
				jobExecutionException.setUnscheduleAllTriggers(true);
				jobExecutionException.setRefireImmediately(true);
				throw jobExecutionException;
			}
			LOG.debug("context.getCalander():{}", context.getCalendar());
			LOG.debug("context.getFireTime():{}", context.getFireTime());
			LOG.debug("context.getRefireCount():{}", context.getRefireCount());
			LOG.debug("context.getJobRunTime():{}", context.getJobRunTime());
			LOG.debug("new Date(context.getJobRunTime()):{}", new Date(context.getJobRunTime()));
			LOG.debug("{}<///////////////////////end", Thread.currentThread().getName());
	}

	@Test
	public void test01() throws SchedulerException, InterruptedException {
		String className = "com.ebitg.commons.quartz.MyJob";
		String jobName = "任务名称";
		String jogGroup = "任务分组";
		String triggerName = "触发器名称";
		String cronExpression = "0/10 * * * * ? ";
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
