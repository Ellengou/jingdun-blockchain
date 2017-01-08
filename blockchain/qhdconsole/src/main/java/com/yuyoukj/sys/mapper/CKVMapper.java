package com.yuyoukj.sys.mapper;

import java.util.List;
import java.util.Map;
import com.yuyoukj.sys.model.CKV;

public interface CKVMapper {

	public List<CKV> getCKVList(Map<String, Object> pmap);

	public CKV getCKV(Map<String, Object> pmap);

}
