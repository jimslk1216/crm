package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.bean.SalesChance;

public interface SalesChanceMapper {
	
	List<SalesChance> test(Map<String, Object> params);


	
	public long getTotalElements();

	public List<SalesChance> getContent(Map<String, Object> map);

	public void delete(Integer id);

	public void save(SalesChance chance);

	public SalesChance getById(Integer id);

	public void update(SalesChance chance);

	public long getTotalElements2(Map<String, Object> params2);

	public List<SalesChance> getContent2(Map<String, Object> params2);

	public int getTotalElements3(long id);

	public List<SalesChance> getContent3(Map<String, Object> map);

	public void updateStatus(SalesChance chance);

}
