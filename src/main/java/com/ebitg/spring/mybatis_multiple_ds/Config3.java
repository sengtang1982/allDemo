package com.ebitg.spring.mybatis_multiple_ds;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class Config3 implements BeanFactoryPostProcessor {
	private static final String SQL_SESSION_FACTORY_PREFIX = "ssf_";
	private static final String DB_PREFIX = "db_";
	private static final String SQL_SESSION_TEMPLATE_PREFIX = "sst_";
	private static final Logger LOG = LoggerFactory.getLogger(Config3.class);

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		LOG.debug("postProcessBeanFactory...");
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			Resource[] resources = resolver.getResources("classpath*:*.properties");
			for (int i = 0; i < resources.length; i++) {
				String fileName = resources[i].getFilename();
				LOG.debug(fileName);
				if (fileName.startsWith(DB_PREFIX)) {
					String key = FilenameUtils.getBaseName(fileName).substring(DB_PREFIX.length());
					LOG.debug("dsµÄÃû×Ö" + key);
					Properties props = PropertiesLoaderUtils.loadProperties(resources[i]);
					BasicDataSource ds = BasicDataSourceFactory.createDataSource(props);
					SqlSessionFactory sqlSessionFactory = buildSessionFacotry(ds);
					LOG.debug("ds>>>>>{}<<<<<,>>>>>ssf{}<<<<",ds,sqlSessionFactory);
					beanFactory.registerSingleton(SQL_SESSION_FACTORY_PREFIX + key, sqlSessionFactory);
					SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
					beanFactory.registerSingleton(SQL_SESSION_TEMPLATE_PREFIX + key, sqlSessionTemplate);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

}
