<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="${ctp }/static/css/styles.css">
<script type="text/javascript">
	$(function() {
		//登出的 JS 代码
		
		$("#logout").click(function(){
			var flag = confirm("确定要退出？")
			if (!flag) {
				return false;
			}
			window.parent.location=this.href;
		});
		
		
	})
</script>
</head>
<body style="border-bottom: solid 1px #666;">
	<TABLE style="width: 100%;">
		<TR>
			<td><img src="${ctp}/static/images/logo.jpg"></td>
			<td style="font-family: 黑体; font-size: 33px; font-weight: bold;">
				客户关系管理系统</td>
			<c:if test="${user != null }">
				<td width="25%" align="right" style="font-size: 12px;"
					valign="bottom"><b>当前登录用户:</b> ${user.name }(${user.role.name })
					[<a href="${ctp}/shiro-logout" id="logout">注销</a>]&nbsp;&nbsp;<br /></td>
			</c:if>
			<c:if test="${user == null }">
				<td width="25%" align="right" style="font-size: 12px;"
					valign="bottom"><b><a href="${ctp}/login" id="login">请登录</a></b> &nbsp;&nbsp;<br/></td>
			</c:if>
		</tr>
	</table>
</body>
</html>

