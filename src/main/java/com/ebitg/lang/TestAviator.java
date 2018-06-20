package com.ebitg.lang;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.aviator.AviatorEvaluator;

public class TestAviator {
	@Test
	@Ignore
	public void test01() {
		String yourName = "Michael";
		Map<String, Object> env = new HashMap<String, Object>();
		env.put("销售额", 5.0);
		env.put("计划额", 100.0);
		Double result = (Double) AviatorEvaluator.execute("销售额/计划额", env);
		System.out.println(result); // hello Michael

	}

	@Test
	public void test02() {
		AviatorEvaluator.setMathContext(MathContext.DECIMAL32);
		AviatorEvaluator.setOptimize(AviatorEvaluator.EVAL);
		String exp = "goodsqty + reqqty + placeqty + placeqty2 +  inqty + stqty-outqty";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("goodsqty", new BigDecimal(1.01));
		params.put("reqqty", new BigDecimal(2.19));
		params.put("placeqty", new BigDecimal(3.324));
		params.put("placeqty2", new BigDecimal(1));
		params.put("inqty", new BigDecimal(1));
		params.put("stqty", new BigDecimal(1));
		params.put("outqty", new BigDecimal(1));
		Object result = AviatorEvaluator.execute(exp, params);
		System.out.println(result);
	}

	@Test
	@Ignore
	public void test03() {
		System.out.println(1.01 + 2.19 + 3.324);
	}
}
