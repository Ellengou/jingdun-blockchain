package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Logscode;

public interface LogscodeService {

	public Page<Logscode> getLogscodeList(Page<Logscode> page, Map<String, Object> pmap);

	public List<Logscode> getLogscodeList(Map<String, Object> pmap);

	public Logscode getLogscode(Map<String, Object> pmap);

	public void saveLogscode(Logscode logscode);

	public void delLogscode(Map<String, Object> pmap);

	public void updateLogscode(Logscode logscode);
	
	public void updateLogscode_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
