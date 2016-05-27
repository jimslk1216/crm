<%@tag import="java.util.Date"%>
<%@ tag language="java" pageEncoding="UTF-8" %>

<!-- 定义当前标签的属性 -->
<!--  
	name: 属性名
	required: 这个属性是否为可选的. 或是否为必须的. 
	type: 属性的类型:
	rtexprvalue: 该属性是否可以接受 EL 表达是的值. 通常取值为 true. 
	rt: runtime, expr: expression, value. 
	
	注意: 属性实际上是当前 tag 文件对应的标签处理器类的成员变量. 所以可以直接在 Java 脚本中使用. 
	也可以像使用域对象那样来使用!
	
	public void setCount(java.lang.Integer count) {
	    this.count = count;
	    jspContext.setAttribute("count", count);
	  }
-->
<%@ attribute name="count" required="true" type="java.lang.Integer" rtexprvalue="true" %>

<% 
	for(int i = 0; i < count; i++){
%>
	time: <%= new Date() %>
	<br>
<%		
	}
%>

<jsp:doBody></jsp:doBody>