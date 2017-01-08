package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.InfopayconfigMapper;
import com.yuyoukj.model.qhd.Infopayconfig;
import com.yuyoukj.service.InfopayconfigService;

@Service
public class InfopayconfigServiceImpl implements InfopayconfigService {
	@Autowired
	private InfopayconfigMapper infopayconfigMapper;

	@Override
	public Page<Infopayconfig> getInfopayconfigList(Page<Infopayconfig> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getInfopayconfigList(pmap);
		return page;
	}

	@Override
	public List<Infopayconfig> getInfopayconfigList(Map<String, Object> pmap) {
		return infopayconfigMapper.getInfopayconfigList(pmap);
	}

	@Override
	public Infopayconfig getInfopayconfig(Map<String, Object> pmap) {
		return infopayconfigMapper.getInfopayconfig(pmap);
	}

	@Override
	public void delInfopayconfig(Map<String, Object> pmap) {
		infopayconfigMapper.delInfopayconfig(pmap);
	}

	@Override
	public void saveInfopayconfig(Infopayconfig infopayconfig) {
		if (infopayconfig.getSeq() == null) {
			infopayconfigMapper.saveInfopayconfig(infopayconfig);
		} else {
			infopayconfigMapper.updateInfopayconfig(infopayconfig);
		}
	}
	
	@Override
	public void updateInfopayconfig_map(Map<String, Object> pmap) {

		infopayconfigMapper.updateInfopayconfig_map(pmap);

	}

	@Override
	public void updateInfopayconfig(Infopayconfig infopayconfig) {

		infopayconfigMapper.updateInfopayconfig(infopayconfig);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return infopayconfigMapper.checkSname(pmap);
	}

}
