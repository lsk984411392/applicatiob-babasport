package com.td.core.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;

import com.td.core.bean.order.Detail;
import com.td.core.bean.order.Order;
import com.td.core.bean.user.Addr;
import com.td.core.bean.user.Buyer;
import com.td.core.query.order.DetailQuery;
import com.td.core.query.order.OrderQuery;
import com.td.core.query.user.AddrQuery;
import com.td.core.query.user.BuyerQuery;
import com.td.core.service.order.DetailService;
import com.td.core.service.order.OrderService;
import com.td.core.service.user.AddrService;
import com.td.core.service.user.BuyerService;



@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private DetailService detailService;
	@Autowired
	private AddrService addrService;
	@Autowired
	private BuyerService buyerService;
		/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping("/order/list.do")
	public String fpl223(Integer isPaiy,Integer state ,Integer pageNo,ModelMap model){
		StringBuilder params=new StringBuilder();
		OrderQuery orderQuery=new OrderQuery();
		if(isPaiy!=null){
			orderQuery.setIsPaiy(isPaiy);
			params.append("&isPaiy=").append(isPaiy);
		}
		if(state!=null){
			orderQuery.setState(state);
			params.append("&state=").append(state);
		}
		orderQuery.orderbyId(false);
		orderQuery.setPageNo(Pagination.cpn(pageNo));
		orderQuery.setPageSize(8);
		
		Pagination pagination = orderService.getOrderListWithPage(orderQuery);
		String url="/order/list.do";
		pagination.pageView(url, params.toString());
		model.addAttribute("pagination", pagination);
		return "order/list";
	}
	@RequestMapping("/order/view.do")
	public String view(Integer  orderId,ModelMap model){
		Order order = orderService.getOrderByKey(orderId);
		model.addAttribute("order", order);
		AddrQuery addrQuery=new AddrQuery();
		addrQuery.setBuyerId(order.getBuyerId());
		addrQuery.setIsDef(1);
		List<Addr> addrs = addrService.getAddrList(addrQuery);
		if(addrs!=null&&addrs.size()>0){
			model.addAttribute("addr", addrs.get(0));
		}
		
		
		DetailQuery detailQuery=new DetailQuery();
		detailQuery.setOrderId(orderId);
		List<Detail> details = detailService.getDetailList(detailQuery);
		if(details!=null&&details.size()>0){
			model.addAttribute("details", details);
		}
		
		
		return "order/view";
	}
	
	
	
	
	
	
	
	
	
}
