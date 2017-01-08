package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;
import com.yuyoukj.model.qhd.Infopayconfig;

public interface InfopayconfigMapper {
	public Infopayconfig getInfopayconfig(Map<String, Object> pmap);

	public List<Infopayconfig> getInfopayconfigList(Map<String, Object> pmap);

	public void saveInfopayconfig(Infopayconfig infopayconfig);

	public void updateInfopayconfig(Infopayconfig infopayconfig);

	public void updateInfopayconfig_map(Map<String, Object> pmap);

	public void delInfopayconfig(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
