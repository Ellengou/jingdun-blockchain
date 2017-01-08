package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.ConfratioMapper;
import com.yuyoukj.model.qhd.Confratio;
import com.yuyoukj.service.ConfratioService;

@Service
public class ConfratioServiceImpl implements ConfratioService {
	@Autowired
	private ConfratioMapper confratioMapper;

	@Override
	public Page<Confratio> getConfratioList(Page<Confratio> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getConfratioList(pmap);
		return page;
	}

	@Override
	public List<Confratio> getConfratioList(Map<String, Object> pmap) {
		return confratioMapper.getConfratioList(pmap);
	}

	@Override
	public Confratio getConfratio(Map<String, Object> pmap) {
		return confratioMapper.getConfratio(pmap);
	}

	@Override
	public void delConfratio(Map<String, Object> pmap) {
		confratioMapper.delConfratio(pmap);
	}

	@Override
	public void saveConfratio(Confratio confratio) {
		if (confratio.getRatioid() == null) {
			confratioMapper.saveConfratio(confratio);
		} else {
			confratioMapper.updateConfratio(confratio);
		}
	}
	
	@Override
	public void updateConfratio_map(Map<String, Object> pmap) {

		confratioMapper.updateConfratio_map(pmap);

	}

	@Override
	public void updateConfratio(Confratio confratio) {

		confratioMapper.updateConfratio(confratio);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return confratioMapper.checkSname(pmap);
	}

}
