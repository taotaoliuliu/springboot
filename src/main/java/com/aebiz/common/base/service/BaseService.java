package com.aebiz.common.base.service;

import java.util.List;
import java.util.Map;

import com.aebiz.common.base.model.BaseModel;
import com.github.pagehelper.PageInfo;





/**
 * 所有服务接口都要继承这个
 */
public interface BaseService<M extends BaseModel> {
	M getModel();

	
	/**
	 * 返回分页后的数据
	 * @param pageView T t
	 * @param t
	 * @return PageView
	 */
	public PageInfo<M> getPageList(PageInfo<M> pageView,M t);
	
	
	
	/**
	 * 返回所有数据
	 * @param t
	 * @return List<T>
	 */
	public List<M> getByCondition(M t);

	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	public void delete(String uuid);

	/**
	 * 更新数据
	 * @param t
	 * @throws Exception
	 */
	public void update(M t);

	/**
	 * 返回某一条数据
	 * @param id
	 * @return T
	 */
	public M getById(String id);

	/**
	 * 保存数据
	 * @param t
	 * @throws Exception
	 */
	public void add(M t);
	
	/**
	 * 批量删除
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String[] uuids);
	
	public Map<String,M> getByUuids(List<String> uuids);

	
	
}
