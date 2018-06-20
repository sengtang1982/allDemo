package com.ebitg.commons.quartz;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.ebitg.sms.YunPianSms;

public class TimerTest {
	public static void main(String[] args) throws ParseException {
		Timer timer = new Timer();
		Date firstTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-12-27 03:00:00");
		long peroid = 1000 * 60 * 60 * 24;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				String apikey = "e79f9d122a27b00a164614d2edc979a9";
				String mobile = "13566447138";
				String text = "��kobe���Ի������Ķ�����ţ���ʤԶ,������Ϣ����ʤԶ,����ĵ绰��֪��������ôʺһ���Ĵ���.֪�����Ƿ��������ü��ڱ���.";
				try {
					System.out.println(YunPianSms.sendSms(apikey, text, mobile));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, firstTime, peroid);
	}
}
