package com.aebiz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aebiz.common.base.service.BaseServiceImpl;
import com.aebiz.entity.product.ProductMain;
import com.aebiz.entity.product.SecKillProduct;
import com.aebiz.mapper.ProductMainMapper;
import com.aebiz.mapper.SecKillProductMapper;
import com.aebiz.service.ProductMainService;
import com.aebiz.service.SecKillProductService;
@Service
public class SecKillProductServiceImpl extends BaseServiceImpl<SecKillProduct> implements SecKillProductService{
	@Autowired
	private ProductMainService productMainService;
	
	private SecKillProductMapper mapper;
	@Autowired
	public void setMapper(SecKillProductMapper mapper) {
		this.mapper = mapper;
		super.setMapper(mapper);
	}
	
	
	@Override
	public SecKillProduct getById(String id) {
		
		SecKillProduct kill = super.getById(id);
		ProductMain main = productMainService.getById(kill.getProductUuid());
		if(main!=null){
			kill.setProductName(main.getName());
			kill.setShopPrice(main.getShopPrice());
		}
		return kill;
	}


	@Override
	public List<SecKillProduct> getByCondition(SecKillProduct t) {
		
		if(StringUtils.isNotEmpty(t.getProductName())){
			ProductMain main=new ProductMain();
			main.setName(t.getProductName());
			List<ProductMain> listMain = productMainService.getByCondition(main);
			
			if(CollectionUtils.isNotEmpty(listMain)){
				List<String> listUuids=new ArrayList<>();

				for(ProductMain m:listMain){
					listUuids.add(m.getUuid());
				}
				
				t.setListUuids(listUuids);
				
			}
			else {
				return new ArrayList<>();
			}
		}
		
		List<SecKillProduct> byCondition = super.getByCondition(t);
		
		
		if(CollectionUtils.isNotEmpty(byCondition)){
			
			List<String> listUuids=new ArrayList<>();
			for(SecKillProduct m: byCondition){
				listUuids.add((m.getProductUuid()));
			}
			
			Map<String, ProductMain> byUuids = productMainService.getByUuids(listUuids);
			
			for(SecKillProduct m: byCondition){
				m.setProductName(byUuids.get(m.getProductUuid()).getName());
				m.setShopPrice((byUuids.get(m.getProductUuid()).getShopPrice()));

			}
			
			
			
		}
		return byCondition;
	}
	
	
	
	
}
