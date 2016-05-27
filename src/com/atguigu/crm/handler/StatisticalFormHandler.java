package com.atguigu.crm.handler;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.bean.Customer;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.StatisticalFormService;

/**
 * @author dell 统计报表
 */
@RequestMapping(value = "/report")
@Controller
public class StatisticalFormHandler {

	private StatisticalFormService sfs;

	@RequestMapping(value = "/pay")
	public String pay(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			HttpServletRequest hsr, Map<String, Object> map) throws ParseException {
		Map<String, Object> with = WebUtils.getParametersStartingWith(hsr,
				"search_");

		int page = 1;
		try {
			page = Integer.parseInt(pageNoStr);
		} catch (Exception ex) {
		}

		Page<Customer> pages = sfs.getPage(page, with);
		// 拼接字符串用以下次查询使用
		String query = queryANDmatter(map);
		map.put("queryString", query);
		map.put("page", page);
		return "report/pay";
	}

	/**
	 * 2016年5月26日 下午11:10:51 dell 返回拼接字符串
	 */
	private String queryANDmatter(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, Object> entry : map.entrySet()) {

			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null || value.toString().trim().equals("")) {
				continue;
			}
			sb.append("search_").append(key).append("=").append(value)
					.append("&");
		}
		if (sb.length() > 1) {
			sb.replace(sb.length() - 1, sb.length(), "");
		}
		return sb.toString();
	}
}
