package com.atguigu.crm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.bean.Customer;
import com.atguigu.crm.mapper.StatisticalFormMapper;
import com.atguigu.crm.orm.Page;

@Service
public class StatisticalFormService {
	@Autowired
	private StatisticalFormMapper sfm;

	public Page<Customer> getPage(int pageNo, Map<String, Object> with) throws ParseException {
		Page<Customer> pages = new Page<>();
		// 转换成mybatis能识别的代码
		Map<String, Object> map = queryAndMyBatis(with);
		pages.setPageNo(pageNo);
		//查询总数
		
		int fistIdex=(pages.getPageNo()-1)*pages.getPageSize()+1;
		int endIndex=fistIdex+pages.getPageSize();
		map.put("fistIndex", fistIdex);
		map.put("endIndex", endIndex);
		return pages;
	}

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	private Map<String, Object> queryAndMyBatis(Map<String, Object> with) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();

		for (Map.Entry<String, Object> entry : with.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null || value.toString().trim().equals("")) {
				continue;
			}
			if (key.equals("name")) {
				map.put(key, "%" + value.toString().trim() + "%");
			}
			if (key.equals("date1") || key.equals("date2")) {
				map.put(key, df.parse(value+""));
			}
		}
		return map;
	}
}
