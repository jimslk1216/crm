package com.atguigu.crm.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crm.bean.SalesChance;
import com.atguigu.crm.bean.User;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.SalesPlanService;

@RequestMapping("/plan")
@Controller
public class SalesPlanHandler {

	@Autowired
	private SalesPlanService salesPlanService;
	
	@ResponseBody
	@RequestMapping("/saveResult-ajax")
	public int saveResult(@RequestParam("id") Long id, @RequestParam("result") String result){
		
		if(result==""){
			return 0;
		}
		Map<String,Object> params  = new HashMap<String,Object>();
		
		params.put("id", id);
		params.put("result",result);
		
		int data = salesPlanService.saveResult(params);
		return data;
	}
	
	@RequestMapping("/execution")
	public String execute(@RequestParam("id")Integer id,Map<String,Object> map){
		SalesChance chance = salesPlanService.getById(id);
		map.put("chance", chance);
		return "/plan/exec";
		
	}
	
	
	//删除某个计划
	@ResponseBody
	@RequestMapping("/delete-ajax")
	public int deletePlan(@RequestParam("id")Long id){
		int data =salesPlanService.deletePlan(id);
		return data;
	}
	
	
	//新建一个计划
	@ResponseBody
	@RequestMapping("/new-ajax")
	public String addPlan(@RequestParam("id")Long chanceId,@RequestParam("date")String dateStr,@RequestParam("todo")String todo) throws ParseException{
		
		Map<String , Object> params  = new HashMap<String, Object>();
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		params.put("todo", todo);
		params.put("date",date);
		params.put("chanceId", chanceId);
		salesPlanService.savePlan(params);
			
		
		return "redirect:/plan/list";
		
	}
	
	@ResponseBody
	//修改一个计划
	@RequestMapping("/make-ajax")
	public int updatePlan(@RequestParam("id")Long id ,@RequestParam("todo")String todo){
		
		System.out.println(id+"~~~~~~");
		Map<String, Object> params = new HashMap<String,Object>();
		
		params.put("id", id);
		params.put("todo", todo);
		int data = salesPlanService.updatePlan(params);
		
		
		return data;
	}
	
	

	//显示列表内容
	@RequestMapping(value="/list")
	public String list(@RequestParam(value="pageNo" ,required=false)String pageNoStr,Map<String,Object> map,HttpSession session){
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {
		}
		
		User user = (User) session.getAttribute("user");
		
		Long userId = user.getId();
		//获取分页对象；
		Page<SalesChance> page = salesPlanService.getPage(pageNo,userId);
		map.put("page", page);
		
		
		
		return "plan/list";
	}
	
	//制定计划
	@RequestMapping(value ="/make/{chanceId}",method=RequestMethod.GET)
	public String make(@PathVariable("chanceId")Integer id,Map<String,Object> map){
		
		System.out.println(id+"~~~~~~~~~~~~~");
		
		SalesChance chance = salesPlanService.getById(id);
		map.put("chance",chance);
	
		return "plan/make";
	}
	
	
	
	
	
	
}
