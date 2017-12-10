<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/back_page/head.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>成功提交订单</title>
<link rel="stylesheet" href="/res/css/style.css" />
<script src="/res/js/jquery.js"></script>
<script src="/res/js/com.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
	$("#d1").offset({"top":35,"left":160});

});
function login(){
	window.location.href = "/shopping/toLogin.shtml?returnUrl="+window.location.href;
}
</script>
<body>
<div class="bar"><div class="bar_w">
	<p class="l">
		<span class="l">
			收藏本网站！北京<a href="#" title="更换">[更换]</a>
		</span>
	</p>
	<ul class="r uls">
		<c:if test="${empty buyer_session}">
			<li class="dev" >您好,请先登录哦！</li>
			<li class="dev"><a href="javascript:void(0)" onclick="login()"  title="登陆">[登陆]</a></li>
			<li class="dev"><a href="javascript:void(0)" onclick="window.location.href='/product/toRegister.shtml'" title="免费注册">[免费注册]</a></li>
			<li class="dev"><a href="javascript:void(0)" onclick="window.location.href='/product/toCart.shtml'" title="退出">[购物车]</a></li>
			<li class="dev"><a href="javascript:void(0)" onclick="window.location.href ='/buyer/index.shtml'" title="我的订单">我的订单</a></li>
		</c:if>
		<c:if test="${!empty buyer_session}">
			<li class="dev">您好,欢迎 <font color="red">${buyer_session.realName }</font>来到新巴巴运动网！</li>
			<li class="dev"><a href="javascript:void(0)" onclick="window.location.href ='/buyer/toLogout.shtml'" title="退出">[退出]</a></li>
			<li class="dev"><a href="javascript:void(0)" onclick="window.location.href='/product/toCart.shtml'" title="退出">[购物车]</a></li>
			<li class="dev"><a href="javascript:void(0)" onclick="window.location.href ='/buyer/index.shtml'" title="我的订单">我的订单</a></li>
		</c:if>
		
		<li class="dev"><a href="#" title="在线客服">在线客服</a></li>
		<li class="dev after"><a href="#" title="English">English</a></li>
	</ul>
</div></div>

<div class="h-logo" id="d1"><a href="http://localhost:8080">&nbsp;&nbsp;&nbsp;&nbsp;<img src="/res/img/pic/logo-1.png" /></a></div>
<ul class="ul step st3_3">
<li title="1.我的购物车">1.我的购物车</li>
<li title="2.填写核对订单信息">2.填写核对订单信息</li>
<li title="3.成功提交订单" class="here">3.成功提交订单</li>
</ul>

<div class="w ofc case">

	<div class="confirm">
		<div class="tl"></div><div class="tr"></div>
		<div class="ofc">
			
			<p class="okMsg">您的订单已成功提交，完成支付后，我们将立即发货！</p>
			
			
			
			
		<%-- <center >	
		<c:if test="${order.isPaiy==1 }">
				<h1><a class="blue" href="javascript:void(0)" onclick="window.location.href ='/buyer/toPay.shtml?orderId=${order.oid}'" ><font color="red">  点此去付款</font> </a>  </h1>
			</c:if>
			</center> --%>
			
			<table cellspacing="0" class="tab tab5" summary="">
			<tbody><tr>
			<th>您的订单号</th>
			<td><var class="blue"><b>${order.oid }</b></var></td>
			<th>应付现金</th>
			<td><var class="red"><b>￥${order.totalPrice }</b></var>&nbsp;元</td>
			<th>支付方式</th>
			<td>${order.paymentWayName }</td>
			</tr>
			<tr>
			<th>配送方式</th>
			<td>快递</td>
			<th>预计到货时间</th>
			<td>${fn:substring(order.createDate,0,10)}</td>
			<th></th>
			<td></td>
			</tr>
			</table><br/><br/><br/><br/>
			
			
	<form action="/buyer/pay.shtml" method="post">
      	<table width="60%">
			<tr>  
				<td bgcolor="#F7FEFF" colspan="4">
			 		订单号：<INPUT TYPE="text" NAME="orderId" value="${order.oid }"> 
			 		支付金额：<INPUT TYPE="text" NAME="money" size="6" value="0.01" readonly="readonly">元
			 	</td>
			</tr>
			<tr><td><br/></td></tr>
			<tr>
				<td>请您选择在线支付银行</td>
			</tr>
			<tr>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CMBCHINA-NET">招商银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ICBC-NET">工商银行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ABC-NET">农业银行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CCB-NET">建设银行 </td>
			</tr>
			<tr>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CMBC-NET">中国民生银行总行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CEB-NET" >光大银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="BOCO-NET">交通银行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="SDB-NET">深圳发展银行</td>
			</tr>
			<tr>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="BOC-NET">中国银行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="PINGANBANGK-NET">平安银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CBHB-NET">渤海银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="HKBEA-NET">东亚银行</td>
			</tr>
			<tr>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="BCCB-NET">北京银行</td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CIB-NET">兴业银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="SPDB-NET">上海浦东发展银行 </td>
			  <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ECITIC-NET">中信银行</td>
			</tr>
			<tr><td><br/></td></tr>
			<tr>
			  <td><input type="submit" value="确定支付"  /></td>
			</tr>
     	</table>
   		</form>
		</div>
	</div>
</div>
</body>
</html>