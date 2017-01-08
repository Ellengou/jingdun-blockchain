package com.yuyoukj.sys.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;
import com.yuyoukj.sys.mapper.RoleMapper;
import com.yuyoukj.sys.model.Role;
import com.yuyoukj.sys.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public Page<Role> getRoleList(Page<Role> page, Map<String, Object> pmap) {

		pmap.put("page", page);
		getRoleList(pmap);
		return page;
	}

	@Override
	public List<Role> getRoleList(Map<String, Object> pmap) {

		return roleMapper.getRoleList(pmap);
	}

	@Override
	public Role getRole(Long rid) {
		return roleMapper.getRole(rid);
	}

	@Override
	public void delRole(Long rid) {
		roleMapper.delRole(rid);

	}

	@Override
	public void saveRole(Role role) {

		if (role.getRid() != null) {
			roleMapper.updateRole(role);
		} else {
			roleMapper.saveRole(role);
		}
	}

	@Override
	public void addCheckedNodes(Map<String, Object> pmap) {
		roleMapper.addCheckedNodes(pmap);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return roleMapper.checkSname(pmap);
	}

	@Override
	public void delCheckedNodes(Long rid) {
		roleMapper.delCheckedNodes(rid);

	}

	@Override
	public void delInterRoleUser(Long rid) {
		roleMapper.delInterRoleUser(rid);

	}

	@Override
	public void saveFunRole(Map<String, Object> pmap) {

		roleMapper.saveFunRole(pmap);
	}

}
