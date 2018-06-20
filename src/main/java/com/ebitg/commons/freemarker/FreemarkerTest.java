package com.ebitg.commons.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

public class FreemarkerTest {
	@Test
	public void test01() {
		try {
			// 创建一个合适的Configration对象
			Configuration configuration = new Configuration(new Version("2.3.20"));
			configuration.setDirectoryForTemplateLoading(new File("src/main/java/com/ebitg/commons/freemarker"));
			// configuration.setObjectWrapper(new DefaultObjectWrapper());
			configuration.setDefaultEncoding("UTF-8"); // 这个一定要设置，不然在生成的页面中 会乱码
			configuration.setClassicCompatible(true);
			// 获取或创建一个模版。
			Template template = configuration.getTemplate("static.html");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("description", "我正在学习使用Freemarker生成静态文件！");

			List<String> nameList = new ArrayList<String>();
			nameList.add("陈靖仇");
			nameList.add("玉儿");
			nameList.add("宇文拓");
			paramMap.put("nameList", nameList);

			Map<String, Object> weaponMap = new HashMap<String, Object>();
			weaponMap.put("first", "轩辕剑");
			weaponMap.put("second", "崆峒印");
			weaponMap.put("third", "女娲石");
			weaponMap.put("fourth", "神农鼎");
			weaponMap.put("fifth", "伏羲琴");
			weaponMap.put("sixth", "昆仑镜");
			weaponMap.put("seventh", null);
			paramMap.put("weaponMap", weaponMap);

			Writer writer = new OutputStreamWriter(new FileOutputStream("success.html"), "UTF-8");
			template.process(paramMap, new PrintWriter(System.out));
			Writer strOut = new StringWriter(2048);
			template.process(paramMap, new PrintWriter(System.out));
			template.process(paramMap, strOut);
			System.out.println("\n================\n");
			System.out.println(strOut);
			System.out.println("================\n");
			System.out.println("恭喜，生成成功~~");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
