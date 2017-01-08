package com.yuyoukj.model.qhd;

import java.io.Serializable;
import java.math.BigDecimal;

public class Infotrade implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long tradeid; // ; 1; 20;
	private String itemids; // ; 0; 60;
	private String userids; // 需求方用户id; 0; 60;
	private Long buyuserid; // 应答方用户id; 0; 20;
	private Long paydate; // 应答方到帐时间; 0; 20;
	private Long createdate; // ; 0; 20;
	private Long updatedate; // ; 0; 20;
	private Integer sstatus;
	private BigDecimal cost;
	private BigDecimal totalcost;
	private String failure_msg;
	private Long failuredate;
	private String transfer;

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

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(BigDecimal totalcost) {
		this.totalcost = totalcost;
	}

	public Integer getSstatus() {
		return sstatus;
	}

	public void setSstatus(Integer sstatus) {
		this.sstatus = sstatus;
	}

	public Long getTradeid() {
		return tradeid;
	}

	public void setTradeid(Long tradeid) {
		this.tradeid = tradeid;
	}

	public String getItemids() {
		return itemids;
	}

	public void setItemids(String itemids) {
		this.itemids = itemids;
	}

	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public Long getBuyuserid() {
		return buyuserid;
	}

	public void setBuyuserid(Long buyuserid) {
		this.buyuserid = buyuserid;
	}

	public Long getPaydate() {
		return paydate;
	}

	public void setPaydate(Long paydate) {
		this.paydate = paydate;
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
