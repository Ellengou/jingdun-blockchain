package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;
import com.yuyoukj.model.qhd.Infouser;

public interface InfouserMapper {
	public Infouser getInfouser(Map<String, Object> pmap);

	public List<Infouser> getInfouserList(Map<String, Object> pmap);

	public void saveInfouser(Infouser infouser);

	public void updateInfouser(Infouser infouser);

	public void updateInfouser_map(Map<String, Object> pmap);

	public void delInfouser(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

	public Integer checkInfouser(Map<String, Object> xmap);

}
