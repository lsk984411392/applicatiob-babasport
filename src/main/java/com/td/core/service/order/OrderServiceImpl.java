package com.td.core.service.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;

import com.td.core.bean.CartItem;
import com.td.core.bean.order.Detail;
import com.td.core.bean.order.Order;
import com.td.core.bean.product.Sku;
import com.td.core.dao.order.OrderDao;
import com.td.core.query.order.DetailQuery;
import com.td.core.query.order.OrderQuery;
import com.td.core.service.product.SkuService;
/**
 * 订单主
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Resource
	OrderDao orderDao;
	@Resource
	private DetailService detailService;
	@Resource
	private SkuService skuService;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addOrder(Order order) {
		Integer i = orderDao.addOrder(order);//添加一个订单
		for (CartItem item : order.getBuyCart().getItems()) {//添加多个订单项
			Detail d=new Detail();
			d.setOrderId(order.getId());
			d.setProductNo(item.getSku().getProduct().getNo());
			d.setProductName(item.getSku().getProduct().getName());
			d.setColor(item.getSku().getColor().getName());
			d.setSize(item.getSku().getSize());
			d.setSkuPrice(item.getSku().getSkuPrice());
			d.setAmount(item.getAmount());
			detailService.addDetail(d);
			
			Sku sku = skuService.getSkuByKey(item.getSku().getId());//当订单添加成功，订单中的商品再库存中要  减
			sku.setStockInventory(sku.getStockInventory()-item.getAmount());
			skuService.updateSkuByKey(sku);
		}
		
		return i;
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Order getOrderByKey(Integer id) {
		return orderDao.getOrderByKey(id);
	}
	
	@Transactional(readOnly = true)
	public List<Order> getOrdersByKeys(List<Integer> idList) {
		return orderDao.getOrdersByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		
		DetailQuery detailQuery=new DetailQuery();
		detailQuery.setOrderId(id);
		List<Detail> details = detailService.getDetailList(detailQuery);
		for (Detail detail : details) {
			detailService.deleteByKey(detail.getId());
		}
		Integer i = orderDao.deleteByKey(id);
		return i;
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return orderDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateOrderByKey(Order order) {
		return orderDao.updateOrderByKey(order);
	}
	
	@Transactional(readOnly = true)
	public Pagination getOrderListWithPage(OrderQuery orderQuery) {
		Pagination p = new Pagination(orderQuery.getPageNo(),orderQuery.getPageSize(),orderDao.getOrderListCount(orderQuery));
		List<Order> orders = orderDao.getOrderListWithPage(orderQuery);
		for (Order order : orders) {
			DetailQuery detailQuery=new DetailQuery();
			detailQuery.setOrderId(order.getId());
			List<Detail> detailList = detailService.getDetailList(detailQuery);
			order.setDetails(detailList);
		}
		p.setList(orderDao.getOrderListWithPage(orderQuery));
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Order> getOrderList(OrderQuery orderQuery) {
		return orderDao.getOrderList(orderQuery);
	}
}
