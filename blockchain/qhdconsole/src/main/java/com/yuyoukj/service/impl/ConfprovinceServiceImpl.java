package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.ConfprovinceMapper;
import com.yuyoukj.model.qhd.Confprovince;
import com.yuyoukj.service.ConfprovinceService;

@Service
public class ConfprovinceServiceImpl implements ConfprovinceService {
	@Autowired
	private ConfprovinceMapper confprovinceMapper;

	@Override
	public Page<Confprovince> getConfprovinceList(Page<Confprovince> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getConfprovinceList(pmap);
		return page;
	}

	@Override
	public List<Confprovince> getConfprovinceList(Map<String, Object> pmap) {
		return confprovinceMapper.getConfprovinceList(pmap);
	}

	@Override
	public Confprovince getConfprovince(Map<String, Object> pmap) {
		return confprovinceMapper.getConfprovince(pmap);
	}

	@Override
	public void delConfprovince(Map<String, Object> pmap) {
		confprovinceMapper.delConfprovince(pmap);
	}

	@Override
	public void saveConfprovince(Confprovince confprovince) {
		if (confprovince.getId() == null) {
			confprovinceMapper.saveConfprovince(confprovince);
		} else {
			confprovinceMapper.updateConfprovince(confprovince);
		}
	}
	
	@Override
	public void updateConfprovince_map(Map<String, Object> pmap) {

		confprovinceMapper.updateConfprovince_map(pmap);

	}

	@Override
	public void updateConfprovince(Confprovince confprovince) {

		confprovinceMapper.updateConfprovince(confprovince);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return confprovinceMapper.checkSname(pmap);
	}

}
