package com.ebitg.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class XMLConfigBuilderTest {
	private static InputStream in;

	@BeforeClass
	public static void beforeClass() throws IOException {
		in = Resources.getResourceAsStream("com/ebitg/mybatis/mybatis-config.xml");
	}

	@Test
	@Ignore
	public void test01() throws IOException {

		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		System.out.println(sqlSessionFactory);
	}

	@Test
	@Ignore
	public void test02() throws IOException {
		XMLConfigBuilder parser = new XMLConfigBuilder(in);
		System.out.println(parser.parse());
	}

	@Test
	public void test03() {
		MyXMLConfigBuilder parse = new MyXMLConfigBuilder(in);
		Configuration config = parse.parse();
		System.out.println(config);
		Collection<String> mappedStatementNames = config.getMappedStatementNames();
		System.out.println(mappedStatementNames);
	}
}
