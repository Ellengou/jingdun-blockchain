package com.yuyoukj.sys.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;
import com.yuyoukj.sys.model.InfoCUser;
import com.yuyoukj.sys.model.InterRoleUser;

public interface InfoCUserService {

	public String createUser(InfoCUser infoUser);

	public InfoCUser getUser(String username);

	public InfoCUser getUser(Map<String, Object> pmap);

	public void changepwd(Map<String, Object> pmap);

	public Page<InfoCUser> getInfoCUserList(Page<InfoCUser> page, Map<String, Object> pmap);

	public List<InfoCUser> getInfoCUserList(Map<String, Object> pmap);

	public InfoCUser getInfoCUser(Long userid);

	public void delInfoCUser(Long userid);

	public void saveInfoCUser(InfoCUser infoCUser);

	public void saveInterRoleUser(InfoCUser infoCUser);

	public InterRoleUser getRoleUser(Long userid);

}
