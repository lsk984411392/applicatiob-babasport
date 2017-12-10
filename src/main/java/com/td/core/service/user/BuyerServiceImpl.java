package com.td.core.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;

import com.td.core.bean.country.City;
import com.td.core.bean.country.Province;
import com.td.core.bean.country.Town;
import com.td.core.bean.user.Addr;
import com.td.core.bean.user.Buyer;
import com.td.core.dao.user.BuyerDao;
import com.td.core.query.user.AddrQuery;
import com.td.core.query.user.BuyerQuery;
import com.td.core.service.country.CityService;
import com.td.core.service.country.ProvinceService;
import com.td.core.service.country.TownService;
/**
 * 购买者
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class BuyerServiceImpl implements BuyerService {

	@Resource
	BuyerDao buyerDao;
	@Resource
	private AddrService addrService;
	@Resource
	private ProvinceService provinceService;
	@Resource
	private CityService cityService;
	@Resource
	private TownService townService;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addBuyer(Buyer buyer,String phone) {
		Integer i = buyerDao.addBuyer(buyer);
		Addr addr=new Addr();
		addr.setBuyerId(buyer.getUsername());
		addr.setName(buyer.getRealName());
		addr.setCity(provinceService.getProvinceByCode(buyer.getProvince()).getName()
				+cityService.getCityByCode(buyer.getCity()).getName()
				+townService.getTownByCode(buyer.getTown()).getName());
		addr.setAddr(buyer.getAddr());
		addr.setPhone(phone);
		addr.setIsDef(1);
		addrService.addAddr(addr);
		return i;
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Buyer getBuyerByKey(String id) {
		return buyerDao.getBuyerByKey(id);
	}
	
	@Transactional(readOnly = true)
	public List<Buyer> getBuyersByKeys(List<String> idList) {
		return buyerDao.getBuyersByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(String id) {
		return buyerDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<String> idList) {
		return buyerDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateBuyerByKey(Buyer buyer) {
		Integer i = buyerDao.updateBuyerByKey(buyer);
		AddrQuery addrQuery=new AddrQuery();
		addrQuery.setBuyerId(buyer.getUsername());
		addrQuery.setIsDef(1);
		List<Addr> addrs = addrService.getAddrList(addrQuery);
		Addr addr = addrs.get(0);
		addr.setName(buyer.getRealName());
			Province province = provinceService.getProvinceByCode(buyer.getProvince());
			City city =cityService.getCityByCode(buyer.getCity()); 
			Town town = townService.getTownByCode(buyer.getTown());
		addr.setCity(province.getName()+city.getName());
		addr.setAddr(town.getName()+buyer.getAddr());
		addrService.updateAddrByKey(addr);
		return i;
		
	}
	
	@Transactional(readOnly = true)
	public Pagination getBuyerListWithPage(BuyerQuery buyerQuery) {
		Pagination p = new Pagination(buyerQuery.getPageNo(),buyerQuery.getPageSize(),buyerDao.getBuyerListCount(buyerQuery));
		p.setList(buyerDao.getBuyerListWithPage(buyerQuery));
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Buyer> getBuyerList(BuyerQuery buyerQuery) {
		return buyerDao.getBuyerList(buyerQuery);
	}
}
