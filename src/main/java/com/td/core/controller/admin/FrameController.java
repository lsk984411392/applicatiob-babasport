package com.td.core.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrameController {
	@RequestMapping("/frame/product_main.do")
	public String fpm(){
		return "frame/product_main";
	}
	@RequestMapping("/frame/product_left.do")
	public String fpl(){
		return "frame/product_left";
	}
	
	@RequestMapping("/frame/order_main.do")
	public String fom(){
		return "frame/order_main";
	}
	@RequestMapping("/frame/order_left.do")
	public String fpl2(){
		return "frame/order_left";
	}
	
}
