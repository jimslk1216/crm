<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
body {
	background-color: rgb(240, 240, 250);
}
</style>
<link rel="stylesheet" type="text/css"
	href="${ctp}/static/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctp}/static/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${ctp}/static/themes/demo.css">
<script type="text/javascript" src="${ctp}/static/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(function() {

		$('#tt')
				.tree(
						{
							onClick : function(node) {
								if (node.url) {
									//alert(node.attributes.url);
									window.parent.document
											.getElementById("content").src = node.url;
								}
							}
						});

	});
</script>

</head>
<body>
	<ul id="tt" class="easyui-tree"
		data-options="url:'${ctp }/user/navigate',method:'get',animate:true"></ul>

</body>
</html>