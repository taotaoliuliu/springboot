package com.aebiz.entity;

import java.util.List;

import javax.persistence.Transient;

import com.aebiz.common.base.model.BaseModel;

public class Menu extends BaseModel{
	
	private String powerid;
	
	private String powername;
	
	private String fatherid;
	
	private String powerurl;
	
	
	
	private String imgurl;
	
	private Long isbutton;
	
	private String powerkey;

	private Integer sno;
	
	private String level;
	
	
	
	
	@Transient
	private List<Menu> listChild;
	
	
	
	
	
	
	

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<Menu> getListChild() {
		return listChild;
	}

	public void setListChild(List<Menu> listChild) {
		this.listChild = listChild;
	}

	public String getPowerid() {
		return powerid;
	}

	public void setPowerid(String powerid) {
		this.powerid = powerid;
	}

	public String getPowername() {
		return powername;
	}

	public void setPowername(String powername) {
		this.powername = powername;
	}

	public String getFatherid() {
		return fatherid;
	}

	public void setFatherid(String fatherid) {
		this.fatherid = fatherid;
	}

	public String getPowerurl() {
		return powerurl;
	}

	public void setPowerurl(String powerurl) {
		this.powerurl = powerurl;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Long getIsbutton() {
		return isbutton;
	}

	public void setIsbutton(Long isbutton) {
		this.isbutton = isbutton;
	}

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}


	public String getPowerkey() {
		return powerkey;
	}

	public void setPowerkey(String powerkey) {
		this.powerkey = powerkey;
	}



	
	
	
	
	
	

}
