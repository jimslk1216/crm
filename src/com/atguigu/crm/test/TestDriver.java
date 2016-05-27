package com.atguigu.crm.test;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.crm.bean.SalesChance;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.SalesPlanService;

public class TestDriver {

	private ApplicationContext ioc = null;
	SalesPlanService salesPlanService=null;
	{
		
		ioc = new ClassPathXmlApplicationContext("appliactionContext.xml");
		 salesPlanService = ioc.getBean(SalesPlanService.class);
	}

	@Test
	public void testJdbc() throws SQLException {
		DataSource dataSource = ioc.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
	}
	
	@Test
	public void testGetPage(){
		Page<SalesChance> page = salesPlanService.getPage(2, 21l);
		List<SalesChance> content = page.getContent();
		System.out.println(content);
	}
	
	@Test
	public void testGetById(){
		SalesChance salesChance = salesPlanService.getById(150);
		System.out.println(salesChance);
		String contact = salesChance.getContact();
		System.out.println(contact);
		System.out.println(salesChance.getDescription());
		System.out.println(salesChance.getDesignee().getName());
	}
	
	@Test
	public void testSaveResult(){
		Map<String,Object> params = new HashMap<>();
		params.put("id", 2374l);
		params.put("result", "bbb");
		salesPlanService.saveResult(params);
	}
	

}
