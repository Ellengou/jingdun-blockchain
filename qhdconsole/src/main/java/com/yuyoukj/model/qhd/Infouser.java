package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Infouser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userid; // ; 1; 20;
    private String username; // 注册用户名（实际是手机号）; 0; 20;
    private String password; // 用户密码（MD5加密）; 0; 36;
    private String nickname; // 用户昵称; 0; 50;
    private String icon; // 头像url; 0; 200;
    private Long iconid; // 头像唯一id; 0; 20;
    private String registerdate; // 注册时间; 0; 20;
    private String payconfig; // 以","分隔，如：0,1; 0; 20;
    private Long createdate; // ; 0; 20;
    private Long updatedate; // ; 0; 20;

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
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Long getIconid() {
        return iconid;
    }

    public void setIconid(Long iconid) {
        this.iconid = iconid;
    }
    public String getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(String registerdate) {
        this.registerdate = registerdate;
    }
    public String getPayconfig() {
        return payconfig;
    }

    public void setPayconfig(String payconfig) {
        this.payconfig = payconfig;
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
