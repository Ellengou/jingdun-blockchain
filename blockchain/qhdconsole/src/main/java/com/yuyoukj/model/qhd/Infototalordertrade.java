package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Infototalordertrade implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long seq; // ; 1; 20;
	private Long totalorderid; // 第三方支付id; 0; 20;
	private Long tradeid; // 交易id; 0; 20;
	private Long createdate; // ; 0; 20;
	private Long updatedate; // ; 0; 20;

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getTotalorderid() {
		return totalorderid;
	}

	public void setTotalorderid(Long totalorderid) {
		this.totalorderid = totalorderid;
	}

	public Long getTradeid() {
		return tradeid;
	}

	public void setTradeid(Long tradeid) {
		this.tradeid = tradeid;
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
