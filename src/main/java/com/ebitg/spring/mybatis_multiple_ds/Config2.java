package com.ebitg.spring.mybatis_multiple_ds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config2 implements BeanDefinitionRegistryPostProcessor {
	private static final String DB_PREFIX = "db_";
	private static final String SQL_SESSION_TEMPLATE_PREFIX = "sst_";
	private static final Logger LOG = LoggerFactory.getLogger(Config2.class);

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		LOG.debug("postProcessBeanFactory....");
		beanFactory.registerSingleton("ABC", new Person());
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		LOG.debug("postProcessBeanDefinitionRegistry....");
		BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Person.class).getBeanDefinition();
		registry.registerBeanDefinition("myBeanName", beanDefinition);
	}

}
