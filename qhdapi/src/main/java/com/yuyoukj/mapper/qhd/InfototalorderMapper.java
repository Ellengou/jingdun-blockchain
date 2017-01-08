package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;
import com.yuyoukj.model.qhd.Infototalorder;

public interface InfototalorderMapper {
	public Infototalorder getInfototalorder(Map<String, Object> pmap);

	public List<Infototalorder> getInfototalorderList(Map<String, Object> pmap);

	public void saveInfototalorder(Infototalorder infototalorder);

	public void updateInfototalorder(Infototalorder infototalorder);

	public void updateInfototalorder_map(Map<String, Object> pmap);

	public void delInfototalorder(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
