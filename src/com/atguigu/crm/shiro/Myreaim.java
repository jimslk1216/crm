package com.atguigu.crm.shiro;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atguigu.crm.bean.Authority;
import com.atguigu.crm.bean.User;
import com.atguigu.crm.mapper.UserMapper;

@Component
public class Myreaim extends AuthorizingRealm {

	@Autowired
	private UserMapper um;

	@Override
	// 进行授权
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		principals.getPrimaryPrincipal();
		Object principal = principals.getPrimaryPrincipal();
		User user = (User) principal;
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> set = new HashSet<>();
		
		for (Authority authority : user.getRole().getAuthorities()) {
			set.add(authority.getName());
		}
		info.addRoles(set);
		return info;
	
	
	}

	@Override
	// 进行认证
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// 1. 强制类型转换. 把 AuthenticationToken 转为 UsernamePasswordToken
		// AuthenticationToken 即为在 Handler 中调用 Subject 的
		// login(UsernamePasswordToken)
		// 传入的参数
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		// 获取前端输入的username
		String username = upt.getUsername();
		User user = um.getUser(username);
		// 把username对应的记录信息封装为AuthenticationInfo的SimpleAuthenticationInfo
		Object principal = user;
		// 登陆凭证
		String realmName = getName();

		SimpleAuthenticationInfo info = null;
		// info= new SimpleAuthenticationInfo(principal,
		// credentials, realmName);
		// 设置盐值
		ByteSource bytes = ByteSource.Util.bytes(user.getSalt());
		info = new SimpleAuthenticationInfo(principal, user.getPassword(),
				bytes, realmName);
		return info;
	}

	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("ceadfd47cdaa814c");
		;
		int hashIterations = 1024;

		Object result = new SimpleHash(hashAlgorithmName, credentials, salt,
				hashIterations);
		System.out.println(result);
	}

	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("MD5");
		matcher.setHashIterations(1024);

		setCredentialsMatcher(matcher);

	}

}
