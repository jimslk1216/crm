package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.bean.Customer;
import com.atguigu.crm.bean.CustomerActivity;
import com.atguigu.crm.mapper.CustomerActivitieMapper;
import com.atguigu.crm.orm.Page;

@Service
public class CustomerActivitieService {
	@Autowired
	private CustomerActivitieMapper cam;

	/**
	 * 2016年5月17日 上午12:54:55 dell 通过id获取一个customer
	 */
	public Customer getCustomer(int id) {
		return cam.getCustomer(id);
	}

	/**
	 * 2016年5月17日 上午1:18:35 dell 通过id获取交往记录分页
	 */
	public Page<CustomerActivity> getPage(int id, int pageNo) {
		Page<CustomerActivity> page = new Page<>();
		Map<String, Object> map = new HashMap<>();
		page.setTotalElements(cam.getTotalElements(id));
		page.setPageNo(pageNo);
		int firstIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = firstIndex + page.getPageSize();
		map.put("firstIndex", firstIndex);
		map.put("endIndex", endIndex);
		map.put("id", id);
		List<CustomerActivity> customeActivitys = cam.getPage(map);
		page.setContent(customeActivitys);
		return page;
	}

	/**
	 * 2016年5月20日 下午10:44:11 dell 保存客户交往
	 */
	public void save(CustomerActivity ca) {
		cam.save(ca);
	}

	public CustomerActivity getById(int id) {
		return cam.getById(id);
	}

	public void update(CustomerActivity ca) {
		cam.update(ca);
	}

	public void delete(int id) {

		cam.delete(id);
	}

}
