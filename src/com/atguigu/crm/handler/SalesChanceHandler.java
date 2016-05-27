package com.atguigu.crm.handler;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.bean.SalesChance;
import com.atguigu.crm.bean.User;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.SalesChanceService;

@RequestMapping("/chance")
@Controller
public class SalesChanceHandler {

	@Autowired
	private SalesChanceService salesChanceService;

	// 条件查询*乱码*
	@RequestMapping(value = "/list2")
	public String getList2(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			Map<String, Object> map, HttpServletRequest request)
			throws UnsupportedEncodingException, Exception {
		Map<String, Object> parame = WebUtils.getParametersStartingWith(
				request, "search_");
		String queryString = parseRequestParams2QueryString(parame);
		map.put("queryString", queryString);
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}

		Page<SalesChance> page = salesChanceService.getPage2(pageNo, parame);
		System.out.println(page);

		map.put("page", page);
		return "/chance/list";
	}

	private String parseRequestParams2QueryString(Map<String, Object> params) {
		StringBuilder result = new StringBuilder("");

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();

			if (val == null || val.toString().trim().equals("")) {
				continue;
			}

			result.append("search_").append(key).append("=").append(val)
					.append("&");
		}

		if (result.length() > 1) {
			result = result.replace(result.length() - 1, result.length(), "");
		}
		return result.toString();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(RedirectAttributes attribtues, SalesChance chance) {

		salesChanceService.update(chance);
		attribtues.addFlashAttribute("message", "更新成功！");
		return "redirect:/chance/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getById(@PathVariable("id") String idStr,
			Map<String, Object> map) {
		SalesChance chance = salesChanceService.getById(idStr);
		map.put("chance", chance);
		return "chance/input";
	}

	// 保存
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String save(SalesChance chance) {
		salesChanceService.save(chance);
		return "redirect:/chance/list";
	}

	// 新建
	@RequestMapping("/")
	public String addUI(SalesChance chance, Map<String, Object> map) {
		map.put("chance", chance);
		return "chance/input";
	}

	// 删除
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") String idStr) {
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		salesChanceService.delete(id);
		return "redirect:/chance/list";
	}

	// 显示列表
	@RequestMapping(value = "/list")
	public String getList(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			Map<String, Object> map) {
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		Page<SalesChance> page = salesChanceService.getPage(pageNo);
		map.put("page", page);
		return "/chance/list";
	}
}
