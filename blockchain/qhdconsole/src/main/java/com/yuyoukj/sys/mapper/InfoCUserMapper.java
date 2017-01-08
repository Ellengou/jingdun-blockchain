package com.yuyoukj.sys.mapper;

import java.util.List;
import java.util.Map;
import com.yuyoukj.sys.model.InfoCUser;
import com.yuyoukj.sys.model.InterRoleUser;

public interface InfoCUserMapper {

	public InfoCUser queryUserInfo(Map<String, Object> map);

	public void changepwd(Map<String, Object> pmap);

	public InfoCUser getUser(Map<String, Object> pmap);

	public List<InfoCUser> getInfoCUserList(Map<String, Object> pmap);

	public InfoCUser getInfoCUser(Long userid);

	public void saveInfoCUser(InfoCUser infocuser);

	public void delInfoCUser(Long userid);

	public void updateInfoCUser(InfoCUser infocuser);

	public void saveInterRoleUser(InfoCUser infoCUser);

	public void delInterRoleUser(Long userid);

	public InterRoleUser getRoleUser(Long userid);

	public List<InfoCUser> getInfoCUserRoleList(Map<String, Object> pmap);

	public void saveInterRoleUser(Map<String, Object> pmap);

	public Integer checkInfoCUser(Map<String, Object> pmap);

}
