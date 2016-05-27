package com.atguigu.crm.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.bean.Customer;
import com.atguigu.crm.bean.CustomerActivity;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerActivitieService;

@RequestMapping(value = "/activity")
@Controller
public class CustomerActivitieHandler {

	@Autowired
	private CustomerActivitieService acs;

	/**
	 * 2016年5月17日 上午12:33:12 dell 交往记录显示
	 */
	@RequestMapping(value = "/list/{id}")
	public String activity(@PathVariable("id") int id,
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			Map<String, Object> map) {
		// 获取客户信息
		Customer cus = acs.getCustomer(id);
		int pageNo = 1;
		// 获取交往记录
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {
		}
		Page<CustomerActivity> actitvity = acs.getPage(id, pageNo);
		map.put("customer", cus);
		map.put("page", actitvity);
		return "activity/list";
	}

	/**
	 * 2016年5月20日 下午10:21:43 dell 进入添加页面
	 */
	@RequestMapping(value = "/create/{id}", method = RequestMethod.GET)
	public String create(@PathVariable("id") int id, Map<String, Object> map) {
		map.put("id", id);
		map.put("activity", new CustomerActivity());
		return "activity/input";
	}

	/**
	 * 2016年5月20日 下午10:31:15 dell 保存交往记录
	 */
	@RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
	public String save(@PathVariable("id") Long cusId, CustomerActivity ca) {
		ca.getCustomer().setId(cusId);
		System.out.println("id:" + cusId);
		System.out.println(ca);
		acs.save(ca);
		return "redirect:/activity/list/" + cusId;
	}

	/**
	 * 2016年5月21日 上午12:16:17 dell 进入更新操作页面
	 */
	@RequestMapping(value = "create/toupdate/{id}")
	public String toUpdate(@PathVariable("id") int id, Map<String, Object> map) {
		CustomerActivity ca = acs.getById(id);
		map.put("activity", ca);
		map.put("id", ca.getCustomer().getId());
		return "activity/input";
	}

	/**
	 * 2016年5月21日 上午12:18:04 dell 进入更新操作页面
	 */
	@RequestMapping(value = "/create/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") Long cusId, CustomerActivity ca) {

		acs.update(ca);
		return "redirect:/activity/list/" + cusId;
	}

	@RequestMapping(value = "/delete/{caId}/{cuId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("caId") int id, RedirectAttributes ra,
			@PathVariable("cuId") int cuId) {
		acs.delete(id);
		ra.addFlashAttribute("message", "删除成功");
		return "redirect:/activity/list/" + cuId;
	}
}
