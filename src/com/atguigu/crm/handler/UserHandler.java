package com.atguigu.crm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.bean.Authority;
import com.atguigu.crm.bean.Navigation;
import com.atguigu.crm.bean.Role;
import com.atguigu.crm.bean.User;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.UserService;

@Controller
@RequestMapping("/user")
public class UserHandler {

	@Autowired
	private UserService userService;
	@Autowired
	private ResourceBundleMessageSource messageSource;

	/**
	 * 2016年5月22日 下午7:02:56 dell 动态生成json链接
	 */
	@ResponseBody
	@RequestMapping(value = "/navigate")
	public List<Navigation> navigation(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		List<Navigation> list = new ArrayList<>();
		//根菜单
		Navigation navigat = new Navigation();
		navigat.setId(Long.MAX_VALUE);
		navigat.setText("客户关系管理系统");
		// 获取登陆当前登陆用户
		list.add(navigat);
		
		User user = (User) SecurityUtils.getSubject().getPrincipals()
				.getPrimaryPrincipal();
		Map<Long, Navigation> map = new HashMap<>();
		
		for (Authority autho : user.getRole().getAuthorities()) {
			Navigation na = new Navigation();
			na.setId(autho.getId());
			na.setText(autho.getDisplayName());
			na.setUrl(contextPath + autho.getUrl());
			System.out.println("URL::"+na.getUrl());
			Authority parentAuthority = autho.getParentAuthority();
			Navigation navigation = map.get(parentAuthority.getId());
			// 把子权限加入到对应的父权限中
			if (navigation == null) {
				navigation = new Navigation();
				navigation.setId(parentAuthority.getId());
				navigation.setText(parentAuthority.getDisplayName());
				navigation.setState("closed");
				//把父权限加入到对应的子权限里
				navigat.getChildren().add(navigation);
				map.put(navigation.getId(), navigation);
				
			}
			
			navigation.getChildren().add(na);
		}
		
		
		return list;
	}

	@RequestMapping(value = "/shiro-login", method = RequestMethod.POST)
	public String login(@RequestParam("userName") String userName,
			@RequestParam("password") String password, HttpSession session,
			RedirectAttributes redirectAttributes, Locale locale) {

		Subject subject = SecurityUtils.getSubject();
		// 验证是否以及登陆
		if (!subject.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(userName,
					password);
			token.setRememberMe(true);
			try {
				// 认证操作
				subject.login(token);

			} catch (AuthenticationException ae) {

				String message = messageSource.getMessage("error.user.login",
						null, locale);

				redirectAttributes.addFlashAttribute("message", message);
				redirectAttributes.addFlashAttribute("username", userName);
				return "redirect:/index";
			}
		}
		User user = (User) SecurityUtils.getSubject().getPrincipals()
				.getPrimaryPrincipal();
		session.setAttribute("user", user);
		return "redirect:/success";
	}

	/**
	 * 2016年5月17日 下午9:21:13 dell 显示带查询条件的用户分页
	 */
	@RequestMapping(value = "/list")
	public String list(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			Map<String, Object> map, HttpServletRequest hsr) {
		Map<String, Object> map2 = WebUtils.getParametersStartingWith(hsr,
				"search_");
		int i = 1;
		try {
			i = Integer.parseInt(pageNoStr);
		} catch (Exception e) {
		}
		Page<User> page = userService.getPage(map2, i);
		String str = parseRequestParams2QueryString(map2);
		map.put("queryString", str);
		map.put("page", page);
		return "user/list";
	}

	/**
	 * 2016年5月17日 下午9:47:42 dell
	 */
	private String parseRequestParams2QueryString(Map<String, Object> map2) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : map2.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null || value.toString().equals("")) {
				continue;
			}
			sb.append("search_").append(key).append("=").append(value)
					.append("&");
		}
		if (sb.length() > 1) {
			sb.replace(sb.length() - 1, sb.length(), "");
		}

		return sb.toString();
	}

	/**
	 * 2016年5月18日 上午10:04:10 dell 进入新建页面
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String add(Map<String, Object> map, String a) {
		List<Role> roles = userService.getRole();
		Map<String, Object> m = new HashMap();
		m.put("0", "无效");
		m.put("1", "有效");

		map.put("allStatus", m);
		map.put("user", new User());
		map.put("roles", roles);
		return "user/input";
	}

	/**
	 * 2016年5月18日 上午11:24:20 dell 添加用户
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String save(User user, RedirectAttributes rab) {

		userService.save(user);
		rab.addFlashAttribute("message", "添加成功");
		return "redirect:/user/list";
	}

	/**
	 * 2016年5月18日 上午11:57:29 dell 去更新页面
	 */
	@RequestMapping(value = "/create/{id}")
	public String toUpdate(Map<String, Object> map, @PathVariable("id") int id) {
		System.out.println(id + "::::::::::::::::::::::");
		User u = userService.getById(id);
		List<Role> roles = userService.getRole();
		Map<String, Object> m = new HashMap();
		m.put("0", "无效");
		m.put("1", "有效");

		map.put("user", u);
		map.put("allStatus", m);
		map.put("roles", roles);

		return "user/input";
	}

	/**
	 * 2016年5月18日 上午11:57:39 dell 更新
	 */
	@RequestMapping(value = "/create", method = RequestMethod.PUT)
	public String update(User user, RedirectAttributes ra) {
		userService.updete(user);
		ra.addFlashAttribute("message", "更新成功");
		return "redirect:/user/list";
	}

	/**
	 * 2016年5月21日 下午10:58:27 dell 删除
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") int id, RedirectAttributes ra) {
		userService.delete(id);
		ra.addFlashAttribute("message", "删除成功");
		return "redirect:/user/list";

	}

	@RequestMapping(value = "/unauthorized")
	public String unauthorized() {
		return "/unauthorized";
	}
}
