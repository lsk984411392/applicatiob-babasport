package com.td.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class BuyCart {
	private List<CartItem> items=new ArrayList<CartItem>();
	private Integer productId;
	
	
	public List<CartItem> getItems() {
		return items;
	}
	public void setItems(List<CartItem> items) {
		this.items = items;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public void add(CartItem item){
		if(items.contains(item)){
			for (CartItem i : items) {
				if(i.getSku().getId().intValue()==item.getSku().getId().intValue()){
					if(item.getAmount()+i.getAmount()> item.getSku().getSkuUpperLimit()){
						i.setAmount(item.getSku().getSkuUpperLimit());
					}else{
						i.setAmount(item.getAmount()+i.getAmount());
					}
				}
			}
		}else{
			items.add(item);
		}
	}
	
	
	public void delete(CartItem item){
		if(items.contains(item)){
			items.remove(item);
		}
	}
	public void clear(){
		items.clear();
	}
	
	
	@JsonIgnore
	public int getProductAmount(){
		int value=0;
		for (CartItem i : items) {
			value+=i.getAmount();
		}
		return value;
	}
	@JsonIgnore
	public Double getProductPrice(){
		Double v=0.00;
		for (CartItem i : items) {
			v+=i.getSku().getSkuPrice()*i.getAmount();
		}
		return v;
	}
	@JsonIgnore
	public Double getFee(){
		Double v=0.00;
		if(getProductPrice()<39){
			v=10.00;
		}
		return v;
	}
	@JsonIgnore
	public Double getTotalPrice(){
		Double v=0.0;
		v= getProductPrice()+getFee();
		return v;
	}
	@JsonIgnore
	public Boolean isEmpty(){
		Boolean v=true;
		if(items.size()>0){
			v=false;
		}
		return v;
		
	}
	@Override
	public String toString() {
		return "BuyCart [items=" + items + ", productId=" + productId + "]";
	}
	
}
