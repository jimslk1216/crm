package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.bean.CustomerDrain;
import com.atguigu.crm.mapper.CustomersDrainMapper;
import com.atguigu.crm.orm.Page;

@Service
public class CustomersDrainService {
	@Autowired
	private CustomersDrainMapper cdm;

	/**
	 * 2016年5月18日 下午8:23:59 dell 获取分页数据
	 */
	@Transactional(readOnly = true)
	public Page<CustomerDrain> getPage(Map<String, Object> parame, int pageNo) {
		Map<String, Object> map = parame2mybatis(parame);
		Page<CustomerDrain> page = new Page<>();
		page.setPageNo(pageNo);
		// 获取总数
		int totalElements = cdm.getTotalElements(map);
		page.setTotalElements(totalElements);
		int firstIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = firstIndex + page.getPageSize();

		map.put("firstIndex", firstIndex);
		map.put("endIndex", endIndex);

		List<CustomerDrain> cus = cdm.getPage(map);
		page.setContent(cus);
		// 获取分页数据
		return page;
	}

	/**
	 * 2016年5月18日 下午8:16:18 dell 转换为mybatis能识别的查询语句
	 */
	private Map<String, Object> parame2mybatis(Map<String, Object> parame) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : parame.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null || value.toString().trim().equals("")) {
				continue;
			}
			if (key.startsWith("LIKE_")) {
				key = key.substring(5);
				if (key.equals("customer.manager.name")) {
					key = "customer_manager_name";
				}
				if (key.startsWith("customer.name")) {
					key = "customer_name";
				}
				map.put(key, "%" + value.toString().trim() + "%");
			}
		}

		return map;
	}

	@Transactional
	public void callcheckDrainProcedure() {
		cdm.callcheckDrainProcedure();
	}

	@Transactional(readOnly = true)
	public CustomerDrain getById(int id) {
		return cdm.getById(id);
	}

	public int delay(int id, String texr) {
		return cdm.delay(id, texr);
	}

	/**2016年5月19日
	 *下午5:27:48
	 *dell
	 *确认流失
	 */
	@Transactional
	public void confirm(CustomerDrain cd) {
		cdm.updeteCus((int)(long)cd.getId());
		cdm.updetecd(cd);
		
		
	}
}
