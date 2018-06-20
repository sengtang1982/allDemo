package com.ebitg.spring.mybatis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@MapperScan("com.ebitg.spring.mybatis")
@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.ebitg.spring.mybatis")
public class Config2 implements TransactionManagementConfigurer {
	public static final String DEFAULT = "default";
	private static final String DB_PREFIX = "db_";
	private static final Logger LOG = LoggerFactory.getLogger(Config2.class);

	@Autowired
	private DataSource dataSource;

	@Bean
	public DataSource dataSource() throws Exception {
		LOG.debug("postProcessBeanFactory...");
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		MultipleDataSource mds = new MultipleDataSource();
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		try {
			Resource[] resources = resolver.getResources("classpath*:*.properties");

			for (int i = 0; i < resources.length; i++) {
				String fileName = resources[i].getFilename();
				LOG.debug(fileName);
				if (fileName.startsWith(DB_PREFIX)) {
					String key = FilenameUtils.getBaseName(fileName).substring(DB_PREFIX.length());
					LOG.debug("ds的名字" + key);
					Properties props = PropertiesLoaderUtils.loadProperties(resources[i]);
					BasicDataSource ds = BasicDataSourceFactory.createDataSource(props);
					targetDataSources.put(key, ds);
					LOG.debug("初始化DataSource.key={},value={}", key, ds.toString());
					if (i == 0) {
						targetDataSources.put(DEFAULT, ds);
					}
					if (DEFAULT.equalsIgnoreCase(key)) {
						mds.setDefaultTargetDataSource(ds);
					}
				}
			}
			mds.setTargetDataSources(targetDataSources);
			mds.setDefaultTargetDataSource(dataSource);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mds;
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		try {
			return bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

}
