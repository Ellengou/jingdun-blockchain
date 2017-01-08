package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.InfouserMapper;
import com.yuyoukj.model.qhd.Infouser;
import com.yuyoukj.service.InfouserService;

@Service
public class InfouserServiceImpl implements InfouserService {
	@Autowired
	private InfouserMapper infouserMapper;

	@Override
	public Page<Infouser> getInfouserList(Page<Infouser> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getInfouserList(pmap);
		return page;
	}

	@Override
	public List<Infouser> getInfouserList(Map<String, Object> pmap) {
		return infouserMapper.getInfouserList(pmap);
	}

	@Override
	public Infouser getInfouser(Map<String, Object> pmap) {
		return infouserMapper.getInfouser(pmap);
	}

	@Override
	public void delInfouser(Map<String, Object> pmap) {
		infouserMapper.delInfouser(pmap);
	}

	@Override
	public void saveInfouser(Infouser infouser) {
		if (infouser.getUserid() == null) {
			infouserMapper.saveInfouser(infouser);
		} else {
			infouserMapper.updateInfouser(infouser);
		}
	}
	
	@Override
	public void updateInfouser_map(Map<String, Object> pmap) {

		infouserMapper.updateInfouser_map(pmap);

	}

	@Override
	public void updateInfouser(Infouser infouser) {

		infouserMapper.updateInfouser(infouser);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return infouserMapper.checkSname(pmap);
	}

}
