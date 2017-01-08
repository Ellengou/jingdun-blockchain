package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.InfototalMapper;
import com.yuyoukj.model.qhd.Infototal;
import com.yuyoukj.service.InfototalService;

@Service
public class InfototalServiceImpl implements InfototalService {
	@Autowired
	private InfototalMapper infototalMapper;

	@Override
	public Page<Infototal> getInfototalList(Page<Infototal> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getInfototalList(pmap);
		return page;
	}

	@Override
	public List<Infototal> getInfototalList(Map<String, Object> pmap) {
		return infototalMapper.getInfototalList(pmap);
	}

	@Override
	public Infototal getInfototal(Map<String, Object> pmap) {
		return infototalMapper.getInfototal(pmap);
	}

	@Override
	public void delInfototal(Map<String, Object> pmap) {
		infototalMapper.delInfototal(pmap);
	}

	@Override
	public void saveInfototal(Infototal infototal) {
		if (infototal.getTotalid() == null) {
			infototalMapper.saveInfototal(infototal);
		} else {
			infototalMapper.updateInfototal(infototal);
		}
	}
	
	@Override
	public void updateInfototal_map(Map<String, Object> pmap) {

		infototalMapper.updateInfototal_map(pmap);

	}

	@Override
	public void updateInfototal(Infototal infototal) {

		infototalMapper.updateInfototal(infototal);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return infototalMapper.checkSname(pmap);
	}

}
