package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Confprovince;

public interface ConfprovinceService {

	public Page<Confprovince> getConfprovinceList(Page<Confprovince> page, Map<String, Object> pmap);

	public List<Confprovince> getConfprovinceList(Map<String, Object> pmap);

	public Confprovince getConfprovince(Map<String, Object> pmap);

	public void saveConfprovince(Confprovince confprovince);

	public void delConfprovince(Map<String, Object> pmap);

	public void updateConfprovince(Confprovince confprovince);
	
	public void updateConfprovince_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
