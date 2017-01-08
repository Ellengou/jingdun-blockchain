package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Ckv implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer ck;
	private String cv;

	private Long seq;

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Integer getCk() {
		return ck;
	}

	public String getCv() {
		return cv;
	}

	public void setCk(Integer ck) {
		this.ck = ck;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

}
