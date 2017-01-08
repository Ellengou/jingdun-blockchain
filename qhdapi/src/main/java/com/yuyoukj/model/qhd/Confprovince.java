package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Confprovince implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // ; 1; 20;
	private Integer provinceid; // 省id; 0; 20;
	private String provincename; // 省名称; 0; 40;
	private Integer orderby; // 排序号; 0; 20;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}
}
