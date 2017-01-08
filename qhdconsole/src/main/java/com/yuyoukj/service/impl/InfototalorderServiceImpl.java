package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.InfototalorderMapper;
import com.yuyoukj.model.qhd.Infototalorder;
import com.yuyoukj.service.InfototalorderService;

@Service
public class InfototalorderServiceImpl implements InfototalorderService {
	@Autowired
	private InfototalorderMapper infototalorderMapper;

	@Override
	public Page<Infototalorder> getInfototalorderList(Page<Infototalorder> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getInfototalorderList(pmap);
		return page;
	}

	@Override
	public List<Infototalorder> getInfototalorderList(Map<String, Object> pmap) {
		return infototalorderMapper.getInfototalorderList(pmap);
	}

	@Override
	public Infototalorder getInfototalorder(Map<String, Object> pmap) {
		return infototalorderMapper.getInfototalorder(pmap);
	}

	@Override
	public void delInfototalorder(Map<String, Object> pmap) {
		infototalorderMapper.delInfototalorder(pmap);
	}

	@Override
	public void saveInfototalorder(Infototalorder infototalorder) {
		if (infototalorder.getSeq() == null) {
			infototalorderMapper.saveInfototalorder(infototalorder);
		} else {
			infototalorderMapper.updateInfototalorder(infototalorder);
		}
	}
	
	@Override
	public void updateInfototalorder_map(Map<String, Object> pmap) {

		infototalorderMapper.updateInfototalorder_map(pmap);

	}

	@Override
	public void updateInfototalorder(Infototalorder infototalorder) {

		infototalorderMapper.updateInfototalorder(infototalorder);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return infototalorderMapper.checkSname(pmap);
	}

}
