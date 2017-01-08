package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Infototal;

public interface InfototalService {

	public Page<Infototal> getInfototalList(Page<Infototal> page, Map<String, Object> pmap);

	public List<Infototal> getInfototalList(Map<String, Object> pmap);

	public Infototal getInfototal(Map<String, Object> pmap);

	public void saveInfototal(Infototal infototal);

	public void delInfototal(Map<String, Object> pmap);

	public void updateInfototal(Infototal infototal);
	
	public void updateInfototal_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
