<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>康康运动网_用户中心</title>
<link rel="stylesheet" href="/res/css/style.css" />
<script src="/res/js/jquery.js"></script>
<script src="/res/js/com.js"></script>
</head>
<script type="text/javascript">
	
	function idDelteOrder(orderId){
		if(confirm("您确定删除此订单")){
			window.location.href="/buyer/deleteOrder.shtml?orderId="+orderId+"&pageNo="+'${pagination.pageNo}';
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
		<div class="h-logo"><a href="/" ><img src="/res/img/pic/logo-1.png" /></a></div>
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
		<h2 class="h2 h2_filter"><em style="color:red">全部订单</em></h2>
		<table cellspacing="0" summary="" class="tab tab4" >
		<thead>
		<tr>
		<th width="12%">订单编号</th>
		<th width="7&">商品名称</th>
		<th width="5%">数量</th>
		<th width="10%">收货人</th>
		<th width="5%">总金额（元）</th>
		<th width="10%">下单时间</th>
		<th width="10%">付款详情</th>
		<th width="7%">订单状态</th>
		<th width="30%">操作</th>
		</tr>     
		</thead>
		<tbody>        
		
		<c:forEach items="${pagination.list }" var="order">
			<tr class="over">
				<td>${order.oid }</td>
				<td >
					<ul >
						<c:forEach items="${ order.details}" var="detail">
						<li>
								<dl>
									<dt>${detail.productName }</dt>
								</dl>
						</li>
					</c:forEach>
					</ul>
				</td>
				<td >
					 <ul >
						<c:forEach items="${ order.details}" var="detail">
						
								<dl>
									<dt>X${detail.amount }</dt>
								</dl>
							
						
					</c:forEach>
					</ul>
					
				</td>
				<td>${addr.name }</td>
				<td>￥${order.totalPrice }</td>
				<td>${fn:substring(order.createDate,0,10) }</td>
				<td>${order.isPaiyName }<br /></td>
				<td>
					<c:if test="${order.isPaiy==1 }"><a class="blue" href="javascript:void(0);" onclick="window.open('/buyer/toPay.shtml?orderId=${order.oid}')">去付款</a>  </c:if>
					<c:if test="${order.isPaiy==2 }">已完成</c:if><br />
				</td>
				<td class="blue"><a href="javascript:void(0);" title="查看详情" onclick="window.open('/buyer/viewOrder.shtml?orderId=${order.id}')">详情</a>
				<a href="javascript:void(0);" title="查看详情" onclick="idDelteOrder(${order.id})">删除订单</a>
				<br />
				</td>
				
				<!-- <a href="javascript:void(0);" title="跟踪物流">跟踪物流</a></td> -->
			</tr>
			</c:forEach>
		</tbody>
		</table>
		
		
		<span class="r inb_a page_b">
				<c:forEach items="${pagination.pageView}" var="page">
					${page }
				</c:forEach>
		</span>
	
<div class="page pb15"></div>
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