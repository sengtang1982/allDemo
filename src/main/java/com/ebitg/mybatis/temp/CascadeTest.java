package com.ebitg.mybatis.temp;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.alibaba.fastjson.JSON;

public class CascadeTest {
	public static void main(String[] args) throws IOException {
		InputStream in = Resources.getResourceAsStream("com/ebitg/mybatis/temp/mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		SqlSession session = sqlSessionFactory.openSession();
		MenuMapper mapper = session.getMapper(MenuMapper.class);
		List<Menu> menus = mapper.findMenuTree(0);
		for (int i = 0; i < menus.size(); i++) {
			Menu menu = menus.get(i);
			System.out.println(menu.getId() + "," + menu.getMenuName());
			List<Menu> childNodes = menu.getChildNodes();
			for (int j = 0; j < childNodes.size(); j++) {
				Menu menu2 = childNodes.get(j);
				System.out.println("\t" + menu2.getId() + "," + menu2.getMenuName());
			}
		}
		System.out.println("-------");

		String jsonString = JSON.toJSONString(menus);
		System.out.println(jsonString);
	}
}
