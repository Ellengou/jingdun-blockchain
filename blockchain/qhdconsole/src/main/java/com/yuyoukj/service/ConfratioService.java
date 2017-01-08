package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Confratio;

public interface ConfratioService {

	public Page<Confratio> getConfratioList(Page<Confratio> page, Map<String, Object> pmap);

	public List<Confratio> getConfratioList(Map<String, Object> pmap);

	public Confratio getConfratio(Map<String, Object> pmap);

	public void saveConfratio(Confratio confratio);

	public void delConfratio(Map<String, Object> pmap);

	public void updateConfratio(Confratio confratio);
	
	public void updateConfratio_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
