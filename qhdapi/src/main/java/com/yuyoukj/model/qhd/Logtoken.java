package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Logtoken implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long seq; // ; 1; 20;
	private String token; // ; 0; 40;
	private Long userid; // ; 0; 20;
	private Integer sflag; // 状态：0-正常；1-失效；; 0; 20;
	private Long createdate; // ; 0; 20;
	private Long updatedate; // ; 0; 20;

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Integer getSflag() {
		return sflag;
	}

	public void setSflag(Integer sflag) {
		this.sflag = sflag;
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
