<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
 
 <%@ taglib tagdir="/WEB-INF/tags" prefix="atguigu" %>  

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>销售机会管理</title>
<script type="text/javascript">
	$(function() {
<%-- 删除操作 --%>
	$("img[id^='delete_']").click(function() {
			var label = $(this).prev(":hidden").val();
			var flag = confirm("确定要删除" + label + "的信息吗？");
			if (flag) {
				var id = $(this).next(":hidden").val();
				$("#hiddenForm").attr("action", "${ctp}/chance/" + id);
				$("#_method").val("DELETE");
				$("#hiddenForm").submit();
			}

		});
<%-- 新建操作 --%>
	$("#new").click(function() {
			window.location.href = "${ctp}/chance/";
			return false;
		});

	})
</script>
</head>

<body class="main">

	<form action="" id="hiddenForm" method="POST">
		<input type="hidden" name="_method" id="_method" />
	</form>


	<form id="command" action="${ctp}/chance/list2" method="post">
		<div class="page_title">销售机会管理</div>
		<div class="button_bar">
			<button class="common_button" id="new">
				新建
			</button>
			<button class="common_button" onclick="document.forms[1].submit();">
				查询
			</button>
		</div>
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title">客户名称</th>
				<td class="input_content"><input type="text"
					name="search_LIKE_custName" /></td>
				<th class="input_title">概要</th>
				<td class="input_content"><input type="text"
					name="search_LIKE_title" /></td>
				<th class="input_title">联系人</th>
				<td class="input_content"><input type="text"
					name="search_LIKE_contact" /></td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />

		<c:if test="${empty page.content }">
			没有数据
		</c:if>

		<c:if test="${not empty page.content }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>编号</th>
					<th>客户名称</th>
					<th>概要</th>
					<th>联系人</th>
					<th>联系人电话</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${page.content }" var="item">
					<tr>
						<td class="list_data_number">${item.id }</td>
						<td class="list_data_text">${item.custName }</td>
						<td class="list_data_text">${item.title }</td>
						<td class="list_data_text">${item.contact }</td>
						<td class="list_data_text">${item.contactTel }</td>
						<td class="list_data_text"><fmt:formatDate
								value="${item.createDate }" pattern="yyyy-MM-dd" /></td>
						<td class="list_data_op"><img
							onclick="window.location.href='${ctp}/chance/dispatch?id=2200'"
							title="指派" src="${ctp}/static/images/bt_linkman.gif"
							class="op_button" /> <img
							onclick="window.location.href='${ctp}/chance/${item.id }'"
							title="编辑" src="${ctp}/static/images/bt_edit.gif"
							class="op_button" /> <input type="hidden"
							value="${item.custName }" /> <img id="delete_${item.id} "
							title="删除" src="${ctp}/static/images/bt_del.gif"
							class="op_button" /> <input type="hidden" value="${item.id }" />
						</td>
					</tr>
				</c:forEach>
			</table>

			<atguigu:page page="${page }" queryString="${queryString }"
				list="chance/list"></atguigu:page>
		</c:if>
	</form>
</body>
</html>
