package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.bean.SalesChance;
import com.atguigu.crm.bean.User;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.SalesChanceService;
import com.atguigu.crm.service.SalesPlanService;

@RequestMapping(value = "/plan")
@Controller
public class DevelopPlanHandler {

	@Autowired
	private SalesChanceService salesChanceService;

	// 客户开发失败
	@RequestMapping(value = "/stop/{id}", method = RequestMethod.PUT)
	public String stop(@PathVariable("id") Integer id,
			RedirectAttributes redirectAttributes) {
		salesChanceService.stop(id);
		redirectAttributes.addFlashAttribute("message", "开发失败");
		return "redirect:/plan/chance/list";
	}

	// 开发成功
	@RequestMapping(value = "/finish/{id}", method = RequestMethod.PUT)
	public String finish(@PathVariable("id") int id,
			RedirectAttributes redirectAttributes) {
		salesChanceService.finish(id);
		redirectAttributes.addFlashAttribute("message", "开发成功");
		return "redirect:/plan/chance/list";
	}

	// 显示列表
	@RequestMapping(value = "/chance/list")
	public String chance(Map<String, Object> map, HttpSession hs,
			@RequestParam(value = "pageNo", required = false) String pageNoStr) {
		// 获取当前登陆的用户
		User u = (User) hs.getAttribute("user");
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		Page<SalesChance> page = salesChanceService.getPage3(pageNo, u.getId());
		map.put("page", page);
		System.out.println(page);
		return "plan/chance/list";
	}

	// 查看详细
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") String idStr,
			Map<String, Object> map) {
		SalesChance salesChance = salesChanceService.getById(idStr);
		map.put("chance", salesChance);
		return "plan/detail";
	}

}
