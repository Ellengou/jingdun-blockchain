package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Infoitem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long itemid; // ; 1; 20;
	private Long userid; // ; 0; 20;
	private Integer stype; // 需求类型：0-CNY；1-USD; 0; 20;
	private java.math.BigDecimal money; // 需求金额; 0; 20;
	private Integer intype; // 支付费用类型：0-CNY；1-USD; 0; 20;
	private Double ratio; // 汇率; 0; 20;
	private java.math.BigDecimal cost; // 汇率金额; 0; 20;
	private java.math.BigDecimal totalcost; // 总金额（含手续费）; 0; 20;
	private Long paydate; // 需求方到帐时间; 0; 20;
	private Integer sflag; // 状态：0-正常；1-超时系统取消；2-用户主动取消；; 0; 20;
	private Integer sstatus; // 0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；; 0; 20;
	private Long createdate; // ; 0; 20;
	private Long updatedate; // ; 0; 20;
	private String failure_msg;
	private Long failuredate;
	private String transfer;

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public String getFailure_msg() {
		return failure_msg;
	}

	public void setFailure_msg(String failure_msg) {
		this.failure_msg = failure_msg;
	}

	public Long getFailuredate() {
		return failuredate;
	}

	public void setFailuredate(Long failuredate) {
		this.failuredate = failuredate;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
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

	public java.math.BigDecimal getMoney() {
		return money;
	}

	public void setMoney(java.math.BigDecimal money) {
		this.money = money;
	}

	public Integer getIntype() {
		return intype;
	}

	public void setIntype(Integer intype) {
		this.intype = intype;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public java.math.BigDecimal getCost() {
		return cost;
	}

	public void setCost(java.math.BigDecimal cost) {
		this.cost = cost;
	}

	public java.math.BigDecimal getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(java.math.BigDecimal totalcost) {
		this.totalcost = totalcost;
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

	public Integer getSstatus() {
		return sstatus;
	}

	public void setSstatus(Integer sstatus) {
		this.sstatus = sstatus;
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
