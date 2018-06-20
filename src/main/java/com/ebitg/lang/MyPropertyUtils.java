package com.ebitg.lang;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPropertyUtils {
	private static final Logger LOG = LoggerFactory.getLogger(MyPropertyUtils.class);

	// static {
	// ConvertUtils.register(new DateLocaleConverter(), Date.class);
	// }

	/**
	 * 将一个对象的属�?赋�?给另�?��属�?，如果属性名相同则直接复制，如果属�?名不同，则根据映射字符串進行匹配<br/>
	 * 支持int<-->String, int<-->boolean,String<-->Date之间的转�?
	 * 
	 * @param orginalObj
	 *            原始对象
	 * @param targetObj
	 *            目标对象
	 * @param propMapStr
	 *            哪些属�?�?��复制，格式为"对象1的属性X1->对象2的属性X2,对象1的属性Y1->对象2的属性Y2,..."
	 * @throws RuntimeException
	 *             如果转换异常将抛出异�?
	 */
	public static void copyProperties(Object orginalObj, Object targetObj, String propMapStr) {
		try {
			LOG.info("OrginalObj的初始�?为{}", orginalObj);
			// BeanUtils.copyProperties(targetObj, orginalObj);
			if (StringUtils.isBlank(propMapStr)) {
				LOG.warn("初始构造的targetObj:" + targetObj + ",如果没有指定属性名称,可直接使用BeanUtils.copyProperties");
				return;
			}
			for (String propMap : propMapStr.split(",")) {
				String[] prop1prop2 = propMap.split(">");
				copyProperties(orginalObj, prop1prop2[0], targetObj, prop1prop2[1]);
			}
			LOG.info("初始构�?的targetObj:" + targetObj);
		} catch (Exception e) {
			throw new RuntimeException("属性复制错误", e);
		}
	}

	private static void copyProperties(Object orginalObj, String p1, Object targetObj, String p2)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Class b1Type = PropertyUtils.getPropertyType(orginalObj, p1);
		Class b2Type = PropertyUtils.getPropertyType(targetObj, p2);
		Object b1Value = PropertyUtils.getProperty(orginalObj, p1);
		Object b2Value = b1Value;
		if (!b1Type.isAssignableFrom(b2Type)) {
			b2Value = ConvertUtils.convert(b1Value, b2Type);
		}
		LOG.info("将targetObj的{}属性,设置为{}", p2, b2Value);
		PropertyUtils.setProperty(targetObj, p2, b2Value);
	}
}
