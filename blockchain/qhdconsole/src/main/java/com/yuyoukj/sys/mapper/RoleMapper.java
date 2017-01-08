package com.yuyoukj.sys.mapper;

import java.util.List;
import java.util.Map;
import com.yuyoukj.sys.model.Role;

public interface RoleMapper {

	public List<Role> getRoleList(Map<String, Object> map);

	public void updateRole(Role role);

	public void saveRole(Role role);

	public void delRole(Long id);

	public Role getRole(Long id);

	public void addCheckedNodes(Map<String, Object> pmap);

	public void delCheckedNodes(Long rid);

	public void delInterRoleUser(Long rid);

	public Integer checkSname(Map<String, Object> pmap);

	public void saveFunRole(Map<String, Object> pmap);

}
