package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Confarea implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // ; 1; 20;
	private Integer areaid; // 地区id; 0; 20;
	private String areaname; // 地区名; 0; 60;
	private Integer fatherid; // 上一级辖区id; 0; 20;
	private Integer orderby; // 排序号; 0; 20;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAreaid() {
		return areaid;
	}

	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public Integer getFatherid() {
		return fatherid;
	}

	public void setFatherid(Integer fatherid) {
		this.fatherid = fatherid;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}
}
