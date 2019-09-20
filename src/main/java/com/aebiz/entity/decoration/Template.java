package com.aebiz.entity.decoration;

import java.io.Serializable;

import com.aebiz.common.base.model.BaseModel;
import com.aebiz.common.exception.MalciousException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户实体类
 * @date 
 */
@ApiModel(value = "模板shiiiiii体类")
public class Template extends BaseModel{
		
	@ApiModelProperty(value = "模板名称")
	private String templateName;
	
	private String note;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	
	
}
