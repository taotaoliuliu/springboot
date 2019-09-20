package com.aebiz.common.base.cotroller;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aebiz.common.base.model.BaseModel;
import com.aebiz.common.base.service.BaseService;
import com.aebiz.common.bean.Result;
import com.aebiz.entity.Ad;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;

public class BaseController<T  extends BaseModel> {

	private Class<T> clazz;

	private BaseService<T> myService;
	
	private String templatePath;
	
	
	public void init(BaseService myService,String templatePath){
		this.templatePath=templatePath;
		this.myService=myService;
	}

    @RequestMapping(value ="toList")
	public String toListPage(){
		
		return templatePath+"/list";
	}
    
    
    @RequestMapping(value ="toAdd")
	public String toAdd(){
		
		return templatePath+"/add";
	}
    
    
    

	/**
	 * 返回某一条数据
	 * 
	 * @param id
	 * @return T
	 */
	@ResponseBody
    @RequestMapping(value = "/getByUuid")
	public T getByUuid(String uuid) {
		return myService.getById(uuid);
	}

	/**
	 * 保存数据
	 * 
	 * @param t
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/add")
	public Result  add(T t) {
		Result result=new Result();
		result.setRetStatus(Result.SUCCESS);
		myService.add(t);
		return result;
	}
	
	
	
	/**
	 * 修改
	 * @param t
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/update")
	public Result  update(T t) {
		Result result=new Result();
		result.setRetStatus(Result.SUCCESS);
		myService.update(t);
		return result;
	}
	
	
	
	/**
	 * 删除
	 * @param t
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/deleteByUuid")
	public Result  deleteByUuid(String uuid) {
		Result result=new Result();
		result.setRetStatus(Result.SUCCESS);
		myService.delete(uuid);
		return result;
	}
	

	
	/**
	 * 获取分页数据
	 * @param pageNum
	 * @param pageSize
	 * @param t
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@ResponseBody
	@RequestMapping(value = "/getListPage")
	public Map<String, Object> getListPage(@RequestParam(value = "pageNum") Integer pageNum,
			@RequestParam(value = "pageSize") Integer pageSize, T t)
			throws InstantiationException, IllegalAccessException {
		PageInfo info = new PageInfo<>();
		info.setPageNum(pageNum);
		info.setPageSize(pageSize);

		PageInfo<T> page = myService.getPageList(info, t);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("totalSize", page.getTotal());
		map.put("totalPage", page.getPages());
		return map;

	}
	
	
		public Class getRealType(){
			// 获取当前new的对象的泛型的父类类型
			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
			// 获取第一个类型参数的真实类型
			this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
			return clazz;
		}
	
	
	/**T
	 * 获取所有数据 list
	 */
	@ResponseBody
	@RequestMapping(value = "/getAll")
	public List<T> getAll(T t){
		List<T> list = myService.getByCondition(t);
		return list;
	}
	
	
	

	


	public void setMyService(BaseService<T> myService) {
		this.myService = myService;
	}


	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	
	
}
