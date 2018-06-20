package com.ebitg.swt.demo01;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.ebitg.swt.demo01.mapper.Mapper01;

public class Demo2 {
	public static void main(String[] args) throws IOException {
		InputStream in = Resources.getResourceAsStream("com/ebitg/swt/demo01/mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		SqlSession session = sqlSessionFactory.openSession();
		Mapper01 mapper = session.getMapper(Mapper01.class);
		List<Object[]> result = mapper.query01();
		session.close();
		for (int i = 0; i < result.size(); i++) {
			Object[] objects = result.get(i);
			for (int j = 0; j < objects.length; j++) {
				System.out.print(objects[j] + "\t");
			}
			System.out.println();
		}
	}
}
