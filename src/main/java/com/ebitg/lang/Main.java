package com.ebitg.lang;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		Runnable command = new Runnable() {
			@Override
			public void run() {
				System.out.println(new Date());
			}
		};
		scheduledExecutorService.schedule(command, 5, TimeUnit.SECONDS);
	}
}
