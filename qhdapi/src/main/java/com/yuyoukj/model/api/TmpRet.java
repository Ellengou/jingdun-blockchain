package com.yuyoukj.model.api;

import java.math.BigDecimal;
import java.util.List;
import com.pingplusplus.model.Charge;
import com.yuyoukj.model.qhd.Infoitem;
import com.yuyoukj.model.qhd.Infouser;

public class TmpRet {
	private Integer totalcount;
	private Integer star;
	private Integer size;
	private Integer totalpage;
	private Infouser userdata;
	private List<Infoitem> itemlist;
	private Integer sflag;
	private Charge chargedata;
	private Integer paystype;
	private Long payorder;
	private BigDecimal paycost;
	private List<Moneylimit> moneylist;

	public List<Moneylimit> getMoneylist() {
		return moneylist;
	}

	public void setMoneylist(List<Moneylimit> moneylist) {
		this.moneylist = moneylist;
	}

	public BigDecimal getPaycost() {
		return paycost;
	}

	public void setPaycost(BigDecimal paycost) {
		this.paycost = paycost;
	}

	public Long getPayorder() {
		return payorder;
	}

	public void setPayorder(Long payorder) {
		this.payorder = payorder;
	}

	public Integer getPaystype() {
		return paystype;
	}

	public void setPaystype(Integer paystype) {
		this.paystype = paystype;
	}

	public Charge getChargedata() {
		return chargedata;
	}

	public void setChargedata(Charge chargedata) {
		this.chargedata = chargedata;
	}

	public Integer getSflag() {
		return sflag;
	}

	public void setSflag(Integer sflag) {
		this.sflag = sflag;
	}

	public List<Infoitem> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<Infoitem> itemlist) {
		this.itemlist = itemlist;
	}

	public Infouser getUserdata() {
		return userdata;
	}

	public void setUserdata(Infouser userdata) {
		this.userdata = userdata;
	}

	public Integer getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(Integer totalpage) {
		this.totalpage = totalpage;
	}

}
