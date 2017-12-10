package com.td.core.controller.admin;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.td.common.web.ResponseUtils;
import com.td.core.bean.product.Sku;
import com.td.core.query.product.SkuQuery;
import com.td.core.service.product.ColorService;
import com.td.core.service.product.SkuService;
@Controller
public class SkuController {
	@Autowired
	private SkuService skuService;
	@Autowired
	private ColorService colorService;
	@RequestMapping("/sku/list.do")
	public String list(String productNo,Integer productId,ModelMap model){
		SkuQuery skuQuery=new SkuQuery();
		skuQuery.setProductId(productId);
		List<Sku> skus = skuService.getSkuList(skuQuery);
		for (Sku sku : skus) {
			sku.setColor(colorService.getColorByKey(sku.getColorId()));
		}
		model.addAttribute("skus", skus);
		model.addAttribute("productNo", productNo);
		return "sku/list";
	}
	@RequestMapping("/sku/update.do")
	public void update(Sku sku,HttpServletResponse response){
		skuService.updateSkuByKey(sku);
		JSONObject json=new JSONObject();
		json.put("message", "修改成功");
		ResponseUtils.renderJson(response, json.toString());
	}
}
