package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.ConfconfigMapper;
import com.yuyoukj.model.qhd.Confconfig;
import com.yuyoukj.service.ConfconfigService;

@Service
public class ConfconfigServiceImpl implements ConfconfigService {
	@Autowired
	private ConfconfigMapper confconfigMapper;

	@Override
	public Page<Confconfig> getConfconfigList(Page<Confconfig> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getConfconfigList(pmap);
		return page;
	}

	@Override
	public List<Confconfig> getConfconfigList(Map<String, Object> pmap) {
		return confconfigMapper.getConfconfigList(pmap);
	}

	@Override
	public Confconfig getConfconfig(Map<String, Object> pmap) {
		return confconfigMapper.getConfconfig(pmap);
	}

	@Override
	public void delConfconfig(Map<String, Object> pmap) {
		confconfigMapper.delConfconfig(pmap);
	}

	@Override
	public void saveConfconfig(Confconfig confconfig) {
		if (confconfig.getSeq() == null) {
			confconfigMapper.saveConfconfig(confconfig);
		} else {
			confconfigMapper.updateConfconfig(confconfig);
		}
	}
	
	@Override
	public void updateConfconfig_map(Map<String, Object> pmap) {

		confconfigMapper.updateConfconfig_map(pmap);

	}

	@Override
	public void updateConfconfig(Confconfig confconfig) {

		confconfigMapper.updateConfconfig(confconfig);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return confconfigMapper.checkSname(pmap);
	}

}
