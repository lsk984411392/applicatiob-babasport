package com.td.core.service.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;

import com.td.core.bean.product.Brand;
import com.td.core.dao.product.BrandDao;
import com.td.core.query.product.BrandQuery;

@Service
@Transactional
public class BrandServiceImpl {
	@Resource
	private BrandDao brandDao;
	//查询所有品牌记录数
	@Transactional(readOnly=true)
	public int getTotal(BrandQuery brandQuery) {
		return brandDao.getTotal(brandQuery);
	}
	@Transactional(readOnly=true)
	public Pagination getBrandListWithPage(BrandQuery brandQuery) {
		//1:起始页  startRow = (pageNo - 1)*pageSize
		//2:每页数
		//3:总记录数
		Pagination  pagination = new Pagination(brandQuery.getPageNo(),brandQuery.getPageSize(),brandDao.getTotal(brandQuery));
		//Brand集合
		pagination.setList(brandDao.getBrandList(brandQuery));
		return pagination;
	}
	public void add(Brand brand) {
		 brandDao.add(brand);
	}
	public void delete(Integer id) {
		brandDao.delete(id);
		
	}
	public Brand findBandById(Integer id) {
		return brandDao.findBrandById(id);
		
	}
	public void update(Brand brand) {
		brandDao.update(brand);
		
	}
	public void delete(Integer[] ids) {
		brandDao.deleteBypiliang(ids);
		
	}
	public List<Brand> getBrandList(BrandQuery brandQuery) {
		return brandDao.getBrandList(brandQuery);
		
	}

}
