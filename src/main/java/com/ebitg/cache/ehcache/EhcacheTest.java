package com.ebitg.cache.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.Assert;
import org.junit.Test;

public class EhcacheTest {
	@Test
	public void test01() {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("preConfigured", CacheConfigurationBuilder
						.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(100))
						.build())
				.build(true);
		Cache<String, String> preConfigured = cacheManager.getCache("preConfigured", String.class, String.class);
		Cache<String, String> myCache = cacheManager.createCache("myCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(100)).build());
		String value = "da one!";
		myCache.put("k1", value);
		String getValue1 = myCache.get("k1");
		Assert.assertEquals(getValue1, value);
		cacheManager.close();
	}
}
