package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.ConfcityMapper;
import com.yuyoukj.model.qhd.Confcity;
import com.yuyoukj.service.ConfcityService;

@Service
public class ConfcityServiceImpl implements ConfcityService {
	@Autowired
	private ConfcityMapper confcityMapper;

	@Override
	public Page<Confcity> getConfcityList(Page<Confcity> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getConfcityList(pmap);
		return page;
	}

	@Override
	public List<Confcity> getConfcityList(Map<String, Object> pmap) {
		return confcityMapper.getConfcityList(pmap);
	}

	@Override
	public Confcity getConfcity(Map<String, Object> pmap) {
		return confcityMapper.getConfcity(pmap);
	}

	@Override
	public void delConfcity(Map<String, Object> pmap) {
		confcityMapper.delConfcity(pmap);
	}

	@Override
	public void saveConfcity(Confcity confcity) {
		if (confcity.getId() == null) {
			confcityMapper.saveConfcity(confcity);
		} else {
			confcityMapper.updateConfcity(confcity);
		}
	}
	
	@Override
	public void updateConfcity_map(Map<String, Object> pmap) {

		confcityMapper.updateConfcity_map(pmap);

	}

	@Override
	public void updateConfcity(Confcity confcity) {

		confcityMapper.updateConfcity(confcity);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return confcityMapper.checkSname(pmap);
	}

}
