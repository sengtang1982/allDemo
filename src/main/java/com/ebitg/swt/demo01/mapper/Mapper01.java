package com.ebitg.swt.demo01.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface Mapper01 {
	@Select("select billid,invtype,entryid from zx_bill_doc_v where rownum<10")
	List<Object[]> query01();
}
