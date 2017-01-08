package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;

import com.yuyoukj.model.qhd.Infopayconfig;

public interface InfopayconfigService {

	public Page<Infopayconfig> getInfopayconfigList(Page<Infopayconfig> page, Map<String, Object> pmap);

	public List<Infopayconfig> getInfopayconfigList(Map<String, Object> pmap);

	public Infopayconfig getInfopayconfig(Map<String, Object> pmap);

	public void saveInfopayconfig(Infopayconfig infopayconfig);

	public void delInfopayconfig(Map<String, Object> pmap);

	public void updateInfopayconfig(Infopayconfig infopayconfig);
	
	public void updateInfopayconfig_map(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
