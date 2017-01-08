package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Logpasswd;

public interface LogpasswdService {

	public Page<Logpasswd> getLogpasswdList(Page<Logpasswd> page, Map<String, Object> pmap);

	public List<Logpasswd> getLogpasswdList(Map<String, Object> pmap);

	public Logpasswd getLogpasswd(Map<String, Object> pmap);

	public void saveLogpasswd(Logpasswd logpasswd);

	public void delLogpasswd(Map<String, Object> pmap);

	public void updateLogpasswd(Logpasswd logpasswd);
	
	public void updateLogpasswd_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
