package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;
import com.yuyoukj.mapper.qhd.InfototalordertradeMapper;
import com.yuyoukj.model.qhd.Infototalordertrade;
import com.yuyoukj.service.InfototalordertradeService;

@Service
public class InfototalordertradeServiceImpl implements InfototalordertradeService {
	@Autowired
	private InfototalordertradeMapper infototalordertradeMapper;

	@Override
	public Page<Infototalordertrade> getInfototalordertradeList(Page<Infototalordertrade> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getInfototalordertradeList(pmap);
		return page;
	}

	@Override
	public List<Infototalordertrade> getInfototalordertradeList(Map<String, Object> pmap) {
		return infototalordertradeMapper.getInfototalordertradeList(pmap);
	}

	@Override
	public Infototalordertrade getInfototalordertrade(Map<String, Object> pmap) {
		return infototalordertradeMapper.getInfototalordertrade(pmap);
	}

	@Override
	public void delInfototalordertrade(Map<String, Object> pmap) {
		infototalordertradeMapper.delInfototalordertrade(pmap);
	}

	@Override
	public void saveInfototalordertrade(Infototalordertrade infototalordertrade) {
		if (infototalordertrade.getSeq() == null) {
			infototalordertradeMapper.saveInfototalordertrade(infototalordertrade);
		} else {
			infototalordertradeMapper.updateInfototalordertrade(infototalordertrade);
		}
	}

	@Override
	public void updateInfototalordertrade_map(Map<String, Object> pmap) {

		infototalordertradeMapper.updateInfototalordertrade_map(pmap);

	}

	@Override
	public void updateInfototalordertrade(Infototalordertrade infototalordertrade) {

		infototalordertradeMapper.updateInfototalordertrade(infototalordertrade);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return infototalordertradeMapper.checkSname(pmap);
	}

}
