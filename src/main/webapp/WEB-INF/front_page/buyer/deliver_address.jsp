<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>收货地址_用户中心</title>
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
	function del(addrId){
		if(confirm("你确定要删除此收货地址吗？")){
			window.location.href="/buyer/deleteDeliveryAddr.shtml?addrId="+addrId;
		
		}
		
	}
$(document).ready(function(){
	var url="/product/viewCart.shtml";
	$.post(url,null,function(data){
		var items=data.items;
		var ht="";
		var amount=0;
		var totalp=0.00;
		for(i=0;i<items.length;i++){
			ht+="<p class='dt'>"+items[i].sku.product.name+"---"+items[i].sku.color.name+"---"+items[i].sku.size+"---"
			+"<b><var>¥"+items[i].sku.skuPrice+"</var><span>x"+items[i].amount+"</span></b></p>";
			amount+=items[i].amount;
			totalp+=(items[i].amount)*(items[i].sku.skuPrice);
		}
		$("#ul1").html(ht);
		
		$("#totaljian").html(amount);
		$("#totaljian2").html(amount);
		$("#totalPrice").html(totalp);
	},'json');

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
	<li class="dev">您好,欢迎 <font color="red">${buyer_session.realName }</font>来到新巴巴运动网！</li>
			<li class="dev"><a href="javascript:void(0)" onclick="window.location.href ='/buyer/toLogout.shtml'" title="退出">[退出]</a></li>
			<li class="dev"><a href="javascript:void(0)" onclick="window.location.href='/product/toCart.shtml'" title="退出">[购物车]</a></li>
			<li class="dev"><a href="javascript:void(0)" onclick="window.location.href ='/buyer/index.shtml'" title="我的订单">我的订单</a></li>
		
		<li class="dev"><a href="#" title="在线客服">在线客服</a></li>
		<li class="dev after"><a href="#" title="English">English</a></li>
	</ul>
</div></div>

<div class="w loc">
	<div class="h-title">
		<div class="h-logo"><a href="http://localhost:8080"><img src="/res/img/pic/logo-1.png" /></a></div>
	    <div class="h-search">
	      	<input type="text" />
	        <div class="h-se-btn"><a href="#">搜索</a></div>
	    </div>
	</div>
	<dl id="cart" class="cart r">
		<dt><a href="javascript:void(0)" title="结算" onclick="window.location.href ='/buyer/toOrder.shtml'"> 去结算</a  >购物车:共有  <b id="totaljian" >0</b>件</dt>
		<dd class="hidden">
			<p class="alg_c hidden">购物车中还没有商品，赶紧选购吧！</p>
			<h3 title="最新加入的商品">最新加入的商品</h3>
			<ul class="uls" id="ul1">
				
			</ul>
			<div>
				<p>共<b id="totaljian2">0</b>件商品&nbsp;&nbsp;&nbsp;&nbsp;共计<b class="f20" id="totalPrice">¥0.00</b></p>
				<a href="javascript:void(0)" title="去购物车结算" class="inb btn120x30c" onclick="window.location.href ='/buyer/toOrder.shtml'">去购物车结算</a>
			</div>
		</dd>
	</dl>
</div>

<div class="w mt ofc">
		<div class="l wl">
		
		<h2 class="h2 h2_l"><em title="交易管理">交易管理</em><cite>&nbsp;</cite></h2>
		<div class="box bg_gray">
			<ul class="ul left_nav">
			<li><a href="/buyer/index.shtml" title="我的订单">我的订单</a></li>
			<!-- <li><a href="../buyer/orders.jsp" title="退换货订单">退换货订单</a></li> -->
			<!-- <li><a href="../buyer/orders.jsp" title="我的收藏">我的收藏</a></li> -->
			</ul>
		</div>

		<h2 class="h2 h2_l mt"><em title="账户管理">账户管理</em><cite>&nbsp;</cite></h2>
		<div class="box bg_gray">
			<ul class="ul left_nav">
			<li><a href="/buyer/profile.shtml" title="个人资料">个人资料</a></li>
			<li><a href="/buyer/toDeliverAddress.shtml" title="收货地址">收货地址</a></li>
			<li><a href="/buyer/toChangePassword.shtml" title="修改密码">修改密码</a></li>
			</ul>
		</div>
	</div>
	<div class="r wr profile">

		<div class="confirm">
			<div class="tl"></div><div class="tr"></div>
			<div class="ofc">

				<h2 class="h2 h2_r2"><em title="个人资料">收货地址</em></h2>

				<h3 class="h3_r">已存收货地址列表</h3>

				<table cellspacing="0" summary="" class="tab tab6">
				<thead>
				<tr>
				<th>收货人</th>
				<th>所在地区</th>
				<th>街道地址</th>
				<th>电话/手机</th>
				<th>状态</th>
				<th>操作</th>
				</tr>                                                          
				</thead>
				<tbody>
					<c:forEach items="${addrs }" var="addr">
						<tr class="here">
							<td>${addr.name }</td>
							<td>${addr.city }</td>
							<td>${addr.addr }</td>
							<td>${addr.phone}</td>
							<td><c:if test="${addr.isDef==1 }">默认地址</c:if>
								<c:if test="${addr.isDef!=1 }"><a class="blue" href="/buyer/setDefaultDelivery.shtml?addrId=${addr.id }" title="设为默认地址">设为默认地址</a>  </c:if>
							
							</td>
							<td><!-- <a href="javascript:void(0);" title="修改" onclick="modify('1')" class="blue">[修改]</a> -->
							<a href="javascript:void(0);" title="删除" onclick="del(${addr.id})" class="blue">[删除]</a></td>
						</tr>
					</c:forEach>
				</tbody>
				</table>

				<h3 class="h3_r">新增/修改收货地址<span>手机、固定电话选填一项，其余均为必填</span></h3>

				<form id="jvForm" method="post" action="/buyer/addDeliverAddress.shtml">
					<ul class="uls form">
					<li id="errorName" class="errorTip" style="display:none">${error}</li>
					<li>
						<label for="username"><samp>*</samp>收货人姓名：</label>
						<span class="bg_text"><input type="text" id="username" name="name" vld="{required:true}" maxLength="100" /></span>
						<span class="pos"><span class="tip okTip">&nbsp;</span></span>
					</li>
					<li>
						<label for="residence"><samp>*</samp>地　　址：</label>
						<span class="word">
							<!-- <select name="">
								<option value="" selected>省/直辖市</option>
								<option value=""></option>
							</select>
							<select name="">
								<option value="" selected>城市</option>
								<option value=""></option>
							</select>
							
							<select name="">
								<option value="" selected>县/区</option>
								<option value=""></option>
							</select> -->
							<select name="provinceCode"  id="province" onchange="changeProvince(this.value)" >
								<option value="" selected>省/直辖市</option>
								<c:forEach items="${ provinces}" var="p">
									<option value="${p.code }" >${p.name }</option>
								</c:forEach>
								
							</select>
							<select name="cityCode" id="city" onchange="changeCity(this.value)">
								<option value="" >城市</option>
								<c:forEach items="${citys }" var="c">
									<option value="${c.code }" <c:if test="${buyer.city==c.code }" >selected="selected"</c:if>>${c.name }</option>
								</c:forEach>
								
							</select>
							<select name="townCode" id="town">
								<option value="" selected>县/区</option>
								<c:forEach items="${towns }" var="t">
									<option value="${t.code }" <c:if test="${buyer.town==t.code }" >selected="selected"</c:if>>${t.name }</option>
								</c:forEach>
								
								
							</select>
							
						</span>
					</li>
					<li>
						<label for="nick"><samp>*</samp>街道地址：</label>
						<span class="bg_text"><input type="text" id="nick" name="addr" maxLength="32"/></span>
						<!-- <span class="pos"><span class="tip errorTip">用户名为4-20位字母、数字或中文组成，字母区分大小写。</span></span> -->
					</li>
					<li>
						<label for="telphone"><samp>*</samp>联系电话：</label>
						<span class="bg_text"><input type="text" id="telphone" name="phone" maxLength="32"/></span>
						<!-- <span class="pos"><span class="tip warningTip">用户名为4-20位字母、数字或中文组成，字母区分大小写。</span></span> -->
					</li>
					<li>
						<label for="statusAddr">&nbsp;</label>
						<span><input type="checkbox" name="isDef" value="1"/>设为默认收货地址</span>
					</li>
					<li><label for="">&nbsp;</label><input type="submit" value="保存" class="hand btn66x23" /></li>
					</ul>
				</form>
			</div>
		</div>

	</div>
</div>

<div class="mode">
	<div class="tl"></div><div class="tr"></div>
	<ul class="uls">
		<li class="first">
			<span class="guide"></span>
			<dl>
			<dt title="购物指南">购物指南</dt>
			<dd><a href="#" title="购物流程">购物流程</a></dd>
			<dd><a href="#" title="购物流程">购物流程</a></dd>
			<dd><a href="#" target="_blank" title="联系客服">联系客服</a></dd>
			<dd><a href="#" target="_blank" title="联系客服">联系客服</a></dd>
			</dl>
		</li>
		<li>
			<span class="way"></span>
			<dl>
			<dt title="支付方式">支付方式</dt>
			<dd><a href="#" title="货到付款">货到付款</a></dd>
			<dd><a href="#" title="在线支付">在线支付</a></dd>
			<dd><a href="#" title="分期付款">分期付款</a></dd>
			<dd><a href="#" title="分期付款">分期付款</a></dd>
			</dl>
		</li>
		<li>
			<span class="help"></span>
			<dl>
			<dt title="配送方式">配送方式</dt>
			<dd><a href="#" title="上门自提">上门自提</a></dd>
			<dd><a href="#" title="上门自提">上门自提</a></dd>
			<dd><a href="#" title="上门自提">上门自提</a></dd>
			<dd><a href="#" title="上门自提">上门自提</a></dd>
			</dl>
		</li>
		<li>
			<span class="service"></span>
			<dl>
			<dt title="售后服务">售后服务</dt>
			<dd><a href="#" target="_blank" title="售后策略">售后策略</a></dd>
			<dd><a href="#" target="_blank" title="售后策略">售后策略</a></dd>
			<dd><a href="#" target="_blank" title="售后策略">售后策略</a></dd>
			<dd><a href="#" target="_blank" title="售后策略">售后策略</a></dd>
			</dl>
		</li>
		<li>
			<span class="problem"></span>
			<dl>
			<dt title="特色服务">特色服务</dt>
			<dd><a href="#" target="_blank" title="夺宝岛">夺宝岛</a></dd>
			<dd><a href="#" target="_blank" title="夺宝岛">夺宝岛</a></dd>
			<dd><a href="#" target="_blank" title="夺宝岛">夺宝岛</a></dd>
			<dd><a href="#" target="_blank" title="夺宝岛">夺宝岛</a></dd>
			</dl>
		</li>
	</ul>
</div>
</body>
</html>
