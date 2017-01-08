package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;
import com.yuyoukj.model.qhd.Infotrade;

public interface InfotradeMapper {
	public Infotrade getInfotrade(Map<String, Object> pmap);

	public List<Infotrade> getInfotradeList(Map<String, Object> pmap);

	public void saveInfotrade(Infotrade infotrade);

	public void updateInfotrade(Infotrade infotrade);

	public void updateInfotrade_map(Map<String, Object> pmap);

	public void delInfotrade(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
