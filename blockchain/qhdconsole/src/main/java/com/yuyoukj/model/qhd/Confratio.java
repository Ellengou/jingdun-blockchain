package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Confratio implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long ratioid; // ; 1; 20;
    private Long sdate; // 日期，如：20160410; 0; 20;
    private Double ratio; // 汇率; 0; 20;
    private Integer stype; // 需求类型：0-CNY；1-USD; 0; 20;
    private Integer intype; // 支付费用类型：0-CNY；1-USD; 0; 20;
    private String sfrom; // 汇率来源url; 0; 200;
    private Long getdate; // 更新时间; 0; 20;
    private Long createdate; // ; 0; 20;
    private Long updatedate; // ; 0; 20;

    public Long getRatioid() {
        return ratioid;
    }

    public void setRatioid(Long ratioid) {
        this.ratioid = ratioid;
    }
    public Long getSdate() {
        return sdate;
    }

    public void setSdate(Long sdate) {
        this.sdate = sdate;
    }
    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }
    public Integer getStype() {
        return stype;
    }

    public void setStype(Integer stype) {
        this.stype = stype;
    }
    public Integer getIntype() {
        return intype;
    }

    public void setIntype(Integer intype) {
        this.intype = intype;
    }
    public String getSfrom() {
        return sfrom;
    }

    public void setSfrom(String sfrom) {
        this.sfrom = sfrom;
    }
    public Long getGetdate() {
        return getdate;
    }

    public void setGetdate(Long getdate) {
        this.getdate = getdate;
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
