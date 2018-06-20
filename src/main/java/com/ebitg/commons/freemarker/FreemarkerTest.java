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
			// ����һ�����ʵ�Configration����
			Configuration configuration = new Configuration(new Version("2.3.20"));
			configuration.setDirectoryForTemplateLoading(new File("src/main/java/com/ebitg/commons/freemarker"));
			// configuration.setObjectWrapper(new DefaultObjectWrapper());
			configuration.setDefaultEncoding("UTF-8"); // ���һ��Ҫ���ã���Ȼ�����ɵ�ҳ���� ������
			configuration.setClassicCompatible(true);
			// ��ȡ�򴴽�һ��ģ�档
			Template template = configuration.getTemplate("static.html");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("description", "������ѧϰʹ��Freemarker���ɾ�̬�ļ���");

			List<String> nameList = new ArrayList<String>();
			nameList.add("�¾���");
			nameList.add("���");
			nameList.add("������");
			paramMap.put("nameList", nameList);

			Map<String, Object> weaponMap = new HashMap<String, Object>();
			weaponMap.put("first", "��ԯ��");
			weaponMap.put("second", "���ӡ");
			weaponMap.put("third", "Ů�ʯ");
			weaponMap.put("fourth", "��ũ��");
			weaponMap.put("fifth", "������");
			weaponMap.put("sixth", "���ؾ�");
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
			System.out.println("��ϲ�����ɳɹ�~~");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
