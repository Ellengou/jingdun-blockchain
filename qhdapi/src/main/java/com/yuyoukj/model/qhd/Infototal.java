package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Infototal implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long totalid; // ; 1; 20;
	private Integer stype; // 支付类型：0-alipay; 1-paypal;; 0; 20;
	private Long askdate; // 订单生成时间; 0; 20;
	private Long paydate; // 到帐时间; 0; 20;
	private Integer sflag; // 0-未处理；1-已处理；; 0; 20;
	private String tradeno; // 第三方反馈的交易号; 0; 50;
	private Long createdate; // ; 0; 20;
	private Long updatedate; // ; 0; 20;

	public Long getTotalid() {
		return totalid;
	}

	public void setTotalid(Long totalid) {
		this.totalid = totalid;
	}

	public Integer getStype() {
		return stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}

	public Long getAskdate() {
		return askdate;
	}

	public void setAskdate(Long askdate) {
		this.askdate = askdate;
	}

	public Long getPaydate() {
		return paydate;
	}

	public void setPaydate(Long paydate) {
		this.paydate = paydate;
	}

	public Integer getSflag() {
		return sflag;
	}

	public void setSflag(Integer sflag) {
		this.sflag = sflag;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
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
