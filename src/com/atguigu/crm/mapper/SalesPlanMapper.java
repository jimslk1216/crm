package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.bean.SalesChance;
import com.atguigu.crm.bean.SalesPlan;

public interface SalesPlanMapper {
	
	public void savePlan(Map<String,Object> params);
	
	public SalesChance getById(@Param("id") Integer id);

	List<SalesChance> getContent(Map<String, Object> params);

	long getTotalElements();

	public int updatePlan(Map<String, Object> params);

	public int deletePlan(@Param("id")Long id);

	public int saveResult(Map<String, Object> params);
}
