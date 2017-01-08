package com.yuyoukj.sys.mapper;

import java.util.List;
import java.util.Map;
import com.yuyoukj.sys.model.ConfCconfig;

public interface ConfCconfigMapper {

	public List<ConfCconfig> findAllList(Map<String, Object> map);

}
