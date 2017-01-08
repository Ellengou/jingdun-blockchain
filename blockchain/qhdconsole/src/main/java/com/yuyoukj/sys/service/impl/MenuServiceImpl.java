package com.yuyoukj.sys.service.impl;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.sys.mapper.MenuMapper;
import com.yuyoukj.sys.model.Menu;
import com.yuyoukj.sys.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	private static Logger log = Logger.getLogger(MenuServiceImpl.class);

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> findAllList(Map<String, Object> pmap) {

		return menuMapper.getAllMenu(pmap);
	}

}
