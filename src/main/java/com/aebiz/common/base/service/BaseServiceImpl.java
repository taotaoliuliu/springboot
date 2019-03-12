package com.aebiz.common.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.aebiz.common.base.mapper.BaseMapper;
import com.aebiz.common.base.model.BaseModel;
import com.aebiz.common.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



/**
 * 服务实现的基类,封装基本的增，删，改。查的方法 所有服务实现类都要继承这个,然后写自己的具体实现方法,
 * 如果只有简单的增，删，改。查业务，继承这个实现类基类时,子类什么都不写，在控制直接调用父类方法即可，
 * 写法：具体可以参照LogServiceImpl

 */
//@Service
public class BaseServiceImpl<M extends BaseModel>  implements BaseService<M> {

	
	private BaseMapper<M> mapper;

	public void setMapper(BaseMapper<M> mapper) {
		this.mapper = mapper;
	}
	
	public M getModel(){
		return null;
	}

	public List<M> get(M t) {
		return mapper.getByCondition(t);
	}

	public void delete(String id) {
		mapper.delete(id);
	}

	public void update(M m) {
		mapper.update(m);
	}

	public M getById(String id) {
		return mapper.getById(id);
	}

	public void add(M t) {
		
		if (StringUtils.isEmpty(t.getUuid())) {
			t.setUuid(BaseModel.genUuid());
		}
		String curDateMime = DateUtil.getCurDateTime();
		t.setCreateTime(curDateMime);

		mapper.add(t);
	}

	public PageInfo<M> getPageList(PageInfo<M> pageView, M t) {
		PageHelper.startPage(pageView.getPageNum(), pageView.getPageSize());
		List<M> byCondition = this.getByCondition(t);
		PageInfo<M> page = new PageInfo<M>(byCondition);
		return page;
	}

	public void delete(String[] ids) {
		for (String id : ids) {
			mapper.delete(id);
		}
	}

	@Override
	public List<M> getByCondition(M t) {
		return mapper.getByCondition(t);

	}

	@Override
	public Map<String,M> getByUuids(List<String> uuids) {
		Map<String,M> map=new HashMap<String,M>();
		List<M> byUuids = mapper.getByUuids(uuids);
		
		for(M m:byUuids){
			map.put(m.getUuid(), m);
		}
		return map;
	}



}
