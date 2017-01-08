package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Logtoken;

public interface LogtokenService {

	public Page<Logtoken> getLogtokenList(Page<Logtoken> page, Map<String, Object> pmap);

	public List<Logtoken> getLogtokenList(Map<String, Object> pmap);

	public Logtoken getLogtoken(Map<String, Object> pmap);

	public void saveLogtoken(Logtoken logtoken);

	public void delLogtoken(Map<String, Object> pmap);

	public void updateLogtoken(Logtoken logtoken);
	
	public void updateLogtoken_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
