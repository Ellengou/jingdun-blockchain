package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Infotrade;

public interface InfotradeService {

	public Page<Infotrade> getInfotradeList(Page<Infotrade> page, Map<String, Object> pmap);

	public List<Infotrade> getInfotradeList(Map<String, Object> pmap);

	public Infotrade getInfotrade(Map<String, Object> pmap);

	public void saveInfotrade(Infotrade infotrade);

	public void delInfotrade(Map<String, Object> pmap);

	public void updateInfotrade(Infotrade infotrade);
	
	public void updateInfotrade_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
