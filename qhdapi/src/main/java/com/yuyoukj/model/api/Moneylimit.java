package com.yuyoukj.model.api;

import java.math.BigDecimal;

public class Moneylimit {
	private Integer stype;
	private BigDecimal min;
	private BigDecimal max;

	public Integer getStype() {
		return stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

}
