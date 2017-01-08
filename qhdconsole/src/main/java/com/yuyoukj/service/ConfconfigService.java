package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Confconfig;

public interface ConfconfigService {

	public Page<Confconfig> getConfconfigList(Page<Confconfig> page, Map<String, Object> pmap);

	public List<Confconfig> getConfconfigList(Map<String, Object> pmap);

	public Confconfig getConfconfig(Map<String, Object> pmap);

	public void saveConfconfig(Confconfig confconfig);

	public void delConfconfig(Map<String, Object> pmap);

	public void updateConfconfig(Confconfig confconfig);
	
	public void updateConfconfig_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
