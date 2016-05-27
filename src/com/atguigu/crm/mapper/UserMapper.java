package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.atguigu.crm.bean.Role;
import com.atguigu.crm.bean.User;

public interface UserMapper {

/*	@Select("SELECT id , enabled ,name , password , role_id ,salt "
			+ "FROM users u "
			+ "WHERE name=#{name} ")*/
	/*@Select("SELECT u.id , u.enabled ,u.name , password  ,salt , r.name as \"role.name\" "
			+ "FROM users u "
			+ "LEFT OUTER JOIN roles r "
			+ "ON u.role_id = r.id "
			+ "WHERE u.name=#{name} ")*/
	public User getUser(@Param("name") String name);

	public int getTotalElments(Map<String, Object> map);

	public List<User> getPage(Map<String, Object> map);

	public List<Role> getRole();

	public void save(@Param("u")User user);

	public User getById(int id);

	public void updete(@Param("u")User user);

	public void delete(int id);

	
}
