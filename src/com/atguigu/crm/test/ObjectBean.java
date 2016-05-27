package com.atguigu.crm.test;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ObjectBean {

	public void doIt(){
		System.out.println("::"+new Date());
	}
	
}
