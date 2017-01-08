package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;

import com.yuyoukj.model.qhd.Infototal;

public interface InfototalMapper {
	public Infototal getInfototal(Map<String, Object> pmap);

	public List<Infototal> getInfototalList(Map<String, Object> pmap);

	public void saveInfototal(Infototal infototal);

	public void updateInfototal(Infototal infototal);
	
	public void updateInfototal_map(Map<String, Object> pmap);

	public void delInfototal(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
