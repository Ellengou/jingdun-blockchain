package com.yuyoukj.sys.model;

public class InfoCUser implements java.io.Serializable {

	private static final long serialVersionUID = 7513094488837403108L;

	private Long userid; // 用户编号
	private String username; // 用户名称
	private String password; // 用户密码
	private Integer userstate; // 状态(0:启用 1:注销)
	private String nickname;
	private Long rid;

	private String musername;

	public String getMusername() {
		return musername;
	}

	public void setMusername(String musername) {
		this.musername = musername;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public Integer getUserstate() {

		return userstate;
	}

	public void setUserstate(Integer userstate) {

		this.userstate = userstate;
	}

}
