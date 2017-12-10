<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/back_page/head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
<script type="text/javascript">

function shangjia(name,brandId,isShow,pageNo) {
	if(Pn.checkedCount('productId')<=0) {
		alert("请至少选择一个!");
		return;
	}
	if(!confirm("确定上架吗?")) {
		return;
	}
	var a="/product/shangjia.do?&pageNo=" + pageNo + "&name=" + name + "&brandId=" + brandId + "&isShow=" + isShow;
	$("#jvForm").attr("action",a);
	$("#jvForm").submit();
}
function xiajia(name,brandId,isShow,pageNo) {
	if(Pn.checkedCount('productId')<=0) {
		alert("请至少选择一个!");
		return;
	}
	if(!confirm("确定下下架吗?")) {
		return;
	}
	var a="/product/xiajia.do?&pageNo=" + pageNo + "&name=" + name + "&brandId=" + brandId + "&isShow=" + isShow;
	$("#jvForm").attr("action",a);
	$("#jvForm").submit();
}
function deleteProduct(pid,pname,name,brandId,isShow,pageNo){
	if(confirm("您确定要删除："+pname+"这件商品吗？")){
		window.location.href="/product/delete.do?productId="+pid+"&name="+name+"&brandId="+brandId+"&isShow="+isShow+"&pageNo="+pageNo;
	}

}
function optDelete(name,brandId,isShow,pageNo) {
	if(Pn.checkedCount('productId')<=0) {
		alert("请至少选择一个!");
		return;
	}
	if(!confirm("确定批量删除吗?")) {
		return;
	}
	var a="/product/delete.do?&pageNo=" + pageNo + "&name=" + name + "&brandId=" + brandId + "&isShow=" + isShow;
	$("#jvForm").attr("action",a);
	$("#jvForm").submit();
}
function quanxuan(productId,checked){
	$("input[name='productId']").each(function(){
		$(this).attr("checked",checked);
	
	});

}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 商品管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="javascript:window.location.href='/product/addUI.do'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="/product/list.do" method="post" style="padding-top:5px;">
<input type="hidden" value="1" name="pageNo"/>
名称: <input type="text" onkeyup="changePageNo()" value="${name }" name="name"/>
	<select  name="brandId"  >
		<option value="">请选择品牌</option>
		<c:forEach items="${p1.list}" var="brand">
			<option value="${brand.id }" <c:if test="${brandId==brand.id}">selected="selected"</c:if> >${brand.name }</option>
		</c:forEach>
	</select>
	<select  name="isShow">
		<option value="">请选择</option>
		<option  value="1"  <c:if test="${isShow == 1 }">selected="selected"</c:if> >上架</option>
		<option  value="0"  <c:if test="${isShow == 0 }">selected="selected"</c:if> >下架</option>
	</select>
	<input type="submit" class="query" value="查询"/>
</form>
<form method="post" id="jvForm" >
	<!-- <input type="hidden" value="" name="pageNo"/>
	<input type="hidden" value="" name="queryName"/> -->
	<table cellspacing="1" cellpadding="0" width="100%" border="0" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="quanxuan('productId',this.checked);"/></th>
			<th>商品编号</th>
			<th>商品名称</th>
			<th>图片</th>
			<th width="4%">新品</th>
			<th width="4%">热卖</th>
			<th width="4%">推荐</th>
			<th width="4%">上下架</th>
			<th width="12%">操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
	<c:forEach items="${pagination.list}" var="product">
		<tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
			<td><input type="checkbox" name="productId" value="${product.id }"/></td>
			<td align="center">${product.no }</td>
			<td align="center">${product.name }</td>
			<td align="center"><img width="50" height="50" src="${product.img.url }"/></td>
			<td align="center"><c:if test="${product.isNew==1}">是</c:if><c:if test="${product.isNew==0}">否</c:if></td>
			<td align="center"><c:if test="${product.isHot==1}">是</c:if><c:if test="${product.isHot==0}">否</c:if></td>
			<td align="center"><c:if test="${product.isCommend==1}">是</c:if><c:if test="${product.isCommend==0}">否</c:if></td>
			<td align="center"><c:if test="${product.isShow==1}">上架</c:if><c:if test="${product.isShow==0}">下架</c:if></td>
			<td align="center">
			<a href="javascript:void(0)" class="pn-opt" onclick="window.open('/product/detail.shtml?productId=${product.id}')">查看</a> | 
			<!-- <a href="javascript:void(0)" class="pn-opt">修改</a> --> | 
			<a href="javascript:void(0)" onclick="deleteProduct('${product.id}','${product.name }','${name }','${brandId }','${isShow }','${pagination.pageNo }')" class="pn-opt">删除</a> | 
			<a href="/sku/list.do?productId=${product.id }&productNo=${product.no}" class="pn-opt">库存</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<div class="page pb15"><span class="r inb_a page_b">
	<c:forEach items="${pagination.pageView}" var="page">
		${page }
	</c:forEach>
	
</span></div>
<div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="optDelete('${name }','${brandId }','${isShow }','${pagination.pageNo }');"/>
	
	<input class="add" type="button" value="上架" onclick="shangjia('${name}','${brandId }','${isShow }','${pagination.pageNo }')"/>
	
	<input class="del-button" type="button" value="下架" onclick="xiajia('${name}','${brandId }','${isShow }','${pagination.pageNo }');"/></div>
</form>
</div>
</body>
</html>