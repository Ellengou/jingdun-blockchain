package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;
import com.yuyoukj.model.qhd.Infototalordertrade;

public interface InfototalordertradeMapper {
	public Infototalordertrade getInfototalordertrade(Map<String, Object> pmap);

	public List<Infototalordertrade> getInfototalordertradeList(Map<String, Object> pmap);

	public void saveInfototalordertrade(Infototalordertrade infototalordertrade);

	public void updateInfototalordertrade(Infototalordertrade infototalordertrade);

	public void updateInfototalordertrade_map(Map<String, Object> pmap);

	public void delInfototalordertrade(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
