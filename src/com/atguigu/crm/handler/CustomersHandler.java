package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.bean.Customer;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomersService;

@RequestMapping(value = "/customer")
@Controller
public class CustomersHandler {
	@Autowired
	private CustomersService cs;

	/**
	 * 2016年5月16日 下午 9:19:13 dell
	 */
	// 显示基本客户信息列表
	@RequestMapping(value = "/list")
	public String list(Map<String, Object> map,
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			HttpServletRequest hsr) {
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {
		}
		//获取传入的参数
		Map<String, Object> parametersStartingWith = WebUtils
				.getParametersStartingWith(hsr, "search_");
		String path = parseRequestParams2QueryString(parametersStartingWith);
		// 获取分页
		Page<Customer> page = cs.getPage(pageNo,parametersStartingWith);
		// 获取地区下拉数据
		
		
		map.put("queryString", path);
		map.put("page", page);
		map.put("regions", cs.getDicts("地区"));
		map.put("levels", cs.getDicts("客户等级"));
		return "customer/list";
	}

	private String parseRequestParams2QueryString(
			Map<String, Object> parametersStartingWith) {
		StringBuffer str = new StringBuffer();
		for (Map.Entry<String, Object> entry : parametersStartingWith
				.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null || value.toString().equals("")) {
				continue;
			}
			str.append("search_").append(key).append("=").append(value)
					.append("&");
		}
		if (str.length() > 1) {
			str.replace(str.length() - 1, str.length(), "");
		}
		return str.toString();
	}
}
