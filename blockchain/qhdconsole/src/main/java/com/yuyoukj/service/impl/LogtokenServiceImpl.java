package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.LogtokenMapper;
import com.yuyoukj.model.qhd.Logtoken;
import com.yuyoukj.service.LogtokenService;

@Service
public class LogtokenServiceImpl implements LogtokenService {
	@Autowired
	private LogtokenMapper logtokenMapper;

	@Override
	public Page<Logtoken> getLogtokenList(Page<Logtoken> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getLogtokenList(pmap);
		return page;
	}

	@Override
	public List<Logtoken> getLogtokenList(Map<String, Object> pmap) {
		return logtokenMapper.getLogtokenList(pmap);
	}

	@Override
	public Logtoken getLogtoken(Map<String, Object> pmap) {
		return logtokenMapper.getLogtoken(pmap);
	}

	@Override
	public void delLogtoken(Map<String, Object> pmap) {
		logtokenMapper.delLogtoken(pmap);
	}

	@Override
	public void saveLogtoken(Logtoken logtoken) {
		if (logtoken.getSeq() == null) {
			logtokenMapper.saveLogtoken(logtoken);
		} else {
			logtokenMapper.updateLogtoken(logtoken);
		}
	}
	
	@Override
	public void updateLogtoken_map(Map<String, Object> pmap) {

		logtokenMapper.updateLogtoken_map(pmap);

	}

	@Override
	public void updateLogtoken(Logtoken logtoken) {

		logtokenMapper.updateLogtoken(logtoken);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return logtokenMapper.checkSname(pmap);
	}

}
