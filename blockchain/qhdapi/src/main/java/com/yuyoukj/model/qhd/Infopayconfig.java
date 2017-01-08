package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Infopayconfig implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long seq; // ; 1; 20;
	private Long userid; // ; 0; 20;
	private Integer stype; // 绑定类型：0-CNY；1-USD; 0; 20;
	private String carnum; // 账号号; 0; 50;
	private String sname; // 开户姓名; 0; 50;
	private Long createdate; // ; 0; 20;
	private Long updatedate; // ; 0; 20;

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Integer getStype() {
		return stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}

	public String getCarnum() {
		return carnum;
	}

	public void setCarnum(String carnum) {
		this.carnum = carnum;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public Long getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}

	public Long getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Long updatedate) {
		this.updatedate = updatedate;
	}
}
