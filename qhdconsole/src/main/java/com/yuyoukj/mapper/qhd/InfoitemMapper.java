package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;

import com.yuyoukj.model.qhd.Infoitem;

public interface InfoitemMapper {
	public Infoitem getInfoitem(Map<String, Object> pmap);

	public List<Infoitem> getInfoitemList(Map<String, Object> pmap);

	public void saveInfoitem(Infoitem infoitem);

	public void updateInfoitem(Infoitem infoitem);
	
	public void updateInfoitem_map(Map<String, Object> pmap);

	public void delInfoitem(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
