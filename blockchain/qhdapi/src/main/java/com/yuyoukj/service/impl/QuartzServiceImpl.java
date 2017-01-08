package com.yuyoukj.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import www.yuyoukj.third.ratio.Getratio;
import www.yuyoukj.third.ratio.Retratio;
import com.yuyoukj.mapper.qhd.ConfconfigMapper;
import com.yuyoukj.mapper.qhd.ConfratioMapper;
import com.yuyoukj.mapper.qhd.InfoitemMapper;
import com.yuyoukj.mapper.qhd.InfotradeMapper;
import com.yuyoukj.model.qhd.Confconfig;
import com.yuyoukj.model.qhd.Confratio;
import com.yuyoukj.model.qhd.Infoitem;
import com.yuyoukj.model.qhd.Infotrade;
import com.yuyoukj.service.QuartzService;
import com.yuyoukj.util.DateUtil;
import com.yuyoukj.util.YyDateUtil;
import com.yuyoukj.util.constants.WebConstants;

public class QuartzServiceImpl implements QuartzService {

	private static Logger log = Logger.getLogger(QuartzServiceImpl.class);
	@Autowired
	private ConfratioMapper confratioMapper;
	@Autowired
	private ConfconfigMapper confconfigMapper;
	@Autowired
	private InfoitemMapper infoitemMapper;
	@Autowired
	private InfotradeMapper infotradeMapper;

	@Override
	public void updateratio() {
		log.error("===updateratio start "
				+ YyDateUtil.long2DateString(System.currentTimeMillis(), "yyyy/MM/dd hh:mm:dd"));

		Map<String, Object> pmap = new HashMap<String, Object>();

		Long sdate = Long.valueOf(DateUtil.long2DateString(System.currentTimeMillis(), "yyyyMMdd"));

		//		if (confratiolist != null && confratiolist.size() == 2) {
		//			return;
		//		}

		//
		//		if (confratiolist != null && confratiolist.size() != 0) {
		//			pmap.clear();
		//			pmap.put("sdate", sdate);
		//			confratioMapper.delConfratio_map(pmap);
		//		}

		Retratio ret = Getratio.getration();
		if (ret != null) {
			Confratio obj1 = null;
			try {
				////货币名称	现汇买入价	现钞买入价	现汇卖出价	
				//现钞卖出价	中行折算价	发布日期	发布时间

				pmap.clear();
				pmap.put("sdate", sdate);
				pmap.put("stype", 0);
				obj1 = confratioMapper.getConfratio(pmap);
				if (obj1 == null) {
					obj1 = new Confratio();
					obj1.setSdate(sdate);
					Double ratio = 100 * 100 / Double.valueOf(ret.getD4());
					obj1.setRatio(BigDecimal.valueOf(ratio).setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
					obj1.setStype(0); //需求类型：0-CNY；1-USD
					obj1.setIntype(1);
					obj1.setSfrom(WebConstants.RATIO_FROM);
					obj1.setGetdate(System.currentTimeMillis());
					obj1.setCreatedate(System.currentTimeMillis());
					obj1.setUpdatedate(obj1.getCreatedate());
					confratioMapper.saveConfratio(obj1);
				} else {
					obj1.setSdate(sdate);
					Double ratio = 100 * 100 / Double.valueOf(ret.getD4());
					obj1.setRatio(BigDecimal.valueOf(ratio).setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
					obj1.setStype(0); //需求类型：0-CNY；1-USD
					obj1.setIntype(1);
					obj1.setSfrom(WebConstants.RATIO_FROM);
					obj1.setGetdate(System.currentTimeMillis());
					obj1.setUpdatedate(obj1.getCreatedate());
					confratioMapper.updateConfratio(obj1);
				}

				pmap.clear();
				pmap.put("sdate", sdate);
				pmap.put("stype", 1);
				obj1 = confratioMapper.getConfratio(pmap);
				if (obj1 == null) {
					obj1 = new Confratio();
					obj1.setSdate(sdate);
					obj1.setRatio(Double.valueOf(ret.getD2()));
					obj1.setStype(1); //需求类型：0-CNY；1-USD
					obj1.setIntype(0);
					obj1.setSfrom(WebConstants.RATIO_FROM);
					obj1.setGetdate(System.currentTimeMillis());
					obj1.setCreatedate(System.currentTimeMillis());
					obj1.setUpdatedate(obj1.getCreatedate());
					confratioMapper.saveConfratio(obj1);
				} else {
					obj1.setSdate(sdate);
					obj1.setRatio(Double.valueOf(ret.getD2()));
					obj1.setStype(1); //需求类型：0-CNY；1-USD
					obj1.setIntype(0);
					obj1.setSfrom(WebConstants.RATIO_FROM);
					obj1.setGetdate(System.currentTimeMillis());
					obj1.setUpdatedate(obj1.getCreatedate());
					confratioMapper.updateConfratio(obj1);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void tdayinfoitem() {
		log.error("===tdayinfoitem start "
				+ YyDateUtil.long2DateString(System.currentTimeMillis(), "yyyy/MM/dd hh:mm:dd"));
		//处理T day订单自动过期

		Map<String, Object> pmap = new HashMap<String, Object>();
		int tday = -1;

		//
		pmap.clear();
		pmap.put("cfield", WebConstants.CFIELD_TDAY);
		Confconfig confconfig = confconfigMapper.getConfconfig(pmap);
		if (confconfig != null && confconfig.getCkey() != null) {
			try {
				tday = Integer.valueOf(confconfig.getCkey()).intValue();
			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
		}

		if (tday == -1) {
			return;
		}

		//
		pmap.clear();
		pmap.put("sflag", 0); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		pmap.put("sstatus", 99);
		//0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；98-登记；99-登记并转帐；96-超时系统取消未退款；97-超时系统取消已退款； 94-用户主动取消未退款；95-用户主动取消已退款
		pmap.put("createdate", (System.currentTimeMillis() - tday * WebConstants.DAY_UNIXTIME));
		List<Infoitem> itemlist = infoitemMapper.getInfoitemList(pmap);
		if (itemlist == null || itemlist.isEmpty()) {
			return;
		}

		//一条条办理退款（退款暂手工）
		for (Infoitem obj1 : itemlist) {
			pmap.clear();
			pmap.put("itemid", obj1.getItemid());
			pmap.put("sflag", 1); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
			pmap.put("sstatus", 2); //等待平台转帐
			pmap.put("updatedate", System.currentTimeMillis());
			infoitemMapper.updateInfoitem_map(pmap);
		}
	}

	@Override
	public void nopayinfotrader() {
		log.error("===nopayinfotrader start "
				+ YyDateUtil.long2DateString(System.currentTimeMillis(), "yyyy/MM/dd hh:mm:dd"));

		Map<String, Object> pmap = new HashMap<String, Object>();

		//0-等待转账；2-等待系统转账；3-完成；21-系统已转给需求方；22-需求已转给应答方；98-登记；99-登记并转帐；
		//96-超时系统取消未退款；97-超时系统取消已退款； 94-用户主动取消未退款；95-用户主动取消已退款；200-支付失败交易取消；
		pmap.clear();
		pmap.put("sstatus", 0);
		pmap.put("nowdate", System.currentTimeMillis() - WebConstants.NOPAY_UNIXTIME);
		List<Infotrade> tradelist = infotradeMapper.getInfotradeList(pmap);

		// 取消交易，恢复需求
		if (tradelist != null && tradelist.size() > 0) {
			for (Infotrade infotrade : tradelist) {
				pmap.clear();
				pmap.put("tradeid", infotrade.getTradeid());
				pmap.put("sstatus", 96);
				pmap.put("updatedate", System.currentTimeMillis());
				infotradeMapper.updateInfotrade_map(pmap);

				try {
					String[] items = infotrade.getItemids().split(",");
					if (items != null && items.length > 0) {
						for (String itemid : items) {
							pmap.clear();
							pmap.put("itemid", itemid);
							pmap.put("sstatus", 99);
							pmap.put("updatedate", System.currentTimeMillis());
							infoitemMapper.updateInfoitem_map(pmap);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}

	}

}
