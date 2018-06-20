package com.ebitg.lang;

import java.util.Date;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonTest {
	@Test
	public void testJSON() {
		String r = JSON.toJSONString(new Date(), SerializerFeature.WriteDateUseDateFormat);
		System.out.println(r);
	}
}
