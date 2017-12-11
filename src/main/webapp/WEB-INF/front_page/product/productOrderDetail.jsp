<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>查看订单详情</title>
<link rel="stylesheet" href="/res/css/style.css" />
<script src="/res/js/jquery.js"></script>
<script src="/res/js/com.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$("#d1").offset({"top":15,"left":160});

});
function login(){
	window.location.href = "/shopping/toLogin.shtml?returnUrl="+window.location.href;
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

<div class="h-logo" id="d1"><a href="/" >&nbsp;&nbsp;&nbsp;&nbsp;<img src="/res/img/pic/logo-1.png" /></a></div>
<center><h1>查看订单详情</h1></center>

<div class="w ofc case">
	<h2 class="h2 h2_r mt"><em title="收货人信息">收货人信息   </em><cite></cite></h2>
	<div class="box bg_white">
		<dl class="distr">
			<dd>${addr.name }   ${addr.phone } <span style="margin-left: 30px">${addr.city } ${addr.addr }</span></dd>
		</dl>
	</div>
	
	<div class="box bg_white pb">
		<dl class="distr">
			<dd class="box_d bg_gray2 ofc">
				<ul class="uls form">
					<li>
						<label for="deliveryTime">支付方式：</label>
						${order.paymentWayName }
					</li>
					
				</ul>
			</dd>
			
			
			<dd class="box_d bg_gray2 ofc">
				<ul class="uls form">
					<li>
						<label for="deliveryTime">送货时间：</label>
						${order.deliveryName }
					</li>
				</ul>
			</dd>
			<dd class="box_d bg_gray2 ofc">
				<ul class="uls form">
					<li>
						<label for="deliveryTime">支付状态：</label>
						${order.isPaiyName }
						<c:if test="${order.isPaiy==1 }">去付款</c:if>
					</li>
				</ul>
			</dd>
		</dl>
	</div>
	
	

	<h2 class="h2 h2_r mt"><em title="商品清单">商品清单</em><cite></cite></h2>
	<div class="box bg_white pb">
		<table cellspacing="0" class="tab tab4" summary="">
		<thead>
		<tr>
		<th>编号</th>
		<th >商品</th>
		<th>商品价格（元）</th>
		<th>数量</th>
		</tr>                                                                                           
		</thead>
		<tbody>
		<c:forEach items="${details }" var="detail">
			<tr>
				<td>${order.oid }</td>
				<td class="img48x20">
					<%-- <span class="inb"><img src="${item.sku.product.img.url }"></span> --%>
					<a target="block" href="javascript:void(0);"> ${detail.productName }--${detail.color }--${detail.size }</a>
				</td>
				<td>￥ ${detail.skuPrice}元</td>
				<td class="tt">${detail.amount }</td>
			</tr>
		</c:forEach>
		</tbody>
		
		</table>
	</div>

	 <div class="confirm mt">
		<div class="tl"></div><div class="tr"></div>
		<div class="ofc">
			<div class="r">
				<dl class="total">
					<dt>订单金额：<!-- <cite>(共<var id="totalNum"></var>个商品)</cite> --></dt>
					<dd><em class="l">商品金额：</em>￥<var>${order.payableFee }</var></dd>
					<dd><em class="l">返现：</em>￥<var>0.00</var></dd>
					<dd><em class="l">运费：</em>￥<var>${order.deliverFee}</var></dd>
					<dd class="orange"><em class="l">应付总额：</em>￥<var id="totalMoney">${order.totalPrice }</var></dd>
					<c:if test="${order.isPaiy==1 }">
						<dd class="alg_c"><input type="button" class="hand btn136x36a" value="去付款" onclick="window.location.href='/buyer/toPay.shtml?orderId=${order.oid}'"></dd>
					</c:if>
					<c:if test="${order.isPaiy==2 }">
						<dd class="alg_c"><input type="button" class="hand btn136x36a" value="订单已完成" ></dd>
					</c:if>
					<c:if test="${order.isPaiy==0 }">
						<dd class="alg_c"><input type="button" class="hand btn136x36a" value="货到付款" ></dd>
					</c:if>
				</dl>
			</div>
		</div>
    </div>
</div>
</body>

</html>