package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.bean.SalesChance;
import com.atguigu.crm.mapper.SalesPlanMapper;
import com.atguigu.crm.orm.Page;

@Service
public class SalesPlanService {
	
	@Autowired
	private SalesPlanMapper salesPlanMapper;
	
	@Transactional
	public void savePlan(Map<String,Object> params){
		salesPlanMapper.savePlan(params);
	}
	
	
	@Transactional(readOnly = true)
	public Page<SalesChance> getPage(int pageNo,Long userId) {
		// 创建 Page 对象
		Page<SalesChance> page = new Page<SalesChance>();
		// 设置 pageNo 属性. 同时校验 pageNo 的合法性: 校验其是否大于 0
		page.setPageNo(pageNo);
		// 获取总的记录数. 校验 pageNo 的合法性: 此时已经可以由 总的记录数和 pageSize 计算出总页数, 进而校验 pageNo
		// 是否在合法的区间
		int totalElements = (int) salesPlanMapper.getTotalElements();

		page.setTotalElements(totalElements);
		// 查询当前页面的 content
		Map<String, Object> map = new HashMap<String, Object>();
		int firstIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = firstIndex + page.getPageSize();
		map.put("firstIndex", firstIndex);
		map.put("endIndex", endIndex);
		map.put("userId", userId);
		List<SalesChance> content = salesPlanMapper.getContent(map);
		// 为 Page 对象的 content 赋值
		page.setContent(content);
		return page;
	}
	
	
	@Transactional(readOnly=true)
	//通过id获取某个对象
	public SalesChance getById(Integer id){
		return salesPlanMapper.getById(id);
		
	}

	@Transactional
	public int updatePlan(Map<String, Object> params) {
		return salesPlanMapper.updatePlan(params);
	}

	@Transactional
	public int deletePlan(Long id) {
		return salesPlanMapper.deletePlan(id);
	}

	@Transactional
	public int saveResult(Map<String, Object> params) {
		return salesPlanMapper.saveResult(params);
	}
	
	
	
}
