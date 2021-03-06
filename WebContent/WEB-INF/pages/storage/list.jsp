<%@ page language="java" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ include file="/commons/common.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>库存查询</title>
<script type="text/javascript">
	$(function() {
		$("img[id^='delete_']").click(function(){
			var $from =$("form");		
			$from.attr("action",$(this).attr("onclick")).submit();
		});
	});
</script>
</head>
<body>
	<div class="page_title">库存管理</div>
	<div class="button_bar">
		<button class="common_button"
			onclick="window.location.href='${ctp}/storage/create'">库存添加
		</button>
		<button class="common_button" onclick="document.forms[0].submit();">
			查询</button>
	</div>

	<form action="${ctp}/storage/list" method="POST">
		<input type="hidden" name="_method" value="DELETE">
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>产品</th>
				<td><input type="text" name="search_LIKE_product.name" /></td>
				<th>仓库</th>
				<td><input type="text" name="search_LIKE_wareHouse" /></td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />

		<c:if test="${page != null && page.totalElements > 0 }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>编号</th>
					<th>产品</th>
					<th>仓库</th>
					<th>货位</th>
					<th>件数</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
				<c:forEach var="storage" items="${page.content }">
					<tr>
						<td class="list_data_number">${storage.id}</td>
						<td class="list_data_ltext">${storage.product.name}</td>
						<td class="list_data_ltext">${storage.wareHouse}</td>
						<td class="list_data_text">${storage.stockWare}</td>

						<td class="list_data_number">${storage.stockCount}</td>
						<td class="list_data_ltext">${storage.memo}</td>
						<td class="list_data_op">
						
						<img
							onclick="window.location.href='${ctp}/storage/create/${storage.id }'"
							title="修改" src="${ctp}/static/images/bt_edit.gif"
							class="op_button" /> 
							
							<img id="delete_${storage.id }"
							onclick="${ctp}/storage/delete/${storage.id }"
							title="删除" src="${ctp}/static/images/bt_del.gif"
							class="op_button" />
						
						</td>
					</tr>
				</c:forEach>
			</table>
			<tags:page page="${page}" list="storage/list"
				queryString="${queryString}" />
		</c:if>
		<c:if test="${page == null || page.totalElements == 0 }">
			没有任何数据
		</c:if>

	</form>
</body>
</html>