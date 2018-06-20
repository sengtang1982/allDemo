package com.ebitg.spring.customize_annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.ebitg.spring.mybatis_multiple_ds.Person;

@Configuration
public class CustomizeAnnotationTest implements BeanFactoryPostProcessor {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				CustomizeAnnotationTest.class);
		Person bean = (Person) context.getBean("ABC1");
		System.out.println(bean);
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("postProcessBeanFactory¡·¡·¡·");
		beanFactory.registerSingleton("ABC1", new Person());
	}

}
