package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Infoimg;

public interface InfoimgService {

	public Page<Infoimg> getInfoimgList(Page<Infoimg> page, Map<String, Object> pmap);

	public List<Infoimg> getInfoimgList(Map<String, Object> pmap);

	public Infoimg getInfoimg(Map<String, Object> pmap);

	public void saveInfoimg(Infoimg infoimg);

	public void delInfoimg(Map<String, Object> pmap);

	public void updateInfoimg(Infoimg infoimg);
	
	public void updateInfoimg_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
