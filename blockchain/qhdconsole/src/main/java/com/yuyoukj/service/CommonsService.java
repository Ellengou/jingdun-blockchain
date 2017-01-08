package com.yuyoukj.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.sys.model.CKV;

public interface CommonsService {

	public List<CKV> getCKVList(Map<String, Object> pmap);

	//	public void saveposition(Map<String, Object> pmap);

	//	public void saveadsposition(Map<String, Object> pmap);
}
