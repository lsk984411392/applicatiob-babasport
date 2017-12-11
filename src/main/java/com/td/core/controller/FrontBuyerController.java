package com.td.core.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.itcast.common.page.Pagination;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.td.common.encode.Md5Pwd;
import com.td.common.web.ResponseUtils;
import com.td.common.web.session.SessionProvider;
import com.td.core.bean.country.City;
import com.td.core.bean.country.Province;
import com.td.core.bean.country.Town;
import com.td.core.bean.order.Detail;
import com.td.core.bean.order.Order;
import com.td.core.bean.user.Addr;
import com.td.core.bean.user.Buyer;
import com.td.core.query.country.CityQuery;
import com.td.core.query.country.TownQuery;
import com.td.core.query.order.DetailQuery;
import com.td.core.query.order.OrderQuery;
import com.td.core.query.user.AddrQuery;
import com.td.core.service.country.CityService;
import com.td.core.service.country.ProvinceService;
import com.td.core.service.country.TownService;
import com.td.core.service.order.DetailService;
import com.td.core.service.order.OrderService;
import com.td.core.service.product.SkuService;
import com.td.core.service.user.AddrService;
import com.td.core.service.user.BuyerService;
@Controller
public class FrontBuyerController {
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
	@Autowired
	private DetailService detailService;
	@RequestMapping(value="/shopping/toLogin.shtml",method=RequestMethod.GET)
	public String tologin(String returnUrl,ModelMap model ){
		model.addAttribute("returnUrl", returnUrl);//如果这边不接收return的值，在loin页面有个隐藏域，用${params.returnUrl}来获取值也可以的
		return "buyer/login";
	}
	@RequestMapping("/shopping/login.shtml")
	public String  login(String returnUrl,String captcha,Buyer buyer,HttpServletRequest request,ModelMap model) throws IOException{
		//验证码是否为null
			//1:JSESSIONID
		if(StringUtils.isNotBlank(captcha)){
			//2验证码
			if(imageCaptchaService.validateResponseForID(sessionProvider.getSessionId(request), captcha)){
				if(null != buyer && StringUtils.isNotBlank(buyer.getUsername())){
					if(StringUtils.isNotBlank(buyer.getPassword())){
						Buyer b = buyerService.getBuyerByKey(buyer.getUsername());
						if(null != b){
							//
							if(b.getPassword().equals(buyer.getPassword())){//不用md5加密
								//把用户对象放在Session
								sessionProvider.setAttribute(request,"buyer_session", b);
								if(StringUtils.isNotBlank(returnUrl)){
									return "redirect:" + returnUrl;
								}else{
									//个人中心
									return "redirect:/buyer/index.shtml" ;
								}
							}else{
								model.addAttribute("error", "密码错误");
							}
						}else{
							model.addAttribute("error", "用户名输入错误");
						}
					}else{
						model.addAttribute("error", "请输入密码");
					}
				}else{
					model.addAttribute("error", "请输入用户名");
				}
			}else{
				model.addAttribute("error", "验证码输入错误");
			}
		}
		return "buyer/login";
	}
	@RequestMapping("/buyer/index.shtml")
	public String index(HttpServletRequest request,Integer pageNo,ModelMap model){
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request, "buyer_session");
		OrderQuery orderQuery=new OrderQuery();
		orderQuery.setBuyerId(buyer.getUsername());
		orderQuery.orderbyId(false);
		orderQuery.setPageNo(Pagination.cpn(pageNo));
		orderQuery.setPageSize(5);
		Pagination pagination = orderService.getOrderListWithPage(orderQuery);
		//分页展示   /brand/list.do?name=瑜伽树（yogatree）&isDisplay=1&pageNo=2
		String url = "/buyer/index.shtml";
		pagination.pageView(url, null);
		model.addAttribute("pagination", pagination);
		
		AddrQuery addrQuery=new AddrQuery();
		addrQuery.setBuyerId(buyer.getUsername());
		addrQuery.setIsDef(1);
		List<Addr> addrs = addrService.getAddrList(addrQuery);
		if(addrs!=null&&addrs.size()>0){
			model.addAttribute("addr", addrs.get(0));
			
		}
		return "buyer/index";
	}
	
	@RequestMapping("/buyer/toLogout.shtml")
	public String logout(HttpServletRequest request){
		sessionProvider.logout(request);
		return "buyer/login";
	}
	
	@RequestMapping("/buyer/profile.shtml")
	public String profile(HttpServletRequest request,ModelMap model){
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request, "buyer_session");
		model.addAttribute("buyer", buyer);
		
		List<Province> provinces = provinceService.getProvinceList(null);
		model.addAttribute("provinces", provinces);
		
		CityQuery cityQuery=new CityQuery();
		cityQuery.setProvince(buyer.getProvince());
		List<City> citys = cityService.getCityList(cityQuery);
		model.addAttribute("citys", citys);
		
		TownQuery townQuery=new TownQuery();
		townQuery.setCity(buyer.getCity());
		List<Town> towns = townService.getTownList(townQuery);	
		model.addAttribute("towns", towns);
		return "buyer/profile";
	}
	@RequestMapping(value=("/buyer/updatePro.shtml"))
	public String updateProfile(Buyer buyer,ModelMap model,HttpServletRequest request,HttpServletResponse response){
		buyerService.updateBuyerByKey(buyer);
		Buyer buyer2 = buyerService.getBuyerByKey(buyer.getUsername());
		sessionProvider.setAttribute(request, "buyer_session", buyer2);
		return "redirect:/buyer/index.shtml";
	}
	@RequestMapping("/changeCity.shtml")
	public void changeCity(String pcode,ModelMap model,HttpServletResponse response){
		
		CityQuery cityQuery=new CityQuery();
		cityQuery.setProvince(pcode);
		List<City> citys = cityService.getCityList(cityQuery);
		JSONObject j=new JSONObject();
		j.put("citys", citys);
		ResponseUtils.renderJson(response, j.toString());
	}
	@RequestMapping("/changeTown.shtml")
	public void changeTown(String ccode,ModelMap model,HttpServletResponse response){
		
		TownQuery townQuery=new TownQuery();
		townQuery.setCity(ccode);
		List<Town> towns = townService.getTownList(townQuery);	
		JSONObject j=new JSONObject();
		j.put("towns", towns);
		ResponseUtils.renderJson(response, j.toString());
	}
	
	
	@RequestMapping("/buyer/toChangePassword.shtml")
	public String tochangePassword(){
		return "buyer/change_password";
	}
	@RequestMapping("/buyer/toDeliverAddress.shtml")
	public String tode(HttpServletRequest request,ModelMap model){
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request, "buyer_session");
		AddrQuery addrQuery=new AddrQuery();
		addrQuery.setBuyerId(buyer.getUsername());
		List<Addr> addrs = addrService.getAddrList(addrQuery);
		model.addAttribute("addrs", addrs);
		List<Province> provinces = provinceService.getProvinceList(null);
		model.addAttribute("provinces", provinces);
		return "buyer/deliver_address";
	}
	@RequestMapping("/buyer/setDefaultDelivery.shtml")
	public String setDefaultDeli(Integer addrId,ModelMap model){
		Addr addr = addrService.getAddrByKey(addrId);
		AddrQuery addrQuery=new AddrQuery();
		addrQuery.setBuyerId(addr.getBuyerId());
		List<Addr> addrs = addrService.getAddrList(addrQuery);
		for (Addr addr2 : addrs) {
			addr2.setIsDef(0);
			addrService.updateAddrByKey(addr2);
		}
		addr.setIsDef(1);
		addrService.updateAddrByKey(addr);
		return "redirect:/buyer/toDeliverAddress.shtml";
	}
	@RequestMapping("/buyer/addDeliverAddress.shtml")
	public String addDeliveryAddress(Addr addr,HttpServletRequest request,ModelMap model,String provinceCode,String cityCode,String townCode){
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request, "buyer_session");
		
		if(addr.getIsDef()!=null&&addr.getIsDef()==1){
			AddrQuery addrQuery=new AddrQuery();
			addrQuery.setBuyerId(buyer.getUsername());
			List<Addr> addrs = addrService.getAddrList(addrQuery);
			for (Addr addr2 : addrs) {
				addr2.setIsDef(0);
				addrService.updateAddrByKey(addr2);
			}
		}else{
			addr.setIsDef(0);
		}
		Province p = provinceService.getProvinceByCode(provinceCode);
		City c = cityService.getCityByCode(cityCode);
		Town t = townService.getTownByCode(townCode);
		addr.setCity(p.getName()+c.getName()+t.getName());
		addr.setBuyerId(buyer.getUsername());
		addrService.addAddr(addr);
		return "redirect:/buyer/toDeliverAddress.shtml";
	}
	
	@RequestMapping("/buyer/deleteDeliveryAddr.shtml")
	public String deleteDeliveryAddress(Integer addrId,HttpServletRequest request,ModelMap model,String provinceCode,String cityCode,String townCode){
		addrService.deleteByKey(addrId);
		return "redirect:/buyer/toDeliverAddress.shtml";
	}
	@RequestMapping("/buyer/changePassword.shtml")
	public String changePassword(String password,String realPassword,HttpServletRequest request,ModelMap model){
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request, "buyer_session");
		if(buyer.getPassword().equals(password)){
			buyer.setPassword(realPassword);
			buyerService.updateBuyerByKey(buyer);
		}else{
			model.addAttribute("error", "您输入的当前密码有误");
			return "buyer/change_password";
		}
		sessionProvider.setAttribute(request, "buyer_session", null);
		return "redirect:/buyer/index.shtml";
	}
	
	@RequestMapping("/buyer/deleteOrder.shtml")
	public String deleteOrder(Integer orderId,Integer pageNo,ModelMap model){
		orderService.deleteByKey(orderId);
		model.addAttribute("pageNo", pageNo);
		return "redirect:/buyer/index.shtml";
	}
	
	@RequestMapping("/buyer/viewOrder.shtml")
	public String viewOrder(Integer orderId,ModelMap model,HttpServletRequest request){
		Order order = orderService.getOrderByKey(orderId);
		model.addAttribute("order", order);
		
		if(order.getAddrId()!=null){
			Addr addr = addrService.getAddrByKey(order.getAddrId());
			model.addAttribute("addr", addr);
		}
		/*Buyer buyer = (Buyer) sessionProvider.getAttribute(request, "buyer_session");
		AddrQuery addrQuery=new AddrQuery();
		addrQuery.setBuyerId(buyer.getUsername());
		addrQuery.setIsDef(1);
		List<Addr> addrs = addrService.getAddrList(addrQuery);
		if(addrs!=null&&addrs.size()>0){
			model.addAttribute("addr", addrs.get(0));
		}*/
		
		DetailQuery detailQuery=new DetailQuery();
		detailQuery.setOrderId(order.getId());
		List<Detail> details = detailService.getDetailList(detailQuery);
		model.addAttribute("details", details);
		return "product/productOrderDetail";
	}
	
}
