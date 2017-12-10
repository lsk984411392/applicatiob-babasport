<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>我的购物车</title>
<link rel="stylesheet" href="/res/css/style.css" />
<script src="/res/js/jquery.js"></script>
<script src="/res/js/com.js"></script>
<script type="text/javascript">
//结算
function trueBuy(){
 	window.location.href = "/buyer/toOrder.shtml";
}
function delProduct(skuId,productName){
	if(confirm("您确定要删除此商品吗？")){
		window.location.href = "/product/delete.shtml?skuId="+skuId;
	}
}
function login(){
	window.location.href = "/shopping/toLogin.shtml?returnUrl="+window.location.href;
}

$(document).ready(function(){
	$("#d1").offset({"top":35,"left":160});

});
function addProductAmount(skuId,buyLimit,productId){
	var a=$("#num492").val();
	if(a>=buyLimit){
		alert("此商品嘴最多购买"+buyLimit+"件哦哦！！！");
		return ;
	}
	var amount =1;
	window.location.href="/product/toCart.shtml?skuId="+skuId+"&buyLimit="+buyLimit+"&amount="+amount+"&productId="+productId;
	
}
function subProductAmount(skuId,buyLimit,productId){
	var a=$("#num492").val();
	if(a==1){
		return ;
	}
	var amount =-1;
	window.location.href="/product/toCart.shtml?skuId="+skuId+"&buyLimit="+buyLimit+"&amount="+amount+"&productId="+productId;
	

}
</script>
</head>
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
<div>
	<div class="h-logo" id="d1"><a href="http://localhost:8080">&nbsp;&nbsp;&nbsp;&nbsp;<img src="/res/img/pic/logo-1.png" /></a></div>
	<ul class="ul step st3_1">
	
	<li title="1.我的购物车" class="here">1.我的购物车</li>
	<li title="2.填写核对订单信息">2.填写核对订单信息</li>
	<li title="3.成功提交订单">3.成功提交订单</li>
	</ul>
</div>
<c:if test="${fn:length(buyCart.items)!=0 }">
<div class="w ofc case">
	<div class="confirm">
		<div class="tl"></div><div class="tr"></div>
		<div class="ofc pb40">

			<div class="page">
				<b class="l f14 blue pt48">
					我挑选的商品：
				</b>
			</div>
			<table cellspacing="0" class="tab tab4" summary="">
			<thead>
			<tr>
			<th class="wp">商品</th>
			<th>单价（元）</th>
			<th>数量</th>
			<th>操作</th>
			</tr>     
			</thead>
			<tbody>
			<c:forEach items="${buyCart.items}" var="item">
				<tr>
					<td class="nwp pic">
						<ul class="uls">
							<li>
								<a class="pic" title=" ${item.sku.product.name }" href="javascript:void(0)"><img alt="${item.sku.product.name }" src="${item.sku.product.img.url }"></a>
								<dl>
									<dt><a title=" ${item.sku.product.name }" href="javascript:void(0)" onclick="window.open('/product/detail.shtml?productId=${item.sku.product.id}')"> ${item.sku.product.name }----${item.sku.color.name }----${item.sku.size }</a></dt>
									
								</dl>
							</li>
						</ul>
					</td>
					<td>￥${item.sku.skuPrice }</td>
					<td>
					
					
						<a onclick="subProductAmount(${item.sku.id},${item.sku.skuUpperLimit },${productId })" class="inb arr" title="减" href="javascript:void(0);">-</a>
						<input type="text" id="num492" readonly="readonly" value="${item.amount }" name="" size="1" class="txts">
						<a onclick="addProductAmount(${item.sku.id},${item.sku.skuUpperLimit },${productId })" class="inb arr" title="加" href="javascript:void(0);">+</a>
					
					</td>
					<td class="blue"><a onclick="delProduct('${item.sku.id}','${item.sku.product.name }')" title="删除" href="javascript:void(0);">删除</a></td>
				</tr>
				</c:forEach>
				
				           
			</tbody>
			</table>
			<div class="page">
				<span class="l">
					<input type="button" onclick="window.location.href='/product/detail.shtml?productId=${productId}'" class="hand btn100x26c" title="继续购物" value="继续购物">
					<input type="button" onclick="window.location.href='/product/clear.shtml'" class="hand btn100x26c" title="清空购物车" value="清空购物车">
				</span>
				<span class="r box_gray">
					<dl class="total">
						<dt>购物车金额小计：<cite>(共<var id="productAmount">${buyCart.productAmount }</var>个商品)</cite></dt>
						<dd><em class="l">商品金额：</em>￥<var id="productPrice">${buyCart.productPrice }</var>元</dd>
						<dd><em class="l">运费：</em>￥<var id="fee">${buyCart.fee }</var>元</dd>
						<dd class="orange"><em class="l">应付总额：</em>￥<var id="totalPrice">${buyCart.totalPrice }</var>元</dd>
						<dd class="alg_c"><input type="button" onclick="trueBuy();" class="hand btn136x36a" value="结算" id="settleAccountId"></dd>
					</dl>
				</span>
			</div>
		</div>
	</div>
</div>
</c:if>
<div class="w ofc case" ">
	<c:if test="${fn:length(buyCart.items)==0 }">
	<div class="confirm">
		<div class="tl"></div><div class="tr"></div>
			<div class="ofc pb40" style="text-align: center;height: 200px;margin-top: 80px">
	       		 <a href="http://localhost:8080" style="color: red;font-size: 30px;">去首页</a>挑选喜欢的商品
			</div>
	</div>
	</c:if>
</div>
<div class="mode">
	<div class="tl"></div><div class="tr"></div>
	<ul class="uls">
		<li class="first">
			<span class="guide"></span>
			<dl>
			<dt title="购物指南">购物指南</dt>
			<dd><a href="#" title="购物流程">购物流程</a></dd>
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