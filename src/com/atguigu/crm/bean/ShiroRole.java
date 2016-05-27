package com.atguigu.crm.bean;

import org.apache.shiro.authz.annotation.RequiresRoles;

public class ShiroRole {
	
	@RequiresRoles({"user"})
	public  void user() {
		System.out.println("user");
	}
	@RequiresRoles({"admin"})
	public  void admin() {
		System.out.println("admin");
	}

	
}
