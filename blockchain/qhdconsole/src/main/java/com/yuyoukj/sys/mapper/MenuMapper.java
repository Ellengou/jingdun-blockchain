package com.yuyoukj.sys.mapper;

import java.util.List;
import java.util.Map;
import com.yuyoukj.sys.model.Menu;

public interface MenuMapper {

	public List<Menu> getAllMenu(Map<String, Object> map);

	public List<Menu> getMenuListT(Menu menu);

}
