package com.atguigu.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.bean.Product;
import com.atguigu.crm.bean.Storage;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.StorageService;

/**
 * @author dell 库存管理
 */
@RequestMapping(value = "/storage")
@Controller
public class StorageHandler {
	@Autowired
	private StorageService ss;

	/**
	 * 2016年5月17日 下午9:11:04 dell 删除操作
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable int id, RedirectAttributes ra) {
		ss.delete(id);
		ra.addFlashAttribute("message", "删除成功");
		return "redirect:/storage/list";
	}

	/**
	 * 2016年5月17日 下午7:22:57 dell 执行更新操作
	 */
	@RequestMapping(value = "/create", method = RequestMethod.PUT)
	public String update(@RequestParam("incrementCount") int count,
			Storage storage, RedirectAttributes ras) {
		storage.setStockCount(storage.getStockCount() + count);
		ss.update(storage);
		ras.addFlashAttribute("message", "更新成功");
		return "redirect:/storage/list";
	}

	// 进入修改页面
	/**
	 * 2016年5月17日 下午6:47:46 dell 进入修改页面并查询
	 */
	@RequestMapping(value = "/create/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable("id") int id, Map<String, Object> map) {
		Storage storage = ss.getById(id);
		map.put("storage", storage);
		return "storage/input";
	}

	/**
	 * 2016年5月17日 下午6:16:26 dell 执行添加保存操作
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String save(Storage storage, RedirectAttributes ras) {
		ss.save(storage);
		ras.addFlashAttribute("message", "添加成功");
		return "redirect:/storage/list";
	}

	/**
	 * 2016年5月17日 下午4:03:06 dell 添加库存页面
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String add(Map<String, Object> map) {
		List<Product> pro = ss.getProducts();
		map.put("products", pro);
		map.put("storage", new Storage());
		return "storage/input";
	}

	/**
	 * 2016年5月17日 上午9:29:50 dell 带查询条件的库存分页
	 */
	@RequestMapping(value = "/list")
	public String list(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			Map<String, Object> map, HttpServletRequest hsr) {
		Map<String, Object> startingWith = WebUtils.getParametersStartingWith(
				hsr, "search_");
		String queryString = parseRequestParams2QueryString(startingWith);
		// 放大域中
		map.put("queryString", queryString);
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException n) {
		}
		Page<Storage> page = ss.getPage(pageNo, startingWith);
		map.put("page", page);
		return "storage/list";
	}

	/**
	 * 2016年5月17日 上午9:44:21 dell 拼接返回时的字符串
	 */
	private String parseRequestParams2QueryString(
			Map<String, Object> startingWith) {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, Object> entry : startingWith.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null || value.toString().trim().equals("")) {
				continue;
			}
			sb.append("search_").append(key).append("=").append(value)
					.append("&");
		}
		if (sb.length() > 1) {
			sb = sb.replace(sb.length() - 1, sb.length(), "");
		}
		return sb.toString();
	}
}
