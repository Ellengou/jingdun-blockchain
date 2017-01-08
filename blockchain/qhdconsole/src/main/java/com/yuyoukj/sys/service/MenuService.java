package com.yuyoukj.sys.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.sys.model.Menu;

public interface MenuService {

	public List<Menu> findAllList(Map<String, Object> pmap);

}
