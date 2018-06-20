package com.ebitg.mybatis.temp;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface MenuMapper {

	@Select("select id,menuName,parentId from Menu where parentid=#{parentId}")
	// @Results({
	// @Result(property = "childNodes", column = "id", one = @One(fetchType =
	// FetchType.EAGER, select = "com.ebitg.mybatis.temp.MenuMapper.findMenuTree")),
	// @Result(property = "parent", column = "parentId", many = @Many(select =
	// "com.ebitg.mybatis.temp.MenuMapper.findById")) })
	@Results({ @Result(id = true, property = "id", column = "id", javaType = Integer.class),
			@Result(property = "childNodes", column = "id", one = @One(select = "com.ebitg.mybatis.temp.MenuMapper.findMenuTree")) })
	public List<Menu> findMenuTree(@Param("parentId") int parentId);

	@Select("select id,menuName,parentId from Menu where parentid=#{parentId}")
	@Results({ @Result(id = true, property = "id", column = "id", javaType = Integer.class),
			@Result(property = "childNodes", column = "id", one = @One(select = "com.ebitg.mybatis.temp.MenuMapper.findMenuTree1")),
			@Result(property = "parent", column = "parentId", many = @Many(select = "com.ebitg.mybatis.temp.MenuMapper.findById")) })
	public List<Menu> findMenuTree1(@Param("parentId") int parentId);

	@Select("select * from Menu where id=#{id}")
	public Menu findById(@Param("id") int id);

	@Insert("insert into Menu values()")
	public void insert();

}
