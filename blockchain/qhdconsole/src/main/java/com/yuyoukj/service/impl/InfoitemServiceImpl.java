package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.InfoitemMapper;
import com.yuyoukj.model.qhd.Infoitem;
import com.yuyoukj.service.InfoitemService;

@Service
public class InfoitemServiceImpl implements InfoitemService {
	@Autowired
	private InfoitemMapper infoitemMapper;

	@Override
	public Page<Infoitem> getInfoitemList(Page<Infoitem> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getInfoitemList(pmap);
		return page;
	}

	@Override
	public List<Infoitem> getInfoitemList(Map<String, Object> pmap) {
		return infoitemMapper.getInfoitemList(pmap);
	}

	@Override
	public Infoitem getInfoitem(Map<String, Object> pmap) {
		return infoitemMapper.getInfoitem(pmap);
	}

	@Override
	public void delInfoitem(Map<String, Object> pmap) {
		infoitemMapper.delInfoitem(pmap);
	}

	@Override
	public void saveInfoitem(Infoitem infoitem) {
		if (infoitem.getItemid() == null) {
			infoitemMapper.saveInfoitem(infoitem);
		} else {
			infoitemMapper.updateInfoitem(infoitem);
		}
	}
	
	@Override
	public void updateInfoitem_map(Map<String, Object> pmap) {

		infoitemMapper.updateInfoitem_map(pmap);

	}

	@Override
	public void updateInfoitem(Infoitem infoitem) {

		infoitemMapper.updateInfoitem(infoitem);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return infoitemMapper.checkSname(pmap);
	}

}
