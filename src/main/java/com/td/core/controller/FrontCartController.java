package com.td.core.controller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.td.common.web.ResponseUtils;
import com.td.core.bean.BuyCart;
import com.td.core.bean.CartItem;
import com.td.core.bean.product.Sku;
import com.td.core.service.product.ProductService;
import com.td.core.service.product.SkuService;
@Controller
public class FrontCartController {
	@Autowired
	private ProductService productServiceImpl;
	@Autowired
	private SkuService skuService;
	
	@RequestMapping("/product/toCart.shtml")
	public String list(Integer amount,ModelMap model,Integer buyLimit,Integer skuId,Integer productId,HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuyCart buyCart=null;
		ObjectMapper om=new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("buyCart_cookie")){
					String s1 = cookie.getValue();
					buyCart = om.readValue(s1, BuyCart.class);
					break;
				}
			}
		}
		if(buyCart==null){
			buyCart=new BuyCart();
		}
		if(skuId!=null){
			Sku sku=new Sku();
			sku.setId(skuId);
			sku.setSkuUpperLimit(buyLimit);
			CartItem item=new CartItem();
			item.setSku(sku);
			item.setAmount(amount);
			buyCart.add(item);
			buyCart.setProductId(productId);
			StringWriter str=new StringWriter();
			om.writeValue(str, buyCart);
			Cookie c=new Cookie("buyCart_cookie", str.toString());
			c.setMaxAge(60*60);//一小时
			c.setPath("/");//设置路径，默认为url
			response.addCookie(c);
		}
		//装购物车装满
		List<CartItem> items = buyCart.getItems();
		for (CartItem i : items) {
			Sku s = skuService.getSkuByKey(i.getSku().getId());
			i.setSku(s);
		}
		model.addAttribute("buyCart", buyCart);
		model.addAttribute("productId", buyCart.getProductId());
		return "product/cart";
	}
	@RequestMapping("/product/addCart.shtml")
	public void addCart(Integer amount,ModelMap model,Integer buyLimit,Integer skuId,Integer productId,HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuyCart buyCart=null;
		ObjectMapper om=new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("buyCart_cookie")){
					String s1 = cookie.getValue();
					buyCart = om.readValue(s1, BuyCart.class);
					break;
				}
			}
		}
		if(buyCart==null){
			buyCart=new BuyCart();
		}
		if(skuId!=null){
			Sku sku=new Sku();
			sku.setId(skuId);
			sku.setSkuUpperLimit(buyLimit);
			CartItem item=new CartItem();
			item.setSku(sku);
			item.setAmount(amount);
			buyCart.add(item);
			buyCart.setProductId(productId);
			StringWriter str=new StringWriter();
			om.writeValue(str, buyCart);
			Cookie c=new Cookie("buyCart_cookie", str.toString());
			c.setMaxAge(60*60);//一小时
			c.setPath("/");//设置路径，默认为url
			response.addCookie(c);
		}
		JSONObject js=new JSONObject();
		js.put("message", "添加购物车成功哦！！！");
		ResponseUtils.renderJson(response, js.toString());
	}
	@RequestMapping("/product/delete.shtml")
	public String delete(ModelMap model,Integer skuId,HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuyCart buyCart=null;
		ObjectMapper om=new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("buyCart_cookie")){
					String s1 = cookie.getValue();
					buyCart = om.readValue(s1, BuyCart.class);
					break;
				}
			}
		}
		Sku sku=new Sku();
		sku.setId(skuId);
		CartItem i1=new CartItem();
		i1.setSku(sku);
		buyCart.delete(i1);
		
		StringWriter str=new StringWriter();
		om.writeValue(str, buyCart);
		Cookie c=new Cookie("buyCart_cookie", str.toString());
		c.setMaxAge(60*60);//一小时
		c.setPath("/");//设置路径，默认为url
		response.addCookie(c);
		//装购物车装满
		List<CartItem> items = buyCart.getItems();
		for (CartItem i : items) {
			Sku s = skuService.getSkuByKey(i.getSku().getId());
			i.setSku(s);
		}
		return "redirect:/product/toCart.shtml";
	}
	@RequestMapping("/product/clear.shtml")
	public String clear(ModelMap model,HttpServletResponse response,HttpServletRequest request) throws Exception{
		Cookie cookie=new Cookie("buyCart_cookie",null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/product/toCart.shtml";
	}
	@RequestMapping("/product/viewCart.shtml")
	public void viewCart(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuyCart buyCart=null;
		ObjectMapper om=new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("buyCart_cookie")){
					String s1 = cookie.getValue();
					buyCart = om.readValue(s1, BuyCart.class);
					break;
				}
			}
		}
		if(buyCart!=null){
			//装购物车装满
			List<CartItem> items = buyCart.getItems();
			for (CartItem i : items) {
				Sku s = skuService.getSkuByKey(i.getSku().getId());
				i.setSku(s);
			}
			JSONObject js=new JSONObject();
			js.put("items", buyCart.getItems());
			ResponseUtils.renderJson(response, js.toString());
		}
	}
}
