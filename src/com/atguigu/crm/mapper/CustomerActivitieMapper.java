package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.bean.Customer;
import com.atguigu.crm.bean.CustomerActivity;

public interface CustomerActivitieMapper {
	Customer getCustomer(int id);

	int getTotalElements(int id);

	List<CustomerActivity> getPage(Map<String, Object> map);

	void save(@Param("ca")CustomerActivity ca);

	CustomerActivity getById(int id);

	void update(@Param("ca")CustomerActivity ca);

	void delete(int id);
	
	
}
