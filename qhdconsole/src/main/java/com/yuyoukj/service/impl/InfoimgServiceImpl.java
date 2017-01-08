package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.mapper.qhd.InfoimgMapper;
import com.yuyoukj.model.qhd.Infoimg;
import com.yuyoukj.service.InfoimgService;

@Service
public class InfoimgServiceImpl implements InfoimgService {
	@Autowired
	private InfoimgMapper infoimgMapper;

	@Override
	public Page<Infoimg> getInfoimgList(Page<Infoimg> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getInfoimgList(pmap);
		return page;
	}

	@Override
	public List<Infoimg> getInfoimgList(Map<String, Object> pmap) {
		return infoimgMapper.getInfoimgList(pmap);
	}

	@Override
	public Infoimg getInfoimg(Map<String, Object> pmap) {
		return infoimgMapper.getInfoimg(pmap);
	}

	@Override
	public void delInfoimg(Map<String, Object> pmap) {
		infoimgMapper.delInfoimg(pmap);
	}

	@Override
	public void saveInfoimg(Infoimg infoimg) {
		if (infoimg.getImgid() == null) {
			infoimgMapper.saveInfoimg(infoimg);
		} else {
			infoimgMapper.updateInfoimg(infoimg);
		}
	}
	
	@Override
	public void updateInfoimg_map(Map<String, Object> pmap) {

		infoimgMapper.updateInfoimg_map(pmap);

	}

	@Override
	public void updateInfoimg(Infoimg infoimg) {

		infoimgMapper.updateInfoimg(infoimg);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return infoimgMapper.checkSname(pmap);
	}

}
