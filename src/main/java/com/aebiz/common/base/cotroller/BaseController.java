package com.aebiz.common.base.cotroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aebiz.common.base.model.BaseModel;
import com.aebiz.common.base.service.BaseService;
import com.aebiz.common.bean.Result;
import com.aebiz.entity.Ad;
import com.github.pagehelper.PageInfo;

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

	


	public void setMyService(BaseService<T> myService) {
		this.myService = myService;
	}


	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	
	
}
