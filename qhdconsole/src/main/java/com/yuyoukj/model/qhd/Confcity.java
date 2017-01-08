package com.yuyoukj.model.qhd;

import java.io.Serializable;

public class Confcity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id; // ; 1; 20;
    private Integer cityid; // 市id; 0; 20;
    private String cityname; // 市名称; 0; 50;
    private Integer fatherid; // 上一级辖区id; 0; 20;
    private Integer orderby; // 排序号; 0; 20;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }
    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
    public Integer getFatherid() {
        return fatherid;
    }

    public void setFatherid(Integer fatherid) {
        this.fatherid = fatherid;
    }
    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }
}
