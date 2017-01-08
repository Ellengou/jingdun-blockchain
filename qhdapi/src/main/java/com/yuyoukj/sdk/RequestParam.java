package com.yuyoukj.sdk;

import java.io.File;
import java.math.BigDecimal;
import com.pingplusplus.model.Event_obj;
import com.yuyoukj.ao.interfaceframework.controller.BaseInfo;

public class RequestParam extends BaseInfo {
	private String method;
	private String appsig; // 校验码
	private String appkey; // 应用唯一标识
	private String appsecret;
	private Integer versioncode;

	// **********************************************
	private File[] file;
	private String username;
	private String password;
	private Long userid;
	private String scode;
	private Integer pagenum;
	private Integer pagesize;
	private String paramurl;
	private Long viewuserid;
	private Integer stype;
	private Integer sflag;
	private String nickname;
	private String paypalnum;
	private String paypalname;
	private String alipaynum;
	private String alipayname;
	private BigDecimal money;
	private String itemids;
	private Long payorder;
	private Long itemid;
	private String paypalid;
	private String keyword;
	private Event_obj event;

	// **********************************************

	public String getMethod() {
		return method;
	}

	public Event_obj getEvent() {
		return event;
	}

	public void setEvent(Event_obj event) {
		this.event = event;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPaypalid() {
		return paypalid;
	}

	public void setPaypalid(String paypalid) {
		this.paypalid = paypalid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Long getPayorder() {
		return payorder;
	}

	public void setPayorder(Long payorder) {
		this.payorder = payorder;
	}

	public String getItemids() {
		return itemids;
	}

	public void setItemids(String itemids) {
		this.itemids = itemids;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPaypalnum() {
		return paypalnum;
	}

	public void setPaypalnum(String paypalnum) {
		this.paypalnum = paypalnum;
	}

	public String getPaypalname() {
		return paypalname;
	}

	public void setPaypalname(String paypalname) {
		this.paypalname = paypalname;
	}

	public String getAlipaynum() {
		return alipaynum;
	}

	public void setAlipaynum(String alipaynum) {
		this.alipaynum = alipaynum;
	}

	public String getAlipayname() {
		return alipayname;
	}

	public void setAlipayname(String alipayname) {
		this.alipayname = alipayname;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAppsig() {
		return appsig;
	}

	public void setAppsig(String appsig) {
		this.appsig = appsig;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public Integer getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(Integer versioncode) {
		this.versioncode = versioncode;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public Integer getPagenum() {
		return pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public String getParamurl() {
		return paramurl;
	}

	public void setParamurl(String paramurl) {
		this.paramurl = paramurl;
	}

	public Long getViewuserid() {
		return viewuserid;
	}

	public void setViewuserid(Long viewuserid) {
		this.viewuserid = viewuserid;
	}

	public Integer getStype() {
		return stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}

	public Integer getSflag() {
		return sflag;
	}

	public void setSflag(Integer sflag) {
		this.sflag = sflag;
	}

}
