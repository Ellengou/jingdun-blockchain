package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Logpasswd implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long seq; // ; 1; 20;
    private String username; // 注册用户名（实际是手机号）; 0; 20;
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
