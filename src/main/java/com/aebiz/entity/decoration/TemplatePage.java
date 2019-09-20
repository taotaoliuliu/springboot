package com.aebiz.entity.decoration;

import java.io.Serializable;

import com.aebiz.common.base.model.BaseModel;
import com.aebiz.common.exception.MalciousException;

/**
 * 用户实体类
 * @date 
 */
public class TemplatePage extends BaseModel{
		
	
	private String templateUuid;
	
	private String pageName;

	private String note;
	
	/**
	 * json 数据
	 */
	private String pageData;

	public String getTemplateUuid() {
		return templateUuid;
	}

	public void setTemplateUuid(String templateUuid) {
		this.templateUuid = templateUuid;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPageData() {
		return pageData;
	}

	public void setPageData(String pageData) {
		this.pageData = pageData;
	}
	
	
	
	
	
	
	
	
	
}
