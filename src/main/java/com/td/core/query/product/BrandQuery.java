package com.td.core.query.product;

import java.util.ArrayList;
import java.util.List;

/**
 * 品牌  查询专用对象
 * @author lx
 *
 */
public class BrandQuery {

	
	private Integer id;
	private String name;
	private String description;
	private String imgUrl;
	private Integer sort;
	private Integer isDisplay;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	/***********查询字段指定*************************************/
	private String fields;//只能查一个   name 什么的
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	/***********查询字段Like*************************************/
	private boolean nameLike;//是否开启 名字的  模糊查询
	public boolean isNameLike() {
		return nameLike;
	}
	public void setNameLike(boolean nameLike) {
		this.nameLike = nameLike;
	}
	
	/***********order by *************************************/
	public class OrderField{//多个字段   排序    id desc,name asc,imgUrl asc 
		private String field;  //id , name  imgUrl
		private String order;  // desc asc
		
		public OrderField(String field, String order) {
			super();
			this.field = field;
			this.order = order;
		}
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}
		
	}
	/**
	 * ==============================批量查询时的Order条件顺序设置==========================
	 * ========
	 **/
	/** 排序列表字段 **/
	private List<OrderField> orderFields = new ArrayList<OrderField>();
	/**
	 * 设置排序按属性：id
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public BrandQuery orderbyId(boolean isAsc) {
		orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：name
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public BrandQuery orderbyName(boolean isAsc) {
		orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：description
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public BrandQuery orderbyDescription(boolean isAsc) {
		orderFields.add(new OrderField("description", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：img_url
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public BrandQuery orderbyImgUrl(boolean isAsc) {
		orderFields.add(new OrderField("img_url", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：web_site
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public BrandQuery orderbyWebSite(boolean isAsc) {
		orderFields.add(new OrderField("web_site", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：sort
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public BrandQuery orderbySort(boolean isAsc) {
		orderFields.add(new OrderField("sort", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：is_display
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public BrandQuery orderbyIsDisplay(boolean isAsc) {
		orderFields.add(new OrderField("is_display", isAsc ? "ASC" : "DESC"));
		return this;
	}
	
	
	public List<OrderField> getOrderFields() {
		return orderFields;
	}
	public void setOrderFields(List<OrderField> orderFields) {
		this.orderFields = orderFields;
	}
	//页号
	private Integer pageNo = 1;//int///根据 是否给pageNo或者pageSize赋值来确定是否开启分页，
	//开始行
	private Integer startRow;//null
	//每页数
	private Integer pageSize = 5;
	
	
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		//计算一次开始行
		this.startRow = (pageNo - 1)*pageSize;
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		//计算一次开始行
		this.startRow = (pageNo - 1)*pageSize;
		this.pageNo = pageNo;
	}
	
	
}
