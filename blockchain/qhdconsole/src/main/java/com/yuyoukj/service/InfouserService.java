package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Infouser;

public interface InfouserService {

	public Page<Infouser> getInfouserList(Page<Infouser> page, Map<String, Object> pmap);

	public List<Infouser> getInfouserList(Map<String, Object> pmap);

	public Infouser getInfouser(Map<String, Object> pmap);

	public void saveInfouser(Infouser infouser);

	public void delInfouser(Map<String, Object> pmap);

	public void updateInfouser(Infouser infouser);
	
	public void updateInfouser_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
