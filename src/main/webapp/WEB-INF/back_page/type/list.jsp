<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/back_page/head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 类型管理 - 列表</div>
	<div class="clear"></div>
</div>
<div class="body-box">

<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th>类型ID</th>
			<th>类型名称</th>
			<th>是否可用</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
		<c:forEach items="${types }" var="type">
			<tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
				<td align="center">${type.id }</td>
				<td align="center">${type.name }</td>
				<td align="center"><c:if test="${type.isDisplay == 1 }">是</c:if><c:if test="${type.isDisplay == 0 }">不是</c:if></td>
				<td align="center">
				</td>
			</tr>
		</c:forEach>
	
	</tbody>
</table>
</div>
</body>
</html>