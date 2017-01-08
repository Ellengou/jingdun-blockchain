package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;

import com.yuyoukj.model.qhd.Confprovince;

public interface ConfprovinceMapper {
	public Confprovince getConfprovince(Map<String, Object> pmap);

	public List<Confprovince> getConfprovinceList(Map<String, Object> pmap);

	public void saveConfprovince(Confprovince confprovince);

	public void updateConfprovince(Confprovince confprovince);
	
	public void updateConfprovince_map(Map<String, Object> pmap);

	public void delConfprovince(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
