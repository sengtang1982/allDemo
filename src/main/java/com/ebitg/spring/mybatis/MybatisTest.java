package com.ebitg.spring.mybatis;

import java.sql.Connection;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MybatisTest {
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config2.class);
	SqlSessionFactory bean = context.getBean(SqlSessionFactory.class);

	@Test
	public void test02() {
		SqlSession session = bean.openSession();
		Connection conn = session.getConnection();
	}

	@Test
	public void test01() {
		CsDao csDao = context.getBean(CsDao.class);
		System.out.println(csDao.getLastModifyTime(""));
	}

}
