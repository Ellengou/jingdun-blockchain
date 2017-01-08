package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;
import com.yuyoukj.mapper.qhd.InfoitemMapper;
import com.yuyoukj.mapper.qhd.InfotradeMapper;
import com.yuyoukj.mapper.qhd.InfouserMapper;
import com.yuyoukj.model.qhd.Infotrade;
import com.yuyoukj.model.qhd.Infouser;
import com.yuyoukj.service.InfotradeService;
import com.yuyoukj.util.weixin.http.util.StrUtils;

@Service
public class InfotradeServiceImpl implements InfotradeService {
	@Autowired
	private InfotradeMapper infotradeMapper;
	@Autowired
	private InfouserMapper infouserMapper;
	@Autowired
	private InfoitemMapper infoitemMapper;

	@Override
	public Page<Infotrade> getInfotradeList(Page<Infotrade> page, Map<String, Object> pmap) {
		pmap.put("page", page);
		getInfotradeList(pmap);
		return page;
	}

	@Override
	public List<Infotrade> getInfotradeList(Map<String, Object> pmap) {

		List<Infotrade> infotradelist = infotradeMapper.getInfotradeList(pmap);
		for (Infotrade obj1 : infotradelist) {
			if (!StrUtils.isBlank(obj1.getUserids())) {
				String userids[] = obj1.getUserids().split(",");
				StringBuilder sb = new StringBuilder();
				for (String userid : userids) {
					pmap.clear();
					pmap.put("userid", userid);
					Infouser infouser = infouserMapper.getInfouser(pmap);
					if (infouser != null) {
						sb.append(infouser.getUsername()).append(",");
					}
				}
				obj1.setUsernames(sb.toString());
			}
		}

		return infotradelist;
	}

	@Override
	public Infotrade getInfotrade(Map<String, Object> pmap) {
		return infotradeMapper.getInfotrade(pmap);
	}

	@Override
	public void delInfotrade(Map<String, Object> pmap) {
		infotradeMapper.delInfotrade(pmap);
	}

	@Override
	public void saveInfotrade(Infotrade infotrade) {
		if (infotrade.getTradeid() == null) {
			infotradeMapper.saveInfotrade(infotrade);
		} else {
			infotradeMapper.updateInfotrade(infotrade);
		}
	}

	@Override
	public void updateInfotrade_map(Map<String, Object> pmap) {

		infotradeMapper.updateInfotrade_map(pmap);

	}

	@Override
	public void updateInfotrade(Infotrade infotrade) {

		infotradeMapper.updateInfotrade(infotrade);

	}

	@Override
	public Integer checkSname(Map<String, Object> pmap) {
		return infotradeMapper.checkSname(pmap);
	}

}
