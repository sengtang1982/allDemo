package com.ebitg.jedis;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JedisTest {
	private static Jedis jedis;

	@BeforeClass
	public static void beforeClass() {
		jedis = new Jedis("127.0.0.1", 6379);
	}

	@Test
	@Ignore
	public void tesSet() {
		jedis.set("k1", "v1");
	}

	@Test
	@Ignore
	public void testGet() {
		String v1 = jedis.get("k1");
		Assert.assertEquals("v1", v1);
	}

	@Test
	public void testSubscribe() {
		jedis.subscribe(new JedisPubSub() {
			public void onMessage(String channel, String message) {
				System.out.println("get a msg: " + "channel=" + channel + ", message=" + message);
			}

			public void onSubscribe(String channel, int subscribedChannels) {
				System.out.println("channel:" + channel + ", subscribedChannels:" + subscribedChannels);
			}

			public void onUnsubscribe(String channel, int subscribedChannels) {
				System.out.println("channel:" + channel + ", subscribedChannels:" + subscribedChannels);
			}

			public void onPSubscribe(String pattern, int subscribedChannels) {
				System.out.println("pattern:" + pattern + ", subscribedChannels:" + subscribedChannels);
			}

			public void onPUnsubscribe(String pattern, int subscribedChannels) {
				System.out.println("pattern:" + pattern + ", subscribedChannels:" + subscribedChannels);
			}

			public void onPMessage(String pattern, String channel, String message) {
				System.out.println("pattern:" + pattern + ", channel:" + channel + ", message:" + message);
			}
		}, "c1");
	}

	@AfterClass
	public static void afterClass() {
		jedis.close();
	}
}
