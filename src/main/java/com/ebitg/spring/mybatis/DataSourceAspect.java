package com.ebitg.spring.mybatis;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {
	private static final Logger LOG = LoggerFactory.getLogger(DataSourceAspect.class);

	// @Before("execution(* com.ebitg.spring.mybatis.CsDao.*(*))")
	@Before("@annotation(com.ebitg.spring.mybatis.MyDataSource)")
	public void before(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Class clazz = signature.getDeclaringType();
		Method method = signature.getMethod();
		MyDataSource myds = method.getDeclaredAnnotation(MyDataSource.class);
		if (null == myds) {
			myds = (MyDataSource) clazz.getDeclaredAnnotation(MyDataSource.class);
		}
		LOG.debug("ÇÐ»»DatSourceÎª{}", myds.value());
		MultipleDataSource.setDataSourceKey(myds.value());
	}

}
