<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/back_page/head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
</head>
<script type="text/javascript">
	function checkBox(name,checked){
		/* $("input[type=checkbox][name="+name+"]").each(function(){
			$(this).attr("checked",checked);			
		}) */
		$("input[name="+name+"]").attr("checked",checked);
	}
	function optDelete(name,isDisplay){
		var s=$("input[name='ids']:checked").size();
		if(s<=0){
			alert("请至少选择一个进行删除");
			return;
		}
		if(!confirm("您确定要删除吗？")){
			return; 
		}
		$("#form2").attr("action","/brand/deletes.do?name="+name+"&isDisplay="+isDisplay).submit();
	
	}

</script>
<body>

<div class="box-positon">
	<div class="rpos">当前位置: 品牌管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="javascript:window.location.href='/brand/addUI.do'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="/brand/list.do" method="post" style="padding-top:5px;">
品牌名称: <input type="text" name="name" value="${name }"/>
	<select name="isDisplay">
		<option value=""> 请选择</option>
		<option value="1" <c:if test="${isDisplay == 1 }">selected="selected"</c:if> >是</option>
		<option value="0" <c:if test="${isDisplay == 0 }">selected="selected"</c:if>>不是</option>
	</select>
	<input type="submit" class="query" value="查询"/>
</form>
<form  id="form2" method="post" >
<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="50"><input type="checkbox" onclick="checkBox('ids',this.checked)"/>全选</th>
			<th>品牌ID</th>
			<th>品牌名称</th>
			<th>品牌图片</th>
			<th>品牌描述</th>
			<th>排序</th>
			<th>是否可用</th>
			<th>操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
		<c:forEach items="${pagination.list }" var="entry">
			<tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
				<td><input type="checkbox" value="${entry.id }" name="ids" /></td>
				<td align="center">${entry.id }</td>
				<td align="center">${entry.name }</td>
				<td align="center"><img width="40" height="40" src="${entry.imgUrl}"/></td>
				<td align="center">${entry.description }</td>
				<td align="center">${entry.sort }</td>
				<td align="center"><c:if test="${entry.isDisplay == 1 }">是</c:if><c:if test="${entry.isDisplay == 0 }">不是</c:if></td>
				<td align="center">
				<a class="pn-opt" href="/brand/editUI.do?id=${entry.id }">修改</a> | <a class="pn-opt"  href="/brand/delete.do?id=${entry.id }&name=${name }&isDisplay=${isDisplay }" onclick="return confirm('您确定要删除吗?');">删除</a>
				</td>
			</tr>
		</c:forEach>
	
	</tbody>
</table>
</form>
<div class="page pb15">
	<span class="r inb_a page_b">
		<c:forEach items="${pagination.pageView }" var="page">
			${page }
		</c:forEach>
	</span>
</div>
<div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="optDelete('${name}','${isDisplay }');"/></div>
</div>
</body>

</html>