package com.atguigu.crm.handler;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.bean.CustomerDrain;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomersDrainService;

@RequestMapping(value = "/drain")
@Controller
public class CustomersDrainHandler {
	@Autowired
	private CustomersDrainService cds;

	/**
	 * 2016年5月18日 下午7:46:00 dell 显示分页
	 */
	@RequestMapping(value = "/list")
	public String list(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			HttpServletRequest hsr, Map<String, Object> map) {
		Map<String, Object> parame = WebUtils.getParametersStartingWith(hsr,
				"search_");
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception s) {
		}
		Page<CustomerDrain> page = cds.getPage(parame, pageNo);
		// 拼接查询字符串
		String parames = query2parame(parame);
		map.put("queryString", parames);
		map.put("page", page);
		return "drain/list";
	}

	/**
	 * 2016年5月18日 下午8:15:27 dell 转换查询字符串工具
	 */
	private String query2parame(Map<String, Object> parame) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : parame.entrySet()) {
			Object value = entry.getValue();
			String key = entry.getKey();
			if (value == null || value.toString().trim().equals("")) {
				continue;
			}
			sb.append("search_").append(key).append("=").append(value)
					.append("&");
		}
		if (sb.length() > 1) {
			sb.replace(sb.length() - 1, sb.length() - 1, "");
		}
		return sb.toString();
	}

	/**
	 * 2016年5月19日 上午10:30:37 dell 暂缓流失
	 */
	@RequestMapping(value = "/delay/{id}", method = RequestMethod.GET)
	public String delay(@PathVariable("id") int id, Map<String, Object> map) {

		CustomerDrain cd = cds.getById(id);
		map.put("drain", cd);
		return "drain/delay";
	}

	/**
	 * 2016年5月19日 下午5:09:18 dell 添加暂缓流失措施
	 */
	@ResponseBody
	@RequestMapping(value = "/delay", method = RequestMethod.POST)
	public int add(@RequestParam("id") int id,
			@RequestParam("textarea") String texr) {
		int i = cds.delay(id, texr);
		return i;
	}

	/**
	 * 2016年5月19日 下午5:17:21 dell 进入确认流失页面
	 */

	@RequestMapping(value = "/affirm/{id}", method = RequestMethod.GET)
	public String affirm(@PathVariable("id") int id, Map<String, Object> map) {
		CustomerDrain byId = cds.getById(id);
		map.put("drain", byId);
		return "drain/confirm";
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.PUT)
	public String confirm( CustomerDrain cd) {
		cd.setDrainDate(new Date());
		cds.confirm(cd);
		return "redirect:/drain/list";
	}
}
