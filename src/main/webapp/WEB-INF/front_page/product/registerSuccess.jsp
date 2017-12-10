<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登录_新巴巴运动网</title>
<link rel="stylesheet" href="/res/css/style.css" />
<script src="/res/js/jquery.js"></script>
<script src="/res/js/com.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
		setInterval(function(){
   			 var a=$("#s1").html();
   			 if(a>1){
   			 	$("#s1").html(--a);
   			 }else{
   			 	window.location.href="http://localhost:8080";
   			 	return;
   			 }
		},1000);


})
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
<div class="w loc">
	<div class="h-title" id="logo">
		<div class="h-logo l"><a href="http://localhost:8080"><img src="/res/img/pic/logo-1.png" /></a></div>
	</div>
</div>
<div class="sign">
		
		<h1><a href="javascript:void(0)" onclick="window.location.href ='/shopping/toLogin.shtml'" title="退出">
		注册成功！！！[点此跳往首页]或者静等五秒钟自动调往登录页面</a>
		<font color="red"><span id="s1">5</span></font>
		 </h1>
		


</div>
</body>
</html>