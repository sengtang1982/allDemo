package com.ebitg.spring.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultipleDataSource extends AbstractRoutingDataSource {
	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();
	private static final Logger LOG = LoggerFactory.getLogger(MultipleDataSource.class);

	public static void setDataSourceKey(String dataSource) {
		dataSourceKey.set(dataSource);
	}

	@Override
	protected Object determineCurrentLookupKey() {
		if (StringUtils.isBlank(dataSourceKey.get())) {
			dataSourceKey.set(Config2.DEFAULT);
		}
		LOG.debug("ÇÐ»»µ½dataSource={}", dataSourceKey.get());
		return dataSourceKey.get();
	}

}