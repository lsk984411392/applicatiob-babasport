package com.td.core.controller.admin;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fckeditor.response.UploadResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.itcast.common.page.Pagination;

import com.td.common.web.ResponseUtils;
import com.td.core.bean.product.Brand;
import com.td.core.bean.product.Color;
import com.td.core.bean.product.Feature;
import com.td.core.bean.product.Img;
import com.td.core.bean.product.Product;
import com.td.core.bean.product.Type;
import com.td.core.query.product.BrandQuery;
import com.td.core.query.product.ColorQuery;
import com.td.core.query.product.FeatureQuery;
import com.td.core.query.product.ProductQuery;
import com.td.core.query.product.TypeQuery;
import com.td.core.service.product.BrandServiceImpl;
import com.td.core.service.product.ColorService;
import com.td.core.service.product.FeatureService;
import com.td.core.service.product.ImgService;
import com.td.core.service.product.ProductService;
import com.td.core.service.product.SkuService;
import com.td.core.service.product.TypeService;
@Controller
public class ProductController {
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
	@RequestMapping("/product/list.do")
	public String list(String name,Integer brandId,Integer isShow,Integer pageNo,ModelMap model){
		ProductQuery product=new ProductQuery();
		StringBuffer sb=new StringBuffer();
		if(StringUtils.isNotBlank(name)){
			product.setName(name);
			sb.append("&name=").append(name);
			model.addAttribute("name", name);
		}
		if(brandId!=null){
			product.setBrandId(brandId);
			sb.append("&brandId=").append(brandId);
			model.addAttribute("brandId", brandId);
		}
		if(isShow==null){
			product.setIsShow(0);//默认上下架为   下架
			sb.append("&isShow=").append(0);
			model.addAttribute("isShow", 0);
		}else{
			product.setIsShow(isShow);
			sb.append("&isShow=").append(isShow);
			model.addAttribute("isShow", isShow);
		}
		BrandQuery brand=new BrandQuery();
		brand.setFields("id,name");
		Pagination p1 = brandServiceImpl.getBrandListWithPage(brand);
		model.addAttribute("p1", p1);
		
		product.setPageNo(Pagination.cpn(pageNo));
		product.setPageSize(5);
		product.orderbyId(false);
		String url="/product/list.do";
		Pagination pagination = productServiceImpl.getProductListWithPage(product);
		pagination.pageView(url, sb.toString());
		model.addAttribute("pagination", pagination);
		return "product/list";
	}
	@RequestMapping("/product/shangjia.do")
	public String list(Integer[] productId,String name,Integer brandId,Integer isShow,Integer pageNo,ModelMap model){
		Product product=new Product();
		for (Integer i : productId) {
			product.setId(i);
			product.setIsShow(1);
			productServiceImpl.updateProductByKey(product);
		}
		if(StringUtils.isNotBlank(name)){
			model.addAttribute("name", name);
		}
		if(brandId!=null){
			model.addAttribute("brandId", brandId);
		}
		if(isShow==null){
			model.addAttribute("isShow", 0);
		}else{
			model.addAttribute("isShow", isShow);
		}
		model.addAttribute("pageNo", pageNo);
		return "redirect:/product/list.do";
	}
	@RequestMapping("/product/xiajia.do")
	public String xiajia(Integer[] productId,String name,Integer brandId,Integer isShow,Integer pageNo,ModelMap model){
		Product product=new Product();
		for (Integer i : productId) {
			product.setId(i);
			product.setIsShow(0);
			productServiceImpl.updateProductByKey(product);
		}
		if(StringUtils.isNotBlank(name)){
			model.addAttribute("name", name);
		}
		if(brandId!=null){
			model.addAttribute("brandId", brandId);
		}
		if(isShow==null){
			model.addAttribute("isShow", 0);
		}else{
			model.addAttribute("isShow", isShow);
		}
		model.addAttribute("pageNo", pageNo);
		return "redirect:/product/list.do";
	}
	@RequestMapping("/product/addUI.do")
	public String addUI(ModelMap model){
		//商品类型 
		TypeQuery typeQuery=new TypeQuery();
		typeQuery.setFields("id,name");
		typeQuery.setIsDisplay(1);
		List<Type> types = typeService.getTypeList(typeQuery);
		model.addAttribute("types", types);
		//   品牌
		BrandQuery brandQuery=new BrandQuery();
		brandQuery.setFields("id,name");
		brandQuery.setIsDisplay(1);
		List<Brand> brands = brandServiceImpl.getBrandList(brandQuery);
		model.addAttribute("brands", brands);
		//材质
		FeatureQuery featureQuery=new FeatureQuery();
		featureQuery.setFields("id,name");
		List<Feature> features = featureService.getFeatureList(featureQuery);
		model.addAttribute("features", features);
		//颜色
		ColorQuery colorQuery=new ColorQuery();
		colorQuery.setFields("id,name");
		colorQuery.setParentId(0);
		List<Color> colors = colorService.getColorList(colorQuery);
		model.addAttribute("colors", colors);
		return "product/add";
	}
	@RequestMapping("/product/add.do")
	public String add(Product product,Img img){
		product.setImg(img);
		productServiceImpl.addProduct(product);
		return "redirect:/product/list.do";
	}
	@RequestMapping("/product/upload.do")
	public void upload(@RequestParam(required=false) MultipartFile pic,HttpServletRequest request,HttpServletResponse response) throws Exception{
		/*MultipartHttpServletRequest mh=(MultipartHttpServletRequest) request;
		CommonsMultipartFile cm = (CommonsMultipartFile) mh.getFile(fileName);*/
		byte[] bytes = pic.getBytes();
		SimpleDateFormat  sd=new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");//日期时间毫秒
		String s1 = sd.format(new Date()).toString();
		String s2=FilenameUtils.getExtension(pic.getOriginalFilename());//获得扩展名,只是扩展名没有点.
		String path = request.getRealPath("/productupload");
		File f=new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		FileOutputStream out2=new FileOutputStream(new File(path,s1+"."+s2));
		out2.write(bytes);
		out2.close();
		String relativePath="/productupload/"+s1+"."+s2;
		JSONObject js=new JSONObject();
		js.put("relativePath", relativePath);
		ResponseUtils.renderText(response, js.toString());
	}
	@RequestMapping("/product/fckupload.do")
	public void uploadfck(HttpServletRequest request,HttpServletResponse response) throws Exception{
		MultipartHttpServletRequest mh=(MultipartHttpServletRequest) request;
		Map<String, MultipartFile> map = mh.getFileMap();
		Set<String> keys = map.keySet();
		String key = keys.iterator().next();
		MultipartFile mf = map.get(key);
		
		SimpleDateFormat  sd=new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");//日期时间毫秒
		String s1 = sd.format(new Date()).toString();
		
		String s2=FilenameUtils.getExtension(mf.getOriginalFilename());//获得扩展名,只是扩展名没有点.
		String path = request.getRealPath("/fckupload");
		File f=new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		FileOutputStream out2=new FileOutputStream(new File(path,s1+"."+s2));
		out2.write(mf.getBytes());
		out2.close();
		String relativePath="/fckupload/"+s1+"."+s2;
		UploadResponse ur=UploadResponse.getOK(relativePath);
		response.getWriter().print(ur);
	}
	
	@RequestMapping("/product/delete.do")
	public String delete(Integer[] productId,String name,Integer brandId,Integer isShow,Integer pageNo,ModelMap model) throws Exception{
		List<Integer> idList=new ArrayList<Integer>();
		for (Integer pid : productId) {
			idList.add(pid);
		}
		productServiceImpl.deleteByKeys(idList);
		if(StringUtils.isNotBlank(name)){
			model.addAttribute("name", name);
		}
		if(brandId!=null){
			model.addAttribute("brandId", brandId);
		}
		if(isShow==null){
			model.addAttribute("isShow", 0);
		}else{
			model.addAttribute("isShow", isShow);
		}
		model.addAttribute("pageNo", pageNo);
		return "redirect:/product/list.do";
	}
}
