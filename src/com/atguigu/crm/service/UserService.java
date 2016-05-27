package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.bean.Role;
import com.atguigu.crm.bean.User;
import com.atguigu.crm.mapper.UserMapper;
import com.atguigu.crm.orm.Page;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Transactional(readOnly = true)
	public User login(String name, String password) {
		User user = userMapper.getUser(name);
		if (user != null && password.equals(user.getPassword())
				&& user.getEnabled() == 1) {

			return user;
		}
		return null;
	}

	/**
	 * 2016年5月17日 下午9:51:46 dell 设置list显示分页
	 */
	public Page<User> getPage(Map<String, Object> map2, int i) {
		Page<User> page = new Page<>();
		page.setPageNo(i);
		Map<String, Object> map = parseRequestParams2mybatis(map2);
		System.out.println(map + ":::::::::::--" + map2);
		int totalElements = userMapper.getTotalElments(map);
		page.setTotalElements(totalElements);
		int firstIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = firstIndex + page.getPageSize();

		map.put("firstIndex", firstIndex);
		map.put("endIndex", endIndex);
		List<User> list = userMapper.getPage(map);
		page.setContent(list);
		return page;
	}

	private Map<String, Object> parseRequestParams2mybatis(
			Map<String, Object> map2) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : map2.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null || value.toString().equals("")) {
				continue;
			}
			if (key.startsWith("LIKE_")) {
				key = key.substring(5, key.length());
				map.put(key, "%" + value + "%");
			}
			if (key.startsWith("EQ_")) {
				key = key.substring(3, key.length());
				map.put(key, value);
			}

		}

		return map;
	}

	public List<Role> getRole() {
		return userMapper.getRole();
	}

	public void save(User user) {

		userMapper.save(user);
	}

	public User getById(int id) {
		return userMapper.getById(id);
	}

	/**2016年5月18日
	 *下午12:00:12
	 *dell
	 *更新
	 */
	public void updete(User user) {
		userMapper.updete(user);
	}

	public void delete(int id) {
		userMapper.delete(id);
	}

}
