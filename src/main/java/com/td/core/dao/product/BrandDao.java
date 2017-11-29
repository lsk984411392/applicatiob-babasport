package com.td.core.dao.product;

import java.util.List;

import com.td.core.bean.product.Brand;
import com.td.core.query.product.BrandQuery;

public interface BrandDao {
	//查询所有品牌
	public int getTotal(BrandQuery brandQuery);
	//public List<Brand> getBrandListWithPage(BrandQuery brandQuery);
	public List<Brand> getBrandList(BrandQuery brandQuery);
	public void add(Brand brand);
	public void delete(Integer id);
	public Brand findBrandById(Integer id);
	public void update(Brand brand);
	public void deleteBypiliang(Integer[] ids);
	
	
}
