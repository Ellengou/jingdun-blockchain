package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.ConfareaMapper;
import com.yuyoukj.model.qhd.Confarea;
import com.yuyoukj.service.ConfareaService;

@Service
public class ConfareaServiceImpl implements ConfareaService {
	@Autowired
	private ConfareaMapper confareaMapper;

	@Override
	public Page<Confarea> getConfareaList(Page<Confarea> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getConfareaList(pmap);
		return page;
	}

	@Override
	public List<Confarea> getConfareaList(Map<String, Object> pmap) {
		return confareaMapper.getConfareaList(pmap);
	}

	@Override
	public Confarea getConfarea(Map<String, Object> pmap) {
		return confareaMapper.getConfarea(pmap);
	}

	@Override
	public void delConfarea(Map<String, Object> pmap) {
		confareaMapper.delConfarea(pmap);
	}

	@Override
	public void saveConfarea(Confarea confarea) {
		if (confarea.getId() == null) {
			confareaMapper.saveConfarea(confarea);
		} else {
			confareaMapper.updateConfarea(confarea);
		}
	}
	
	@Override
	public void updateConfarea_map(Map<String, Object> pmap) {

		confareaMapper.updateConfarea_map(pmap);

	}

	@Override
	public void updateConfarea(Confarea confarea) {

		confareaMapper.updateConfarea(confarea);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return confareaMapper.checkSname(pmap);
	}

}
