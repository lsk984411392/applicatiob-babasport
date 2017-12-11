package com.td.core.controller;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;

import com.td.core.bean.country.Province;
import com.td.core.bean.product.Brand;
import com.td.core.bean.product.Color;
import com.td.core.bean.product.Feature;
import com.td.core.bean.product.Product;
import com.td.core.bean.product.Sku;
import com.td.core.bean.product.Type;
import com.td.core.bean.user.Buyer;
import com.td.core.query.country.ProvinceQuery;
import com.td.core.query.product.BrandQuery;
import com.td.core.query.product.FeatureQuery;
import com.td.core.query.product.ProductQuery;
import com.td.core.query.product.TypeQuery;
import com.td.core.service.country.CityService;
import com.td.core.service.country.ProvinceService;
import com.td.core.service.country.TownService;
import com.td.core.service.product.BrandServiceImpl;
import com.td.core.service.product.ColorService;
import com.td.core.service.product.FeatureService;
import com.td.core.service.product.ImgService;
import com.td.core.service.product.ProductService;
import com.td.core.service.product.SkuService;
import com.td.core.service.product.TypeService;
import com.td.core.service.user.BuyerService;
@Controller
public class FrontProductController {
	@Autowired
	private ProductService productServiceImpl;
	@Autowired
	private BrandServiceImpl brandServiceImpl;
	@Autowired
	private TypeService typeService;
	@Autowired
	private FeatureService featureService;
	@Autowired
	private ColorService colorService;
	@Autowired
	private ImgService imgService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private TownService townService;
	@Autowired
	private BuyerService buyerService;
	@RequestMapping("/product/display/list.shtml")
	public String list(Integer pageNo,ModelMap model,Integer brandId,String brandName,Integer typeId,String typeName,String featureName,Integer featureId,String searchName){
		StringBuffer params=new StringBuffer();
		ProductQuery productQuery=new ProductQuery();
		productQuery.orderbyId(false);//按照id倒序查询
		productQuery.setIsShow(1);//设置 商品 上架
		LinkedHashMap<String, String> map=new LinkedHashMap<String, String>();
		boolean flag=false;
		if(StringUtils.isNotBlank(searchName)){
			productQuery.setNameLike(true);
			productQuery.setName(searchName);
			model.addAttribute("searchName", searchName);
		}
		if(StringUtils.isNotBlank(featureName)){
			flag=true;
			productQuery.setFeature(featureId.toString());
			model.addAttribute("featureName", featureName);
			model.addAttribute("featureId", featureId);
			map.put("材质", featureName);
			params.append("&featureName=").append(featureName).append("&featureId=").append(featureId);
		}else{
			FeatureQuery featureQuery=new FeatureQuery();
			featureQuery.setFields("id,name");
			List<Feature> features = featureService.getFeatureList(featureQuery);
			model.addAttribute("features", features);
		}
		if(brandId!=null){
			flag=true;
			productQuery.setBrandId(brandId);
			model.addAttribute("brandId", brandId);
			model.addAttribute("brandName", brandName);
			map.put("品牌", brandName);
			params.append("&").append("brandId=").append(brandId).append("&brandName=").append(brandName);
		}else{
			//品牌
			BrandQuery brandQuery=new BrandQuery();
			brandQuery.setFields("id,name");
			brandQuery.setIsDisplay(1);
			List<Brand> brands = brandServiceImpl.getBrandList(brandQuery);
			model.addAttribute("brands", brands);
		}
		if(typeId!=null){
			flag=true;
			productQuery.setTypeId(typeId);
			model.addAttribute("typeId", typeId);
			model.addAttribute("typeName", typeName);
			map.put("类型", typeName);
			params.append("&").append("typeId=").append(typeId).append("&typeName=").append(typeName);
		}else{
			//商品类型 
			TypeQuery typeQuery=new TypeQuery();
			typeQuery.setFields("id,name");
			typeQuery.setIsDisplay(1);
			List<Type> types = typeService.getTypeList(typeQuery);
			model.addAttribute("types", types);
		}
		model.addAttribute("flag", flag);
		model.addAttribute("map", map);
		
		
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(8);
		String url="/product/display/list.shtml";
		Pagination pagination = productServiceImpl.getProductListWithPage(productQuery);
		pagination.pageView(url, params.toString());
		model.addAttribute("pagination", pagination);
		return "product/product";
	}
	@RequestMapping("/product/detail.shtml")
	public String detail(Integer productId,ModelMap model){
		Product product = productServiceImpl.getProductByKey(productId);
		model.addAttribute("product", product);
		
		List<Sku> skus = skuService.getSkuListKucundayuling(productId);//查找库存大于0 的商品单元
		HashSet<Color> colors=new HashSet<Color>();
		for (Sku sku : skus) {
			Color c = sku.getColor();
			colors.add(c);
		}
		model.addAttribute("colors", colors);
		model.addAttribute("skus", skus);
		return "product/productDetail";
	}
	@RequestMapping("/product/toRegister.shtml")
	public String toRe(ModelMap model){
		ProvinceQuery provinceQuery=new ProvinceQuery();
		List<Province> provinces = provinceService.getProvinceList(provinceQuery);
		model.addAttribute("provinces", provinces);
		return "product/register";
	}
	
	@RequestMapping("/product/register.shtml")
	public String register(Buyer buyer,ModelMap model,String phone){
		buyer.setRegisterTime(new Date());
		buyer.setIsDel(1);
		buyerService.addBuyer(buyer ,phone);
		
		return "product/registerSuccess";
	}
	
	
}