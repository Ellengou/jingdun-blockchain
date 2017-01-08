package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;

import com.yuyoukj.model.qhd.Confconfig;

public interface ConfconfigMapper {
	public Confconfig getConfconfig(Map<String, Object> pmap);

	public List<Confconfig> getConfconfigList(Map<String, Object> pmap);

	public void saveConfconfig(Confconfig confconfig);

	public void updateConfconfig(Confconfig confconfig);
	
	public void updateConfconfig_map(Map<String, Object> pmap);

	public void delConfconfig(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
