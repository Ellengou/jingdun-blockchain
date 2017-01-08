package com.yuyoukj.sys.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;
import com.yuyoukj.sys.model.Role;

public interface RoleService {

	public Page<Role> getRoleList(Page<Role> page, Map<String, Object> pmap);

	public List<Role> getRoleList(Map<String, Object> pmap);

	public Role getRole(Long rid);

	public void delRole(Long rid);

	public void saveRole(Role role);

	public void addCheckedNodes(Map<String, Object> pmap);

	public void delCheckedNodes(Long rid);

	public void delInterRoleUser(Long rid);

	public Integer checkSname(Map<String, Object> pmap);

	public void saveFunRole(Map<String, Object> pmap);

}
