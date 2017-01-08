package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;
import com.yuyoukj.model.qhd.Logpasswd;

public interface LogpasswdMapper {
	public Logpasswd getLogpasswd(Map<String, Object> pmap);

	public List<Logpasswd> getLogpasswdList(Map<String, Object> pmap);

	public void saveLogpasswd(Logpasswd logpasswd);

	public void updateLogpasswd(Logpasswd logpasswd);

	public void updateLogpasswd_map(Map<String, Object> pmap);

	public void delLogpasswd(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
