package com.td.core.service.product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;

import com.td.core.bean.product.Img;
import com.td.core.bean.product.Product;
import com.td.core.bean.product.Sku;
import com.td.core.dao.product.ProductDao;
import com.td.core.query.product.ImgQuery;
import com.td.core.query.product.ProductQuery;
import com.td.core.query.product.SkuQuery;
/**
 * 商品事务层
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ImgService imgService;
	@Autowired
	private SkuService skuService;
	@Resource
	private ProductDao productDao;
	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addProduct(Product product) {
		//商品编号
		DateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");
		String no = df.format(new Date());
		product.setNo(no);
		//添加时间
		product.setCreateTime(new Date());
		//影响到行数   返回商品ID
		//商品保存  
		Integer i = productDao.addProduct(product);
		//1:商品   图片   sku
		//2:图片
		//1)设置图片商品ID
		product.getImg().setProductId(product.getId());//获得商品id，要在productDao.xml中添加后获得主键id值才可以
		//2)
		product.getImg().setIsDef(1);
		imgService.addImg(product.getImg());
		//3:保存Sku    9,13,...
		//  S M  ...
		//实例化一个Sku对象
		Sku sku = new Sku();
		//商品ID
		sku.setProductId(product.getId());
		//运费
		sku.setDeliveFee(10.00);
		//售价
		sku.setSkuPrice(0.00);
		//市场价
		sku.setMarketPrice(0.00);
		//库存
		sku.setStockInventory(0);
		//购买限制
		sku.setSkuUpperLimit(0);
		//添加时间
		sku.setCreateTime(new Date());
		//是否最新
		sku.setLastStatus(1);
		//商品
		sku.setSkuType(1);
		//销量
		sku.setSales(0);
		if(product.getColor()!=null&&product.getSize()!=null){
			for(String color : product.getColor().split(",")){
				//颜色ID
				sku.setColorId(Integer.parseInt(color));
				for(String size : product.getSize().split(",")){
					//尺码
					sku.setSize(size);
					//保存SKu
					skuService.addSku(sku);
				}
			}
		}
				
		return i;
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Product getProductByKey(Integer id) {
		 Product p = productDao.getProductByKey(id);
		 ImgQuery imgQuery=new ImgQuery();
		 imgQuery.setProductId(p.getId());
		 List<Img> imgList = imgService.getImgList(imgQuery);
		 p.setImg(imgList.get(0));
		 return p;
	}
	
	@Transactional(readOnly = true)
	public List<Product> getProductsByKeys(List<Integer> idList) {
		return productDao.getProductsByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return productDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		for (Integer integer : idList) {
			ImgQuery imgQuery=new ImgQuery();
			imgQuery.setProductId(integer);
			List<Img> imgs = imgService.getImgList(imgQuery);
			if(imgs!=null&&imgs.size()>0){
				imgService.deleteByKey(imgs.get(0).getId());
			}
			
			SkuQuery skuQuery=new SkuQuery();
			skuQuery.setProductId(integer);
			List<Sku> skus = skuService.getSkuList(skuQuery);
			if(skus!=null&&skus.size()>0){
				for (Sku sku : skus) {
					skuService.deleteByKey(sku.getId());
				}
			}
		}
		Integer i = productDao.deleteByKeys(idList);
		return i;
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateProductByKey(Product product) {
		return productDao.updateProductByKey(product);
	}
	
	@Transactional(readOnly = true)
	public Pagination getProductListWithPage(ProductQuery productQuery) {
		Pagination p = new Pagination(productQuery.getPageNo(),productQuery.getPageSize(),productDao.getProductListCount(productQuery));
		List<Product> products = productDao.getProductListWithPage(productQuery);
		for (Product product : products) {
			ImgQuery imgQuery=new ImgQuery();
			imgQuery.setProductId(product.getId());
			imgQuery.setIsDef(1);
			List<Img> list = imgService.getImgList(imgQuery);
			if(list!=null&&list.size()>0){
				product.setImg(list.get(0));
				
			}
		}
		p.setList(products);
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Product> getProductList(ProductQuery productQuery) {
		return productDao.getProductList(productQuery);
	}
}
