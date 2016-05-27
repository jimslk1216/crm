package com.atguigu.crm.mapper;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.bean.Contact;

public interface ContactMapper {

	void saveCont(@Param("con") Contact con);
	
}
