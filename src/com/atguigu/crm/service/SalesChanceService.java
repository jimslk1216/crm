package com.atguigu.crm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.bean.Contact;
import com.atguigu.crm.bean.Customer;
import com.atguigu.crm.bean.SalesChance;
import com.atguigu.crm.mapper.ContactMapper;
import com.atguigu.crm.mapper.CustomerMapper;
import com.atguigu.crm.mapper.SalesChanceMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.utils.DataProcessUtils;

@Service
public class SalesChanceService {
	@Autowired
	private ContactMapper contactMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private SalesChanceMapper salesChanceMapper;

	/**
	 * 带查询条件的分页
	 * 
	 * @param pageNo
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public Page<SalesChance> getPage2(int pageNo,
			Map<String, Object> requestParams) throws ParseException {
		Page<SalesChance> page = new Page<SalesChance>();
		page.setPageNo(pageNo);
		// 将请求参数转化为MyBatis需要的参数集合
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> params = DataProcessUtils.parseRequestParams2MyBatisParams(requestParams ,dateFormat);
		int totalElements = (int) salesChanceMapper.getTotalElements2(params);

		page.setTotalElements(totalElements);
		// 查询当前页面的 content
		int firstIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = firstIndex + page.getPageSize();
		params.put("firstIndex", firstIndex);
		params.put("endIndex", endIndex);
		List<SalesChance> content = salesChanceMapper.getContent2(params);
		// 为 Page 对象的 content 赋值
		page.setContent(content);
		return page;
	}


	@Transactional(readOnly = true)
	public Page<SalesChance> getPage(int pageNo) {
		// 创建 Page 对象
		Page<SalesChance> page = new Page<SalesChance>();
		// 设置 pageNo 属性. 同时校验 pageNo 的合法性: 校验其是否大于 0
		page.setPageNo(pageNo);
		// 获取总的记录数. 校验 pageNo 的合法性: 此时已经可以由 总的记录数和 pageSize 计算出总页数, 进而校验 pageNo
		// 是否在合法的区间
		int totalElements = (int) salesChanceMapper.getTotalElements();

		page.setTotalElements(totalElements);
		// 查询当前页面的 content
		Map<String, Object> map = new HashMap<String, Object>();
		int firstIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = firstIndex + page.getPageSize();
		map.put("firstIndex", firstIndex);
		map.put("endIndex", endIndex);
		List<SalesChance> content = salesChanceMapper.getContent(map);
		// 为 Page 对象的 content 赋值
		page.setContent(content);
		return page;
	}

	@Transactional
	public void delete(Integer id) {
		salesChanceMapper.delete(id);
	}

	@Transactional
	public void save(SalesChance chance) {
		chance.setStatus(1);
		salesChanceMapper.save(chance);

	}

	@Transactional
	public SalesChance getById(String idStr) {
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		return salesChanceMapper.getById(id);
	}

	@Transactional
	public void update(SalesChance chance) {
		salesChanceMapper.update(chance);

	}

/*	public void saveDispatch(SalesChance chance) {
		chance.setStatus(2);
		salesChanceMapper.saveDispatch(chance);

	}*/

	// 开发失败
	@Transactional
	public void stop(Integer id) {
		SalesChance byId = salesChanceMapper.getById(id);
		byId.setStatus(4);
		System.out.println(byId.getStatus());
		salesChanceMapper.update(byId);
	}

	// 客户开发成功
	@Transactional
	public void finish(int id) {
		// 获取当前客户信息
		SalesChance chance = salesChanceMapper.getById(id);
		// 改为开发成功
		chance.setStatus(3);
		salesChanceMapper.updateStatus(chance);
	}

	public Page<SalesChance> getPage3(int pageNo, Long id) {
		// 创建 Page 对象
		Page<SalesChance> page = new Page<SalesChance>();
		// 设置 pageNo 属性. 同时校验 pageNo 的合法性: 校验其是否大于 0
		page.setPageNo(pageNo);
		// 获取总的记录数. 校验 pageNo 的合法性: 此时已经可以由 总的记录数和 pageSize 计算出总页数, 进而校验 pageNo
		// 是否在合法的区间
		int totalElements = (int) salesChanceMapper.getTotalElements3(id);
		page.setTotalElements(totalElements);
		// 查询当前页面的 content
		Map<String, Object> map = new HashMap<String, Object>();
		int firstIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = firstIndex + page.getPageSize();
		map.put("firstIndex", firstIndex);
		map.put("endIndex", endIndex);
		map.put("id", id);
		List<SalesChance> content = salesChanceMapper.getContent3(map);
		// 为 Page 对象的 content 赋值
		page.setContent(content);
		return page;
	}

}
