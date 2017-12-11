package com.td.core.controller;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.td.common.encode.Md5Pwd;
import com.td.common.utils.PaymentUtil;
import com.td.common.web.session.SessionProvider;
import com.td.core.bean.BuyCart;
import com.td.core.bean.CartItem;
import com.td.core.bean.order.Order;
import com.td.core.bean.product.Sku;
import com.td.core.bean.user.Addr;
import com.td.core.bean.user.Buyer;
import com.td.core.query.order.OrderQuery;
import com.td.core.query.user.AddrQuery;
import com.td.core.service.country.CityService;
import com.td.core.service.country.ProvinceService;
import com.td.core.service.country.TownService;
import com.td.core.service.order.OrderService;
import com.td.core.service.product.SkuService;
import com.td.core.service.user.AddrService;
import com.td.core.service.user.BuyerService;
@Controller
public class FrontOrderController {
	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private Md5Pwd md5Pwd;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private TownService townService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private AddrService addrService;
	@Autowired
	private OrderService orderService;

	@RequestMapping("/buyer/toOrder.shtml")
	public String toOrder(ModelMap model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuyCart buyCart=null;
		ObjectMapper om=new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("buyCart_cookie")){
					String s1 = URLDecoder.decode(cookie.getValue(),"UTF-8") ;
					buyCart = om.readValue(s1, BuyCart.class);
					break;
				}
			}
		}
		if(buyCart!=null){
			if(!buyCart.isEmpty()){
				for (CartItem i : buyCart.getItems()) {
					Sku sku = skuService.getSkuByKey(i.getSku().getId());
					if(i.getAmount()>sku.getStockInventory()){
						buyCart.delete(i);
					}
				}
				StringWriter str=new StringWriter();
				om.writeValue(str, buyCart);
				Cookie c=new Cookie("buyCart_cookie", URLEncoder.encode(str.toString(), "UTF-8"));
				c.setMaxAge(60*60);//一小时
				c.setPath("/");//设置路径，默认为url
				response.addCookie(c);
			}else{
				return "redirect:/product/toCart.shtml";
			}
			if(buyCart.isEmpty()){
				return "redirect:/product/toCart.shtml";
			}
			//装购物车装满
			List<CartItem> items = buyCart.getItems();
			for (CartItem i : items) {
				Sku s = skuService.getSkuByKey(i.getSku().getId());
				i.setSku(s);
			}
			model.addAttribute("buyCart", buyCart);
			
			Buyer buyer = (Buyer) sessionProvider.getAttribute(request, "buyer_session");
			AddrQuery addrQuery=new AddrQuery();
			addrQuery.setBuyerId(buyer.getUsername());
			addrQuery.setIsDef(1);
			List<Addr> addrs = addrService.getAddrList(addrQuery);
			if(addrs!=null&&addrs.size()>0){
				model.addAttribute("addr", addrs.get(0));
				return "product/productOrder";
			}else{
				return "/buyer/index.shtml";
			}
			
		}else{
			return "redirect:/product/toCart.shtml";
			
		}
	}
	@RequestMapping("/buyer/submitOrder.shtml")
	public String submitOrder(Order order,Integer addrId, ModelMap model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuyCart buyCart=null;
		ObjectMapper om=new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("buyCart_cookie")){
					String s1 = URLDecoder.decode(cookie.getValue(),"UTF-8") ;
					buyCart = om.readValue(s1, BuyCart.class);
					break;
				}
			}
		}
		List<CartItem> items = buyCart.getItems();
		if(items!=null&&items.size()>0){
			for (CartItem i : items) {
				Sku s = skuService.getSkuByKey(i.getSku().getId());
				i.setSku(s);
			}
		}
		Buyer buyer=(Buyer) sessionProvider.getAttribute(request, "buyer_session");
		SimpleDateFormat sd=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		order.setOid(sd.format(new Date()));
		order.setDeliverFee(buyCart.getFee());
		order.setPayableFee(buyCart.getTotalPrice());
		order.setTotalPrice(buyCart.getTotalPrice());
		if(addrId!=null){
			order.setAddrId(addrId);
		}
		if(order.getPaymentWay()==0){
			order.setIsPaiy(0);
		}else{
			order.setIsPaiy(1);
		}
		order.setState(0);
		order.setCreateDate(new Date());
		order.setBuyerId(buyer.getUsername());
		order.setBuyCart(buyCart);
		orderService.addOrder(order);
		
		
		Order o = orderService.getOrderByKey(order.getId());
		model.addAttribute("order", o);
		
		
		Cookie cookie=new Cookie("buyCart_cookie",null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "product/confirmOrder";
	}
	
	@RequestMapping("/buyer/toPay.shtml")
	public String toPay(String orderId,ModelMap model) throws Exception{
		OrderQuery orderQuery=new OrderQuery();
		orderQuery.setOid(orderId);
		List<Order> orders = orderService.getOrderList(orderQuery);
		if(orders!=null&&orders.size()>0){
			Order order = orders.get(0);
			if(order.getIsPaiy()==1){
				model.addAttribute("orderId", orderId);
				return "product/pay";
			}
		}
		return "redirect:/buyer/index.shtml";
		
	}
	
	@RequestMapping("/buyer/pay.shtml")
	public String pay(String orderId,String money,String pd_FrpId ,HttpServletRequest request) throws Exception{
		String p0_Cmd = "Buy";
		String p1_MerId = "10001126856";//商户id
		String p2_Order = orderId;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "unknown";
		String p6_Pcat = "unknown";
		String p7_Pdesc = "descrition";
		String p8_Url = "http://localhost:8080/buyer/responseResult.shtml";
		String p9_SAF = "1";
		String pa_MP = "unknown";
		String pr_NeedResponse="1"; 
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		
		request.setAttribute("p0_Cmd",p0_Cmd );
		request.setAttribute("p1_MerId",p1_MerId );
		request.setAttribute("p2_Order", p2_Order);
		request.setAttribute("p3_Amt", p3_Amt);
		request.setAttribute("p4_Cur",p4_Cur );
		request.setAttribute("p5_Pid",p5_Pid );
		request.setAttribute("p6_Pcat",p6_Pcat );
		request.setAttribute("p7_Pdesc",p7_Pdesc );
		request.setAttribute("p8_Url",p8_Url );
		request.setAttribute("pa_MP",pa_MP );
		request.setAttribute("pr_NeedResponse",pr_NeedResponse );
		request.setAttribute("hmac",hmac );
		request.setAttribute("p9_SAF",p9_SAF );
		request.setAttribute("pd_FrpId", pd_FrpId);
		
		return "product/payconfirm";
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * 当支付成功，过几秒会自动响应此地址，会夹带一些参数
	 * http://localhost:8080/buyer/responseResult.shtml?p1_MerId=10001126856&r0_Cmd=Buy
	 * &r1_Code=1			//支付结果。1代表成功
	 * &r2_TrxId=868898362252252B&r3_Amt=0.01&r4_Cur=RMB&r5_Pid=unknown
	 * &r6_Order=20171209212001049			//订单编号
	 * &r7_Uid=&r8_MP=unknown
	 * &r9_BType=1						//1浏览器访问的。2点对点
	 * &ru_Trxtime=20171210102308&ro_BankOrderId=5162377500171210&rb_BankId=BOC-NET&rp_PayDate=20171210102307&rq_CardNo=&rq_SourceFee=0.0&rq_TargetFee=0.0&hmac=478945f1a5aaa92386bd60d9a9eb987c
	 */
	@RequestMapping("buyer/responseResult.shtml")
	public void responseResult(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");//支付结果。1代表成功
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur= request.getParameter("r4_Cur");
		String r5_Pid= request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");//订单编号
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");//1浏览器访问的。2点对点
		String hmac = request.getParameter("hmac");
		
		//数据校验
		boolean ok = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		if(!ok){
			out.write("数据有可能被篡改，请联系网站");
		}else{
			if("1".equals(r1_Code)){
				//支付成功：根据订单号更改订单状态。  点卡或充值时注意表单的重复提交问题。
				if("2".equals(r9_BType)){
					out.write("success");
				}
				out.write("<h1>支付成功哦哦哦！！！<font color='blue'><a href='javascript:void(0)' onclick=\"window.location.href='/buyer/index.shtml'\">点此返回我的个人中心</a></font></h1>");
				out.close();
				OrderQuery orderQuery=new OrderQuery();
				orderQuery.setOid(r6_Order);
				List<Order> orders = orderService.getOrderList(orderQuery);
				Order order = orders.get(0);
				order.setIsPaiy(2);
				orderService.updateOrderByKey(order);
			}
		}
		
	}
}
