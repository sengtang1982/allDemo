package com.ebitg.spring.mybatis_multiple_ds;

import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 多数据源使用测试
 * 
 * @author Administrator
 *
 */
public class MdsTest {

	public static void main(String[] args) throws ClassNotFoundException {
		String DS_NAME = "sqlserver";
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
		Map<String, SqlSessionFactory> sqlSessionFactoryMap = (Map<String, SqlSessionFactory>) ctx
				.getBean("sqlSessionFactoryMap");
		System.out.println(sqlSessionFactoryMap.get(DS_NAME));
		Map<String, SqlSessionTemplate> sqlSessionTemplates = (Map<String, SqlSessionTemplate>) ctx
				.getBean("sqlSessionTemplates");
		SqlSessionTemplate sqlSessionTemplate = sqlSessionTemplates.get(DS_NAME);
		Config config = ctx.getBean(Config.class);
		config.print();
	}
}
