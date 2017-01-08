package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.mapper.qhd.CkvMapper;
import com.yuyoukj.model.qhd.Ckv;
import com.yuyoukj.service.CkvService;

@Service
public class CkvServiceImpl implements CkvService {

	@Autowired
	private CkvMapper ckvMapper;

	@Override
	public List<Ckv> getAddressList(Map<String, Object> pmap) {

		return ckvMapper.getAddressList(pmap);
	}

	@Override
	public Ckv getAddress(Map<String, Object> pmap) {
		return ckvMapper.getAddress(pmap);
	}

	@Override
	public Ckv getCkv(Map<String, Object> pmap) {
		return ckvMapper.getCkv(pmap);
	}

	@Override
	public List<Ckv> getCkvList(Map<String, Object> pmap) {
		return ckvMapper.getCkvList(pmap);
	}

	@Override
	public String getCvalue(Map<String, Object> pmap) {
		return ckvMapper.getCvalue(pmap);
	}

	@Override
	public List<Ckv> getProvinceList(Map<String, Object> pmap) {
		return ckvMapper.getProvinceList(pmap);
	}
}
