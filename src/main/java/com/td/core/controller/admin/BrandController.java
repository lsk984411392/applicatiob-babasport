package com.td.core.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.itcast.common.page.Pagination;

import com.td.common.web.ResponseUtils;
import com.td.core.bean.product.Brand;
import com.td.core.bean.product.Product;
import com.td.core.query.product.BrandQuery;
import com.td.core.query.product.ProductQuery;
import com.td.core.service.product.BrandServiceImpl;
import com.td.core.service.product.ProductService;



@Controller
public class BrandController {
	@Autowired
	private BrandServiceImpl brandServiceImpl;
	@Autowired
	private ProductService productService;
		/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping("/brand/list.do")
	public String list(String name,Integer isDisplay,Integer pageNo,String message,ModelMap model){
		StringBuilder params = new StringBuilder();
		BrandQuery brandQuery = new BrandQuery();
		//判断传进来的名称是否为Null并且还要判断 是否为空串   blank  ""  "   "   emtpy  ""   "  "
		if(StringUtils.isNotBlank(name)){
			brandQuery.setName(name);
			params.append("name=").append(name);
			model.addAttribute("name", name);//request.setAttribute
		}
		//是  不是
		if(null != isDisplay){
			brandQuery.setIsDisplay(isDisplay);
			params.append("&").append("isDisplay=").append(isDisplay);
			model.addAttribute("isDisplay", isDisplay);//request.setAttribute
		}
		brandQuery.setPageNo(Pagination.cpn(pageNo));//页号,当页号小于等于1或者null，自定设置为1，并得到startRow属性值
		brandQuery.setNameLike(true);//设置名字模糊查询为true
		brandQuery.orderbyId(false);//设置排序id排序 为  倒序
		//分页对象
		Pagination pagination = brandServiceImpl.getBrandListWithPage(brandQuery);
		//分页展示   /brand/list.do?name=瑜伽树（yogatree）&isDisplay=1&pageNo=2
		String url = "/brand/list.do";
		pagination.pageView(url, params.toString());
		model.addAttribute("pagination", pagination);//request.setAttribute
		
		
		if(StringUtils.isNotBlank(message)){
			model.addAttribute("message", message);
		}
		return "brand/list";
	}
	@RequestMapping("/brand/addUI.do")
	public String addUI(){
		return "brand/add";
	}
	@RequestMapping("/brand/upload.do")
	public void upload(String fileName,HttpServletRequest request,HttpServletResponse response) throws Exception{
		MultipartHttpServletRequest mh=(MultipartHttpServletRequest) request;
		CommonsMultipartFile cm = (CommonsMultipartFile) mh.getFile(fileName);
		byte[] bytes = cm.getBytes();
		SimpleDateFormat  sd=new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");//日期时间毫秒
		String s1 = sd.format(new Date()).toString();
		/*String originalFilename = cm.getOriginalFilename();
		String s2 = originalFilename.substring(originalFilename.lastIndexOf("."));*/
		String s2=FilenameUtils.getExtension(cm.getOriginalFilename());//获得扩展名,只是扩展名没有点.
		String path = request.getRealPath("/brandupload");
		File f=new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		FileOutputStream out2=new FileOutputStream(new File(path,s1+"."+s2));
		out2.write(bytes);
		out2.close();
		/*String fullPath ="http://localhost:8080/upload/"+s1+s2;
		String relativePath="/upload/"+s1+s2;
		//{"":"","":""}
		String result="{\"fullPath\":\""+fullPath+"\",\"relativePath\":\""+relativePath+"\"}";
		System.out.println(fullPath);
		System.out.println(relativePath);*/
		String relativePath="/brandupload/"+s1+"."+s2;
		JSONObject js=new JSONObject();
		js.put("relativePath", relativePath);
		ResponseUtils.renderText(response, js.toString());
	}
	@RequestMapping("/brand/add.do")
	public String add(Brand brand){
		brandServiceImpl.add(brand);
		return "redirect:/brand/list.do";
	}
	@RequestMapping("/brand/editUI.do")
	public String editUI(Integer id,ModelMap model){
		Brand brand = brandServiceImpl.getBandById(id);
		model.addAttribute("brand", brand);
		return "brand/edit";
	}
	@RequestMapping("/brand/edit.do")
	public String edit(Brand brand){
		brandServiceImpl.update(brand);
		return "redirect:/brand/list.do";
	}
	@RequestMapping("/brand/delete.do")
	public String delete(Integer id,String name,Integer isDisplay,Integer pageNo,ModelMap model){//当多条件查询时删除，条件还在。
		if(StringUtils.isNotBlank(name)){
			model.addAttribute("name", name);
		}
		if(null != isDisplay){
			model.addAttribute("isDisplay", isDisplay);
		}
		model.addAttribute("pageNo", pageNo);
		Integer i = brandServiceImpl.delete(id);
		
		if(i!=null&&i==0){
			model.addAttribute("message", "删除失败，请先删除品牌对应的  商品 。。。。");
		}
		return "redirect:/brand/list.do";//之所以不用这"redirect:/brand/list.do?name="+name+"&isDisplay="+isDisplay,，这样会导致中文乱码，所以采用model
	}
	@RequestMapping("/brand/deletes.do")
	public String deletes(Integer[] ids,String name,Integer isDisplay,Integer pageNo,ModelMap model){//当多条件查询时删除，条件还在。
		for (Integer integer : ids) {
			brandServiceImpl.delete(integer);
		}
		if(StringUtils.isNotBlank(name)){
			model.addAttribute("name", name);
		}
		if(null != isDisplay){
			model.addAttribute("isDisplay", isDisplay);
		}
		model.addAttribute("pageNo", pageNo);
		return "redirect:/brand/list.do";//之所以不用这"redirect:/brand/list.do?name="+name+"&isDisplay="+isDisplay,，这样会导致中文乱码，所以采用model
	}


	
	
	
	
	
	
	
	
	
}
