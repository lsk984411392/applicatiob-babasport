<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>注册_康康运动网</title>
<link rel="stylesheet" href="/res/css/style.css" />
<script src="/res/js/jquery.js"></script>
<script src="/res/js/com.js"></script>
</head>
<script type="text/javascript">
	function changeProvince(pcode){
		var url="/changeCity.shtml";
		var param={"pcode":pcode};
		$.post(url,param,function(data){
			var citys=data.citys;
			var h="<option value='' >城市</option>";
			for(i=0;i<citys.length;i++){
				h+="<option value="+citys[i].code+" >"+citys[i].name+"</option>";
			}
			$("#city").html(h);
			$("#town").html("<option value='' >县/区</option>"); 
		},'json');
	}
	function changeCity(ccode){
		var url="/changeTown.shtml";
		var param={"ccode":ccode};
		$.post(url,param,function(data){
			var towns=data.towns;
			var h="<option value='' >县/区</option>";
			for(i=0;i<towns.length;i++){
				h+="<option value="+towns[i].code+" >"+towns[i].name+"</option>";
			}
			$("#town").html(h); 
		},'json');
	}
	function login(){
	window.location.href = "/shopping/toLogin.shtml?returnUrl="+window.location.href;
}
$(document).ready(function(){
	$("#u1").hover(function(){
		$("#f1").toggle();
	});
	

});

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
		<div class="h-logo l"><a href="/" ><img src="/res/img/pic/logo-1.png" /></a></div>
		<div class="l" style="margin: 13px 10px;font-size: 20px;font-family: '微软雅黑';letter-spacing: 2px">欢迎注册</div>
	</div>
</div>
<div class="sign">
	<div class="l ad420x205"><a href="#" title="title"><img src="/res/img/pic/ppp0.jpg" width="400" height="400"/></a></div>
	<div class="r">
		<h2 title="登录新巴巴运动网">注册新巴巴运动网</h2>
		<form id="jvForm" action="/product/register.shtml" method="post">
		
			<ul class="uls form">
					<li>
						
						<label for="username">  用 户 名：</label>
						<span class="word"><input  name="username" value="" id="u1"><font style="display:none" id="f1" color="red">数字英文字母</font> </span>
					</li>
					<li>
						<input type="password" name="password">
						<label for="username">密　　码：</label>
						<span class="word"></span>
					</li>
					<li>
						<input  name="email" value="">
						<label for="username">邮　　箱：</label>
						<span class="word"></span>
					</li>
					<li>
						<label for="realName">真实姓名：</label>
						<span class="bg_text"><input type="text" id="realName" name="realName" maxLength="32" value=""/></span>
						<span class="pos"><span class="tip okTip">&nbsp;</span></span>
					</li>
					<li>
						<label for="gender">性　　别：</label>
						<span class="word"><input type="radio" name="gender"  value="SECRECY"/>保密
						<input type="radio" name="gender" value="MAN"/>男
						<input type="radio" name="gender" value="WOMAN"/>女</span>
					</li>
					<li>
						<label for="residence">居 住 地：</label>
						<span class="word">
							<select name="province"  id="province" onchange="changeProvince(this.value)" >
								<option value="" selected>省/直辖市</option>
								<c:forEach items="${ provinces}" var="p">
									<option value="${p.code }" >${p.name }</option>
								</c:forEach>
								
							</select>
							<select name="city" id="city" onchange="changeCity(this.value)">
								<option value="" >城市</option>
								<c:forEach items="${citys }" var="c">
									<option value="${c.code }" >${c.name }</option>
								</c:forEach>
								
							</select>
							<select name="town" id="town">
								<option value="" selected>县/区</option>
								<c:forEach items="${towns }" var="t">
									<option value="${t.code }" >${t.name }</option>
								</c:forEach>
								
								
							</select>
						</span>
					</li>
					<li><label for="address">详细地址：</label>
						<span class="bg_text"><input type="text" id="address" name="addr" maxLength="32" value=""/></span>
						<!-- <span class="pos"><span class="tip errorTip">用户名为4-20位字母、数字或中文组成，字母区分大小写。</span></span> -->
					</li>
					<li><label for="address">手机号：</label>
						<span class="bg_text"><input type="text" id="address" name="phone" maxLength="32" value=""/></span>
						<!-- <span class="pos"><span class="tip errorTip">用户名为4-20位字母、数字或中文组成，字母区分大小写。</span></span> -->
					</li>
					
					<li><label for="">&nbsp;</label><input type="submit" value="注册" class="hand btn66x23"  /></li>
			</ul>
			
		</form>
	</div>
</div>
</body>
</html>