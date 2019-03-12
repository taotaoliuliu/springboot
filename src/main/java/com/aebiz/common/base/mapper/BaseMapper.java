package com.aebiz.common.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aebiz.common.base.model.BaseModel;


/**
 * 所有的Mapper继承这个接口
 	lsl
 */
public interface BaseMapper<T extends BaseModel> {
	/**
	 * 返回所有数据
	 * @param t
	 * @return List<T>
	 */
	public List<T> getByCondition(T t);

	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id);

	/**
	 * 更新数据
	 * @param t
	 * @throws Exception
	 */
	public void update(T t);

	/**
	 * 返回某一条数据
	 * @param id
	 * @return T
	 */
	public T getById(String id);

	/**
	 * 保存数据
	 * @param t
	 * @throws Exception
	 */
	public void add(T t);
	
	
	
	public List<T> getByUuids(@Param("uuids")List<String> uuids);
	
}
