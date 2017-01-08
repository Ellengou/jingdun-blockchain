package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Confconfig implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long seq; // ; 1; 20;
	private String ckey; // key; 0; 50;
	private String cvalue; // value; 0; 100;
	private String ctbname; // value; 0; 100;
	private String cfield; // value; 0; 100;
	private Integer cflag; // 是否有效：0-有效；1-无效；; 0; 20;
	private String remark; // 备注; 0; 100;
	private Long createdate; // ; 0; 20;
	private Long updatedate; // ; 0; 20;

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getCkey() {
		return ckey;
	}

	public void setCkey(String ckey) {
		this.ckey = ckey;
	}

	public String getCvalue() {
		return cvalue;
	}

	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}

	public String getCtbname() {
		return ctbname;
	}

	public void setCtbname(String ctbname) {
		this.ctbname = ctbname;
	}

	public String getCfield() {
		return cfield;
	}

	public void setCfield(String cfield) {
		this.cfield = cfield;
	}

	public Integer getCflag() {
		return cflag;
	}

	public void setCflag(Integer cflag) {
		this.cflag = cflag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
