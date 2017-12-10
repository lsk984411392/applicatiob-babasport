package com.td.core.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.td.core.bean.product.Type;
import com.td.core.service.product.TypeService;



@Controller
public class TypeController {
	@Autowired
	private TypeService typeService;
	@RequestMapping("/type/list.do")
	public String list(ModelMap model){
		List<Type> types = typeService.getTypeList(null);
		model.addAttribute("types", types);
		return "type/list";
	}

	
	
	
	
	
	
	
}
