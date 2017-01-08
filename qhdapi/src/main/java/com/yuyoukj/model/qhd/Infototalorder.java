package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Infototalorder implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long seq; // ; 1; 20;
	private Long totalid; // 第三方支付id; 0; 20;
	private Long tradeid; // 交易id; 0; 20;
	private Long itemid;
	private Long createdate; // ; 0; 20;
	private Long updatedate; // ; 0; 20;
	private Integer stype; // 类型：0-itemid；1-tradeid；; 0; 20;

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getTotalid() {
		return totalid;
	}

	public void setTotalid(Long totalid) {
		this.totalid = totalid;
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

	public Integer getStype() {
		return stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}
}
