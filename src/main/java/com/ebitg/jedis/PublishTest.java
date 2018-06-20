package com.ebitg.jedis;

import java.text.SimpleDateFormat;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class PublishTest {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	public static void main(String[] args) throws InterruptedException {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		JedisPubSub jedisPubSub = new JedisPubSub() {

		};
		jedis.subscribe(jedisPubSub, "c1");
	}
}
