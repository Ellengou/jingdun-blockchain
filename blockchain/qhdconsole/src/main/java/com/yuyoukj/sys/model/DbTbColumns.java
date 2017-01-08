package com.yuyoukj.sys.model;

public class DbTbColumns {
	private String columnname;
	private String ucolumnname;
	private String datatype;
	private String columncomment;
	private Integer pkkey;
	private Integer maxlen;

	public String getUcolumnname() {
		return ucolumnname;
	}

	public void setUcolumnname(String ucolumnname) {
		this.ucolumnname = ucolumnname;
	}

	public Integer getMaxlen() {
		return maxlen;
	}

	public void setMaxlen(Integer maxlen) {
		this.maxlen = maxlen;
	}

	public String getColumncomment() {
		return columncomment;
	}

	public void setColumncomment(String columncomment) {
		this.columncomment = columncomment;
	}

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public Integer getPkkey() {
		return pkkey;
	}

	public void setPkkey(Integer pkkey) {
		this.pkkey = pkkey;
	}

}
