package com.ebitg.spring.mybatis_multiple_ds;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class Config {
	private static final String DB_PREFIX = "db_";
	@javax.annotation.Resource(name = "sqlSessionFactoryMap")
	private Map<String, SqlSessionFactory> sqlSessionFactoryMap;
	@Autowired
	private ApplicationContext context;

	@Bean("sqlSessionFactoryMap")
	public Map<String, SqlSessionFactory> sqlSessionFactoryMap() throws Exception {
		Map<String, SqlSessionFactory> sqlSessionFactories = new HashMap<String, SqlSessionFactory>();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath*:*.properties");
		for (int i = 0; i < resources.length; i++) {
			String fileName = resources[i].getFilename();
			System.out.println(fileName);
			if (fileName.startsWith(DB_PREFIX)) {
				String key = FilenameUtils.getBaseName(fileName).substring(DB_PREFIX.length());
				Properties props = PropertiesLoaderUtils.loadProperties(resources[i]);
				BasicDataSource ds = BasicDataSourceFactory.createDataSource(props);
				SqlSessionFactory sqlSessionFactory = buildSessionFacotry(ds);
				sqlSessionFactories.put(key, sqlSessionFactory);
			}
		}
		return sqlSessionFactories;
	}

	private SqlSessionFactory buildSessionFacotry(BasicDataSource ds) {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(ds);
		bean.setTypeAliasesPackage("com.ebitg.spring.mybatis.multiple_ds");
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			bean.setMapperLocations(resolver.getResources("com/ebitg/spring/mybatis/*.xml"));
			return bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Bean
	public Map<String, SqlSessionTemplate> sqlSessionTemplates() {
		Map<String, SqlSessionTemplate> sqlSessionTemplates = new HashMap<String, SqlSessionTemplate>();
		Iterator<String> keys = sqlSessionFactoryMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			System.out.println("key==" + key);
			sqlSessionTemplates.put(key, new SqlSessionTemplate(sqlSessionFactoryMap.get(key)));
		}
		return sqlSessionTemplates;
	}

	public void print() {
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getParentBeanFactory();
		AutowireCapableBeanFactory beanFactory1 = context.getAutowireCapableBeanFactory();
		System.out.println("beanFactory:" + beanFactory);
		System.out.println("beanFactory1:" + beanFactory1);
		DefaultListableBeanFactory temp = (DefaultListableBeanFactory) beanFactory1;
		temp.createBean(Person.class);
		beanFactory1.autowireBeanProperties(new Person(), AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
		int count = context.getBeanDefinitionCount();
		String[] names = context.getBeanDefinitionNames();
		System.out.println(">>>>" + count);
		for (String n : names) {
			System.out.println(n);
		}
	}

}
