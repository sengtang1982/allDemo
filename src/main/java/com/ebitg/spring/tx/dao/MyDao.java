package com.ebitg.spring.tx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ebitg.spring.tx.T1;

@Mapper
public interface MyDao {
	@Select("select * from T1 where updateTime is null  ORDER BY RAND() limit 10  ")
	List<T1> findAll();

	@Select("select * from T1 where id=#{id}")
	T1 syncGet(@Param("id") int id);

	@Update("update T1 set updateCount=updateCount+1,updateTime=#{updateTime} where id=#{id} ")
	void update(T1 t1db);

}
