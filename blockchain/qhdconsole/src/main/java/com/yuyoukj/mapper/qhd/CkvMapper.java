package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;
import com.yuyoukj.model.qhd.Ckv;

public interface CkvMapper {

	public Ckv getAddress(Map<String, Object> pmap);

	public List<Ckv> getAddressList(Map<String, Object> pmap);

	public List<Ckv> getCkvList(Map<String, Object> pmap);

	public String getCvalue(Map<String, Object> pmap);

	public List<Ckv> getProvinceList(Map<String, Object> pmap);

	public Ckv getCkv(Map<String, Object> pmap);

	public List<Ckv> getSearchList(Map<String, Object> pmap);

}
