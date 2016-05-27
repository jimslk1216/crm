package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.bean.Customer;
import com.atguigu.crm.bean.CustomerActivity;

public interface CustomerMapper {

	void saveCust(@Param("cus") Customer cus);

	long getById(String no);

	int getTotalElements(Map<String,Object> map);

	List<Customer> getContent(Map<String, Object> map);

	List<String> getDicts(String type);


}
