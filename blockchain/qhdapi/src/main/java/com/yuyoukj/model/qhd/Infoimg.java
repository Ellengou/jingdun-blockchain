package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Infoimg implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long imgid; // ; 1; 20;
	private String imgurl; // ; 0; 200;
	private String ctbname; // 图片所用于的表名称; 0; 50;
	private Long createdate; // ; 0; 20;
	private Long updatedate; // ; 0; 20;

	public Long getImgid() {
		return imgid;
	}

	public void setImgid(Long imgid) {
		this.imgid = imgid;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getCtbname() {
		return ctbname;
	}

	public void setCtbname(String ctbname) {
		this.ctbname = ctbname;
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
