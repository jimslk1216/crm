package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.bean.Customer;
import com.atguigu.crm.bean.CustomerActivity;
import com.atguigu.crm.bean.SalesChance;
import com.atguigu.crm.mapper.CustomerMapper;
import com.atguigu.crm.orm.Page;

@Service
public class CustomersService {
	@Autowired
	private CustomerMapper cm;

	/**
	 * 2016年5月16日 下午10:06:12 dell
	 * 
	 * @param pageNo
	 * @return
	 */
	// 获取分页数据（客户基本信息管理）
	public Page<Customer> getPage(int pageNo, Map<String, Object> param) {
		Map<String, Object> map = parametersStarting2mybatis(param);
		// 创建 Page 对象
		Page<Customer> page = new Page<Customer>();
		// 设置 pageNo 属性. 同时校验 pageNo 的合法性: 校验其是否大于 0
		page.setPageNo(pageNo);
		// 获取总的记录数. 校验 pageNo 的合法性: 此时已经可以由 总的记录数和 pageSize 计算出总页数, 进而校验 pageNo
		// 是否在合法的区间
		int totalElements = (int) cm.getTotalElements(map);
		page.setTotalElements(totalElements);
		// 查询当前页面的 content
		int firstIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = firstIndex + page.getPageSize();
		map.put("firstIndex", firstIndex);
		map.put("endIndex", endIndex);
		List<Customer> content = cm.getContent(map);
		// 为 Page 对象的 content 赋值
		page.setContent(content);
		return page;
	}

	/**
	 * 2016年5月18日 下午3:34:07 dell 拼接让数据库能识别
	 */
	private Map<String, Object> parametersStarting2mybatis(
			Map<String, Object> param) {
		Map<String, Object> map = new HashMap<>();
		for (Map.Entry<String, Object> entry : param.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null || value.toString().trim().equals("")) {
				continue;
			}
			if (key.startsWith("LIKE_")) {
				key = key.substring(5, key.length());
				if (key.equals("manager.name")) {
					key = "managerName";
				}
				value = "%" + value.toString().trim() + "%";
				map.put(key, value);
			}
			if (key.startsWith("EQ_")) {
				key = key.substring(3, key.length());
				map.put(key, value.toString().trim());
			}
		}

		return map;
	}

	/**
	 * 2016年5月16日 下午11:31:03 dell 通过字段获取type信息
	 */
	public List<String> getDicts(String type) {
		return cm.getDicts(type);
	}

}
