package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Confarea;

public interface ConfareaService {

	public Page<Confarea> getConfareaList(Page<Confarea> page, Map<String, Object> pmap);

	public List<Confarea> getConfareaList(Map<String, Object> pmap);

	public Confarea getConfarea(Map<String, Object> pmap);

	public void saveConfarea(Confarea confarea);

	public void delConfarea(Map<String, Object> pmap);

	public void updateConfarea(Confarea confarea);
	
	public void updateConfarea_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
