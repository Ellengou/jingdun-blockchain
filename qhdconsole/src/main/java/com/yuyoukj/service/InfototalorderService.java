package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Infototalorder;

public interface InfototalorderService {

	public Page<Infototalorder> getInfototalorderList(Page<Infototalorder> page, Map<String, Object> pmap);

	public List<Infototalorder> getInfototalorderList(Map<String, Object> pmap);

	public Infototalorder getInfototalorder(Map<String, Object> pmap);

	public void saveInfototalorder(Infototalorder infototalorder);

	public void delInfototalorder(Map<String, Object> pmap);

	public void updateInfototalorder(Infototalorder infototalorder);
	
	public void updateInfototalorder_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
