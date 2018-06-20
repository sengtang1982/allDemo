package com.ebitg.spring.mybatis;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@MapperScan("com.ebitg.spring.mybatis")
@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.ebitg.spring.mybatis")
public class Config implements TransactionManagementConfigurer {
	@Autowired
	private DataSource dataSource;

	@Bean
	public DataSource dataSource() throws Exception {
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/main/java/db_sqlserver.properties"));
		BasicDataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
		Properties properties1 = new Properties();
		properties1.load(new FileInputStream("src/main/java/db_mysql.properties"));
		BasicDataSource dataSource1 = BasicDataSourceFactory.createDataSource(properties1);
		MultipleDataSource mds = new MultipleDataSource();
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		targetDataSources.put("sqlserver", dataSource);
		targetDataSources.put("mysql", dataSource1);
		mds.setTargetDataSources(targetDataSources);
		mds.setDefaultTargetDataSource(dataSource);
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
