package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Infoitem;

public interface InfoitemService {

	public Page<Infoitem> getInfoitemList(Page<Infoitem> page, Map<String, Object> pmap);

	public List<Infoitem> getInfoitemList(Map<String, Object> pmap);

	public Infoitem getInfoitem(Map<String, Object> pmap);

	public void saveInfoitem(Infoitem infoitem);

	public void delInfoitem(Map<String, Object> pmap);

	public void updateInfoitem(Infoitem infoitem);
	
	public void updateInfoitem_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
