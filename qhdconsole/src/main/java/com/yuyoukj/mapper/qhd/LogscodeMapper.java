package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;

import com.yuyoukj.model.qhd.Logscode;

public interface LogscodeMapper {
	public Logscode getLogscode(Map<String, Object> pmap);

	public List<Logscode> getLogscodeList(Map<String, Object> pmap);

	public void saveLogscode(Logscode logscode);

	public void updateLogscode(Logscode logscode);
	
	public void updateLogscode_map(Map<String, Object> pmap);

	public void delLogscode(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
