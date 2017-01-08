package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;

import com.yuyoukj.model.qhd.Confcity;

public interface ConfcityMapper {
	public Confcity getConfcity(Map<String, Object> pmap);

	public List<Confcity> getConfcityList(Map<String, Object> pmap);

	public void saveConfcity(Confcity confcity);

	public void updateConfcity(Confcity confcity);
	
	public void updateConfcity_map(Map<String, Object> pmap);

	public void delConfcity(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
