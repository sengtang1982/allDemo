package com.ebitg.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class UnsubscribeTest {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		JedisPubSub jedisPubSub = new JedisPubSub() {
			@Override
			public void unsubscribe() {
				
				super.unsubscribe();
			}
		};
		jedisPubSub.unsubscribe();
	}
}
