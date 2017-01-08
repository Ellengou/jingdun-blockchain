package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.LogscodeMapper;
import com.yuyoukj.model.qhd.Logscode;
import com.yuyoukj.service.LogscodeService;

@Service
public class LogscodeServiceImpl implements LogscodeService {
	@Autowired
	private LogscodeMapper logscodeMapper;

	@Override
	public Page<Logscode> getLogscodeList(Page<Logscode> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getLogscodeList(pmap);
		return page;
	}

	@Override
	public List<Logscode> getLogscodeList(Map<String, Object> pmap) {
		return logscodeMapper.getLogscodeList(pmap);
	}

	@Override
	public Logscode getLogscode(Map<String, Object> pmap) {
		return logscodeMapper.getLogscode(pmap);
	}

	@Override
	public void delLogscode(Map<String, Object> pmap) {
		logscodeMapper.delLogscode(pmap);
	}

	@Override
	public void saveLogscode(Logscode logscode) {
		if (logscode.getSeq() == null) {
			logscodeMapper.saveLogscode(logscode);
		} else {
			logscodeMapper.updateLogscode(logscode);
		}
	}
	
	@Override
	public void updateLogscode_map(Map<String, Object> pmap) {

		logscodeMapper.updateLogscode_map(pmap);

	}

	@Override
	public void updateLogscode(Logscode logscode) {

		logscodeMapper.updateLogscode(logscode);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return logscodeMapper.checkSname(pmap);
	}

}
