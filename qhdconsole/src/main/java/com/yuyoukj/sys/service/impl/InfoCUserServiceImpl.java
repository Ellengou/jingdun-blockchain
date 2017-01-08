package com.yuyoukj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;
import com.yuyoukj.sys.mapper.InfoCUserMapper;
import com.yuyoukj.sys.model.InfoCUser;
import com.yuyoukj.sys.model.InterRoleUser;
import com.yuyoukj.sys.service.InfoCUserService;

@Service
public class InfoCUserServiceImpl implements InfoCUserService {

	@Autowired
	private InfoCUserMapper infoCUserMapper;

	@Override
	public String createUser(InfoCUser infoUser) {

		return null;
	}

	@Override
	public InfoCUser getUser(String username) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		return infoCUserMapper.queryUserInfo(map);
	}

	@Override
	public void changepwd(Map<String, Object> pmap) {
		infoCUserMapper.changepwd(pmap);
	}

	@Override
	public InfoCUser getUser(Map<String, Object> pmap) {
		return infoCUserMapper.getUser(pmap);
	}

	@Override
	public Page<InfoCUser> getInfoCUserList(Page<InfoCUser> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getInfoCUserList(pmap);
		return page;
	}

	@Override
	public List<InfoCUser> getInfoCUserList(Map<String, Object> pmap) {
		return infoCUserMapper.getInfoCUserList(pmap);
	}

	@Override
	public InfoCUser getInfoCUser(Long userid) {
		return infoCUserMapper.getInfoCUser(userid);
	}

	@Override
	public void delInfoCUser(Long userid) {
		infoCUserMapper.delInfoCUser(userid);
		infoCUserMapper.delInterRoleUser(userid);
	}

	@Override
	public void saveInfoCUser(InfoCUser infoCUser) {

		if (infoCUser.getUserid() != null) {
			infoCUserMapper.updateInfoCUser(infoCUser);
		} else {
			infoCUserMapper.saveInfoCUser(infoCUser);
		}

	}

	@Override
	public void saveInterRoleUser(InfoCUser infoCUser) {

		infoCUserMapper.delInterRoleUser(infoCUser.getUserid());
		infoCUserMapper.saveInterRoleUser(infoCUser);
	}

	@Override
	public InterRoleUser getRoleUser(Long userid) {

		return infoCUserMapper.getRoleUser(userid);
	}

}
