package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;

import com.yuyoukj.model.qhd.Logtoken;

public interface LogtokenMapper {
	public Logtoken getLogtoken(Map<String, Object> pmap);

	public List<Logtoken> getLogtokenList(Map<String, Object> pmap);

	public void saveLogtoken(Logtoken logtoken);

	public void updateLogtoken(Logtoken logtoken);
	
	public void updateLogtoken_map(Map<String, Object> pmap);

	public void delLogtoken(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
