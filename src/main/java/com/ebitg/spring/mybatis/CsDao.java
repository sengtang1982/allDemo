package com.ebitg.spring.mybatis;

import java.util.Date;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@MyDataSource("mysql")
public interface CsDao {
	@Select("select recordTime from MyLog where id=1")
	public Date getLastModifyTime(String tblName);

}
