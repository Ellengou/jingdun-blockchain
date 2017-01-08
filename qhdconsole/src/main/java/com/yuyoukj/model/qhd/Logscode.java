package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Logscode implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long seq; // ; 1; 20;
    private String username; // 用户名（实际为手机号）; 0; 20;
    private String scode; // 短信验证码; 0; 10;
    private Integer sflag; // 状态：0-正常；1-失效；; 0; 20;
    private Long createdate; // ; 0; 20;
    private Long updatedate; // ; 0; 20;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
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
