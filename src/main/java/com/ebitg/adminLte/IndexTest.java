package com.ebitg.adminLte;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class IndexTest {

	@Test
	public void test01() throws IOException, TemplateException {
		Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		URL url = IndexTest.class.getResource(".");
		configuration.setDirectoryForTemplateLoading(new File(url.getPath()));
		configuration.setDefaultEncoding("UTF-8"); // 这个一定要设置，不然在生成的页面中 会乱码
		configuration
				.setObjectWrapper(new BeansWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).build());
		Template template = configuration.getTemplate("AdminLTE_02.html");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Menu> menus = new ArrayList<Menu>();
		menus.add(new Menu("标题1", "http://www.baidu.com", true, "laptop", null));
		menus.add(new Menu("标题2", "http://www.163.com", false, null, null));
		List<Menu> subMenus = new ArrayList<Menu>();
		subMenus.add(new Menu("子菜单1", "http://www.baidu.com", true, "laptop", null));
		subMenus.add(new Menu("子菜单2", "http://www.baidu.com", true, "laptop", null));
		menus.add(new Menu("标题3", "http://www.163.com", false, null, subMenus));
		paramMap.put("menuList", menus);
		Writer writer = new OutputStreamWriter(new FileOutputStream("D:\\logs\\A.html"), "UTF-8");
		template.process(paramMap, writer);
	}
}
