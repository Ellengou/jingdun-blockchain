package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.LogpasswdMapper;
import com.yuyoukj.model.qhd.Logpasswd;
import com.yuyoukj.service.LogpasswdService;

@Service
public class LogpasswdServiceImpl implements LogpasswdService {
	@Autowired
	private LogpasswdMapper logpasswdMapper;

	@Override
	public Page<Logpasswd> getLogpasswdList(Page<Logpasswd> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getLogpasswdList(pmap);
		return page;
	}

	@Override
	public List<Logpasswd> getLogpasswdList(Map<String, Object> pmap) {
		return logpasswdMapper.getLogpasswdList(pmap);
	}

	@Override
	public Logpasswd getLogpasswd(Map<String, Object> pmap) {
		return logpasswdMapper.getLogpasswd(pmap);
	}

	@Override
	public void delLogpasswd(Map<String, Object> pmap) {
		logpasswdMapper.delLogpasswd(pmap);
	}

	@Override
	public void saveLogpasswd(Logpasswd logpasswd) {
		if (logpasswd.getSeq() == null) {
			logpasswdMapper.saveLogpasswd(logpasswd);
		} else {
			logpasswdMapper.updateLogpasswd(logpasswd);
		}
	}
	
	@Override
	public void updateLogpasswd_map(Map<String, Object> pmap) {

		logpasswdMapper.updateLogpasswd_map(pmap);

	}

	@Override
	public void updateLogpasswd(Logpasswd logpasswd) {

		logpasswdMapper.updateLogpasswd(logpasswd);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return logpasswdMapper.checkSname(pmap);
	}

}
