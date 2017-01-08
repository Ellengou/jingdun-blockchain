package com.yuyoukj.interservice.inter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import www.yuyoukj.third.paypal.PayPalVerifyPayment;
import com.alibaba.fastjson.JSON;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Batch_transfer;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event_obj;
import com.pingplusplus.model.Recipients_obj;
import com.pingplusplus.model.Transfer;
import com.pingxx.example.Main;
import com.pingxx.example.TransferExample;
import com.yuyoukj.ao.common.util.UuidUtils;
import com.yuyoukj.ao.interfaceframework.exception.MyRcUtil;
import com.yuyoukj.ao.interfaceframework.exception.RcCode;
import com.yuyoukj.ao.interfaceframework.exception.RcException;
import com.yuyoukj.async.service.MyAsyncService;
import com.yuyoukj.interservice.ApiService;
import com.yuyoukj.mapper.qhd.ConfconfigMapper;
import com.yuyoukj.mapper.qhd.ConfratioMapper;
import com.yuyoukj.mapper.qhd.InfoimgMapper;
import com.yuyoukj.mapper.qhd.InfoitemMapper;
import com.yuyoukj.mapper.qhd.InfopayconfigMapper;
import com.yuyoukj.mapper.qhd.InfototalMapper;
import com.yuyoukj.mapper.qhd.InfototalorderMapper;
import com.yuyoukj.mapper.qhd.InfotradeMapper;
import com.yuyoukj.mapper.qhd.InfouserMapper;
import com.yuyoukj.mapper.qhd.LogpasswdMapper;
import com.yuyoukj.mapper.qhd.LogscodeMapper;
import com.yuyoukj.mapper.qhd.LogtokenMapper;
import com.yuyoukj.model.api.Moneylimit;
import com.yuyoukj.model.api.TmpRet;
import com.yuyoukj.model.qhd.Confconfig;
import com.yuyoukj.model.qhd.Confratio;
import com.yuyoukj.model.qhd.Infoimg;
import com.yuyoukj.model.qhd.Infoitem;
import com.yuyoukj.model.qhd.Infopayconfig;
import com.yuyoukj.model.qhd.Infototal;
import com.yuyoukj.model.qhd.Infototalorder;
import com.yuyoukj.model.qhd.Infotrade;
import com.yuyoukj.model.qhd.Infouser;
import com.yuyoukj.model.qhd.Logscode;
import com.yuyoukj.model.qhd.Logtoken;
import com.yuyoukj.response.ApiResponse;
import com.yuyoukj.sdk.BaseSDKService;
import com.yuyoukj.sdk.RequestParam;
import com.yuyoukj.third.HttpSenderTest;
import com.yuyoukj.util.CommonUtil;
import com.yuyoukj.util.DateUtil;
import com.yuyoukj.util.SMSUtil;
import com.yuyoukj.util.StrUtils;
import com.yuyoukj.util.constants.WebConstants;
import com.yuyoukj.util.third.pingpp.ChargeEx;
import com.yuyoukj.util.third.pingpp.PayConstants;

public class ApiServiceImpl extends BaseSDKService implements ApiService {
	
	private static String[][] getOrder(String[][] array) {
		for (int j = 0; j < array.length; j++) {
			for (int bb = 0; bb < array.length - 1; bb++) {
				String[] ss;
				int a1 = Integer.valueOf(array[bb][1]); //转化成int型比较大小
				int a2 = Integer.valueOf(array[bb + 1][1]);
				if (a1 > a2) {
					ss = array[bb];
					array[bb] = array[bb + 1];
					array[bb + 1] = ss;
				}
			}
		}
		return array;
	}
	
	private Logger log = Logger.getLogger(ApiServiceImpl.class);
	@Autowired
	private ConfconfigMapper confconfigMapper;
	@Autowired
	private ConfratioMapper confratioMapper;
	@Autowired
	private InfoimgMapper infoimgMapper;
	@Autowired
	private InfoitemMapper infoitemMapper;
	@Autowired
	private InfopayconfigMapper infopayconfigMapper;
	@Autowired
	private InfotradeMapper infotradeMapper;
	@Autowired
	private InfouserMapper infouserMapper;
	@Autowired
	private LogpasswdMapper logpasswdMapper;
	@Autowired
	private LogscodeMapper logscodeMapper;
	@Autowired
	private LogtokenMapper logtokenMapper;
	@Autowired
	private InfototalMapper infototalMapper;
	@Autowired
	private InfototalorderMapper infototalorderMapper;
	
	@Autowired
	private MyAsyncService myAsyncService;
	
	public ApiServiceImpl() throws Exception {
		super();
	}
	
	public ApiServiceImpl(String appkey, String secret) throws Exception {
		super(appkey, secret);
	}
	
	@Override
	public ApiResponse exit(RequestParam param) throws RcException {
		ApiResponse objResponse = null;
		
		exit_fun(param.getUserid(), param.getToken());
		
		return objResponse;
		
	}
	
	private void exit_fun(Long userid, String token) {
		tokenlose(userid, token);
	}
	
	private BigDecimal gettotalcost(BigDecimal cost) {
		BigDecimal totalcost = BigDecimal.valueOf(cost.doubleValue() * (1 + WebConstants.COST_COMMISSION));
		totalcost = totalcost.setScale(2, BigDecimal.ROUND_DOWN);
		
		return totalcost;
	}
	
	@Override
	public ApiResponse iconupload(RequestParam param) throws RcException {
		ApiResponse objResponse = new ApiResponse();
		
		iconupload_fun(param);
		
		return objResponse;
	}
	
	private void iconupload_fun(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		List<Infoimg> infoimgList = imgupload(param);
		
		if (infoimgList != null && infoimgList.size() == 1) {
			pmap.clear();
			pmap.put("userid", param.getUserid());
			pmap.put("iconid", infoimgList.get(0).getImgid());
			pmap.put("icon", infoimgList.get(0).getImgurl());
			pmap.put("updatedate", System.currentTimeMillis());
			infouserMapper.updateInfouser_map(pmap);
		}
	}
	
	private List<Infoimg> imgupload(RequestParam param) {
		String imgurl = null;
		List<Infoimg> infoimgList = new ArrayList<Infoimg>();
		
		// 处理图片文件。复制图片，删除源图片
		File[] imgfile = param.getFile();
		if (imgfile != null && imgfile.length > 0) {
			for (File uploadfile : imgfile) {
				if (uploadfile != null && uploadfile.exists()) {
					String suploadPath = WebConstants.DEFAULT_IMG_PATH + WebConstants.JOIN_URL
							+ DateUtil.getCurrentDate("yyyyMM") + WebConstants.JOIN_URL + DateUtil.getCurrentDate("dd")
							+ WebConstants.JOIN_URL + DateUtil.getCurrentDate("HH") + WebConstants.JOIN_URL;
					String realPath = WebConstants.DEFAULT_FILE_DIRECTORY + suploadPath;
					
					String targetname = System.currentTimeMillis() + "."
							+ uploadfile.getName().substring(uploadfile.getName().lastIndexOf(".") + 1);
					try {
						if (CommonUtil.uploadFile(uploadfile, realPath, targetname, true)) {
							//
							imgurl = WebConstants.DEFAULT_URL_HTTP + suploadPath + targetname;
							
							Infoimg infoimg = new Infoimg();
							infoimg.setImgurl(imgurl);
							infoimg.setCreatedate(System.currentTimeMillis());
							
							infoimgMapper.saveInfoimg(infoimg);
							infoimgList.add(infoimg);
							
							imgurl = null;
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		imgfile = null;
		param.setFile(null);
		
		return infoimgList;
	}
	
	@Override
	public ApiResponse itemadd(RequestParam param) throws RcException {
		
		ApiResponse objResponse = new ApiResponse();
		
		TmpRet tmpRet = itemadd_fun(param);
		if (tmpRet == null) {
			MyRcUtil.MyRcException(RcCode.ITEM_INVALID);
		} else {
			if (param.getStype() == 1) { //要换美金，转人民币
				objResponse.setPaystype(tmpRet.getPaystype());
				objResponse.setPayorder(tmpRet.getPayorder());
				objResponse.setPaycost(tmpRet.getPaycost());
				objResponse.setChargedata(tmpRet.getChargedata());
			} else { //要换人民币，转美金
				objResponse.setPaystype(tmpRet.getPaystype());
				objResponse.setPayorder(tmpRet.getPayorder());
				objResponse.setPaycost(tmpRet.getPaycost());
			}
		}
		
		return objResponse;
	}
	
	@Override
	public ApiResponse itemdel(RequestParam param) throws RcException {
		
		ApiResponse objResponse = new ApiResponse();
		
		Integer ret = itemdel_fun(param);
		if (ret == 1) {
			MyRcUtil.MyRcException(RcCode.ITEM_ERR);
		} else {
			
		}
		
		return objResponse;
	}
	
	private Integer itemdel_fun(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		Integer ret = 0;
		
		if (param.getItemid() == null) {
			return 1;
		}
		
		pmap.clear();
		pmap.put("itemid", param.getItemid());
		Infoitem infoitem = infoitemMapper.getInfoitem(pmap);
		if (infoitem == null || infoitem.getSstatus() != 99 || infoitem.getSflag() != 0) {
			return 1;
		}
		
		pmap.clear();
		pmap.put("itemid", param.getItemid());
		pmap.put("sflag", 2); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		pmap.put("sstatus", 2); //0-等待转账；2-等待系统转帐；3-完成；98-登记；99-登记并转帐；96-超时系统取消未退款；97-超时系统取消已退款； 94-用户主动取消未退款；95-用户主动取消已退款；
		pmap.put("updatedate", System.currentTimeMillis());
		infoitemMapper.updateInfoitem_map(pmap);
		
		return ret;
	}
	
	private TmpRet itemadd_fun(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		TmpRet tmpret = null;
		
		if (param.getStype() == null) {
			return null;
		}
		
		Integer stype = param.getStype();
		Integer intype = 0;
		if (stype == 0) {
			intype = 1;
		} else {
			intype = 0;
		}
		
		//
		pmap.clear();
		pmap.put("sdate", DateUtil.long2DateString(System.currentTimeMillis(), "yyyyMMdd"));
		pmap.put("stype", stype);
		pmap.put("intype", intype);
		Confratio confratio = confratioMapper.getConfratio(pmap);
		if (confratio == null) {
			return null;
		}
		
		//保存需求
		Infoitem infoitem = new Infoitem();
		infoitem.setUserid(param.getUserid());
		infoitem.setStype(stype);
		infoitem.setMoney(param.getMoney());
		infoitem.setIntype(intype);
		infoitem.setRatio(confratio.getRatio());
		infoitem.setCost(infoitem.getMoney().multiply(BigDecimal.valueOf(confratio.getRatio() / 100.0)));
		infoitem.setCost(infoitem.getCost().setScale(2, BigDecimal.ROUND_DOWN));
		infoitem.setTotalcost(gettotalcost(infoitem.getCost()));
		infoitem.setSflag(0);
		infoitem.setSstatus(98);
		infoitem.setCreatedate(System.currentTimeMillis());
		infoitem.setUpdatedate(infoitem.getCreatedate());
		infoitemMapper.saveInfoitem(infoitem);
		
		Double costs = 0.0;
		switch (intype) { //根据不同的兑换类型分别获取不同的支付凭证
			case 0: //换美金，花费人民币。ping++
				// 获取支付凭证
				costs = infoitem.getTotalcost().doubleValue() * 100;
				//支付类型：0-alipay; 1-paypal;
				//类型：0-itemid；1-tradeid；
				tmpret = new TmpRet();
				tmpret = togetcharge(param, costs.longValue(), infoitem.getItemid(), 0, 0);
				tmpret.setPaystype(intype);
				tmpret.setPaycost(infoitem.getTotalcost());
				
				break;
			case 1:
				costs = infoitem.getTotalcost().doubleValue() * 100;
				
				//支付类型：0-alipay; 1-paypal;
				//类型：0-itemid；1-tradeid；
				tmpret = new TmpRet();
				tmpret = togetpaypal(param, costs.longValue(), infoitem.getItemid(), 0, 1);
				tmpret.setPaystype(intype);
				tmpret.setPaycost(infoitem.getTotalcost());
				
				break;
			default:
				break;
		}
		
		return tmpret;
		
	}
	
	@Override
	public ApiResponse itemlist(RequestParam param) throws RcException {
		ApiResponse objResponse = new ApiResponse();
		
		TmpRet tmpRet = itemlist_fun(param);
		if (tmpRet == null) {
			//			MyRcUtil.MyRcException(RcCode.ITEM_INVALID);
		} else {
			objResponse.setItemlist(tmpRet.getItemlist());
			objResponse.setTotalpage(tmpRet.getTotalpage());
		}
		
		return objResponse;
	}
	
	private TmpRet itemlist_fun(RequestParam param) {
		
		TmpRet tmpret = null;
		
		if (param.getSflag() == null) {
			param.setSflag(0); // 默认
		}
		if (param.getStype() == null) {
			param.setStype(0); // 默认
		}
		
		switch (param.getSflag()) {
			case 0:
				tmpret = new TmpRet();
				if (param.getStype() == 0) {
					param.setStype(1);
				} else {
					param.setStype(0);
				}
				tmpret = itemlist_fun0(param);
				
				break;
			case 1:
				tmpret = new TmpRet();
				tmpret = itemlist_fun1(param);
				
				break;
			case 2:
				tmpret = new TmpRet();
				tmpret = itemlist_fun2(param);
				
				break;
			default:
				break;
		}
		
		return tmpret;
	}
	
	private TmpRet itemlist_fun0(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		TmpRet tmpret = null;
		
		pmap.clear();
		pmap.put("sflag", 0); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		pmap.put("sstatus", 99); //0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；98-登记；99-登记并转帐；
		pmap.put("stype", param.getStype());
		if (!StrUtils.isBlank(param.getKeyword())) {
			pmap.put("keyword", param.getKeyword());
		}
		Integer totalcount = infoitemMapper.getInfoitemList_simplecount(pmap);
		tmpret = topage(param.getPagenum(), param.getPagesize(), totalcount);
		if (tmpret == null) {
			return null;
		}
		
		pmap.clear();
		pmap.put("sflag", 0); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		pmap.put("sstatus", 99); //0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；98-登记；99-登记并转帐；
		pmap.put("stype", param.getStype());
		if (!StrUtils.isBlank(param.getKeyword())) {
			pmap.put("keyword", param.getKeyword());
		}
		pmap.put("star", tmpret.getStar());
		pmap.put("size", tmpret.getSize());
		pmap.put("limit", 1);
		List<Infoitem> itemlist = infoitemMapper.getInfoitemList_simple(pmap);
		if (itemlist != null && !itemlist.isEmpty()) {
			tmpret.setItemlist(itemlist);
		}
		
		return tmpret;
	}
	
	private TmpRet itemlist_fun1(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		TmpRet tmpret = null;
		
		pmap.clear();
		pmap.put("sflagbl", 1);
		pmap.put("sflag", 11); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		pmap.put("sstatus", 99); //0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；98-登记；99-登记并转帐；
		pmap.put("userid", param.getUserid());
		Integer totalcount = infoitemMapper.getInfoitemList_simplecount(pmap);
		tmpret = topage(param.getPagenum(), param.getPagesize(), totalcount);
		if (tmpret == null) {
			return null;
		}
		
		pmap.clear();
		pmap.put("sflagbl", 1);
		pmap.put("sflag", 11); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		pmap.put("sstatus", 99); //0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；98-登记；99-登记并转帐；
		pmap.put("userid", param.getUserid());
		pmap.put("star", tmpret.getStar());
		pmap.put("size", tmpret.getSize());
		pmap.put("limit", 1);
		List<Infoitem> itemlist = infoitemMapper.getInfoitemList_simple(pmap);
		if (itemlist != null && !itemlist.isEmpty()) {
			tmpret.setItemlist(itemlist);
		}
		
		return tmpret;
	}
	
	private TmpRet itemlist_fun2(RequestParam param) {
		
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		TmpRet tmpret = null;
		
		pmap.clear();
		pmap.put("sflag", 0); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		//pmap.put("sstatus", 3); //0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；98-登记；99-登记并转帐；
		pmap.put("buyuserid", param.getUserid());
		Integer totalcount = infoitemMapper.getInfoitemList_tradecount(pmap);
		tmpret = topage(param.getPagenum(), param.getPagesize(), totalcount);
		if (tmpret == null) {
			return null;
		}
		
		pmap.clear();
		pmap.put("sflag", 0); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		//pmap.put("sstatus", 3); //0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；98-登记；99-登记并转帐；
		pmap.put("buyuserid", param.getUserid());
		pmap.put("star", tmpret.getStar());
		pmap.put("size", tmpret.getSize());
		pmap.put("limit", 1);
		List<Infoitem> itemlist = infoitemMapper.getInfoitemList_trade(pmap);
		if (itemlist != null && !itemlist.isEmpty()) {
			tmpret.setItemlist(itemlist);
		}
		
		return tmpret;
	}
	
	@Override
	public ApiResponse login(RequestParam param) throws RcException {
		
		ApiResponse objResponse = new ApiResponse();
		TmpRet tmpret = login_fun(param);
		if (tmpret != null) {
			tmpret.getUserdata().setPassword(null);
			objResponse.setUserdata(tmpret.getUserdata());
			objResponse.setToken(tokenget(tmpret.getUserdata().getUserid()));
			objResponse.setMoneylist(tmpret.getMoneylist());
		}
		
		return objResponse;
	}
	
	private TmpRet login_fun(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		TmpRet tmpret = null;
		
		pmap.clear();
		pmap.put("username", param.getUsername());
		pmap.put("password", param.getPassword());
		if (infouserMapper.checkSname(pmap) == 0) {
			MyRcUtil.MyRcException(RcCode.INFOUSER_INVALID);
		} else {
			tmpret = new TmpRet();
			
			pmap.clear();
			pmap.put("username", param.getUsername());
			pmap.put("password", param.getPassword());
			Infouser infouser = infouserMapper.getInfouser(pmap);
			
			pmap.clear();
			pmap.put("userid", infouser.getUserid());
			List<Infopayconfig> payconfiglist = infopayconfigMapper.getInfopayconfigList(pmap);
			if (payconfiglist != null && payconfiglist.size() > 0) {
				infouser.setPayconfiglist(payconfiglist);
			}
			
			tmpret.setUserdata(infouser);
			
			// 追加金额限制
			List<Moneylimit> moneylimit = new ArrayList<Moneylimit>();
			Moneylimit moneylimit_obj = null;
			
			//
			try {
				Confconfig confconfig = null;
				
				//
				moneylimit_obj = new Moneylimit();
				moneylimit_obj.setStype(0);
				
				pmap.clear();
				pmap.put("cfield", WebConstants.MONEY_LIMIT_STYPE0_MIN);
				pmap.put("ctbname", "money_limit");
				confconfig = confconfigMapper.getConfconfig(pmap);
				moneylimit_obj.setMin(BigDecimal.valueOf(Double.valueOf(confconfig.getCkey())));
				
				pmap.clear();
				pmap.put("cfield", WebConstants.MONEY_LIMIT_STYPE0_MAX);
				pmap.put("ctbname", "money_limit");
				confconfig = confconfigMapper.getConfconfig(pmap);
				moneylimit_obj.setMax(BigDecimal.valueOf(Double.valueOf(confconfig.getCkey())));
				
				moneylimit.add(moneylimit_obj);
				
				//
				moneylimit_obj = new Moneylimit();
				moneylimit_obj.setStype(1);
				
				pmap.clear();
				pmap.put("cfield", WebConstants.MONEY_LIMIT_STYPE1_MIN);
				pmap.put("ctbname", "money_limit");
				confconfig = confconfigMapper.getConfconfig(pmap);
				moneylimit_obj.setMin(BigDecimal.valueOf(Double.valueOf(confconfig.getCkey())));
				
				pmap.clear();
				pmap.put("cfield", WebConstants.MONEY_LIMIT_STYPE1_MAX);
				pmap.put("ctbname", "money_limit");
				confconfig = confconfigMapper.getConfconfig(pmap);
				moneylimit_obj.setMax(BigDecimal.valueOf(Double.valueOf(confconfig.getCkey())));
				
				moneylimit.add(moneylimit_obj);
				
				//
				tmpret.setMoneylist(moneylimit);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
		
		return tmpret;
	}
	
	@Override
	public ApiResponse orderadd(RequestParam param) throws RcException {
		ApiResponse objResponse = new ApiResponse();
		
		TmpRet tmpRet = orderadd_fun(param);
		if (tmpRet == null) {
			MyRcUtil.MyRcException(RcCode.ITEM_INVALID);
		} else {
			if (tmpRet.getPaystype() == 0) { //要换美金，转人民币
				objResponse.setPaystype(tmpRet.getPaystype());
				objResponse.setPayorder(tmpRet.getPayorder());
				objResponse.setChargedata(tmpRet.getChargedata());
			} else { //要换人民币，转美金
				objResponse.setPaystype(tmpRet.getPaystype());
				objResponse.setPayorder(tmpRet.getPayorder());
				objResponse.setPaycost(tmpRet.getPaycost());
			}
		}
		
		return objResponse;
	}
	
	private TmpRet orderadd_fun(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		TmpRet tmpret = null;
		
		if (StrUtils.isBlank(param.getItemids())) {
			return null;
		}
		
		Integer intype = -1;
		String userids = "";
		BigDecimal cost = new BigDecimal(0);
		
		String itemids[] = URLDecoder.decode(param.getItemids()).split(",");
		List<Infoitem> itemlist = new ArrayList<Infoitem>();
		
		for (String id : itemids) {
			pmap.clear();
			pmap.put("itemid", id);
			Infoitem infoitem = infoitemMapper.getInfoitem(pmap);
			if (infoitem == null || infoitem.getSstatus() != 99) {
				return null;
			}
			itemlist.add(infoitem);
		}
		
		for (Infoitem infoitem : itemlist) {
			// 需求加锁
			pmap.clear();
			pmap.put("itemid", infoitem.getItemid());
			pmap.put("sstatus", 0);
			pmap.put("updatedate", System.currentTimeMillis());
			infoitemMapper.updateInfoitem_map(pmap);
			
			cost = cost.add(infoitem.getTotalcost());
			userids = infoitem.getUserid() + "," + userids;
			intype = infoitem.getIntype();
		}
		
		if (intype == -1 || userids.length() == 1) {
			return null;
		}
		if (intype == 1) {
			intype = 0;
		} else {
			intype = 1;
		}
		userids = userids.substring(0, userids.length() - 1);
		
		BigDecimal costs = cost.setScale(2, BigDecimal.ROUND_DOWN);
		
		//
		Infotrade infotrade = new Infotrade();
		infotrade.setItemids(param.getItemids());
		infotrade.setUserids(userids);
		infotrade.setBuyuserid(param.getUserid());
		infotrade.setCreatedate(System.currentTimeMillis());
		infotrade.setUpdatedate(infotrade.getCreatedate());
		infotrade.setSstatus(0);
		infotrade.setCost(costs);
		infotrade.setTotalcost(costs.multiply(BigDecimal.valueOf(1 + WebConstants.COST_COMMISSION)).setScale(2,
				BigDecimal.ROUND_DOWN));
		infotradeMapper.saveInfotrade(infotrade);
		
		Double totalcost = 0.0;
		switch (intype) { //根据不同的兑换类型分别获取不同的支付凭证
			case 0: //换美金，花费人民币。ping++
				// 获取支付凭证
				totalcost = infotrade.getTotalcost().doubleValue() * 100; //人民币：分
				
				//类型：0-itemid；1-tradeid；
				//支付类型：0-alipay; 1-paypal;
				tmpret = new TmpRet();
				tmpret = togetcharge(param, totalcost.longValue(), infotrade.getTradeid(), 1, 0);
				tmpret.setPaystype(intype);
				tmpret.setPaycost(infotrade.getTotalcost());
				
				break;
			case 1:
				// 获取支付凭证
				
				//支付类型：0-alipay; 1-paypal;
				//类型：0-itemid；1-tradeid；
				tmpret = new TmpRet();
				tmpret = togetpaypal(param, costs.longValue(), infotrade.getTradeid(), 1, 1);
				tmpret.setPaystype(intype);
				tmpret.setPaycost(infotrade.getTotalcost());
				
				break;
			default:
				break;
		}
		
		return tmpret;
		
	}
	
	@Override
	public ApiResponse orderpay(RequestParam param) throws RcException {
		ApiResponse objResponse = new ApiResponse();
		
		Integer ret = orderpay_fun(param);
		if (ret == 1) {
			MyRcUtil.MyRcException(RcCode.ORDER_INVALID);
		}
		
		return objResponse;
	}
	
	private Integer orderpay_fun(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		Integer ret = 0;
		Batch_transfer batchtransfer = null;
		
		if (param.getPayorder() == null || param.getStype() == null) {
			return 1;
		}
		
		pmap.clear();
		pmap.put("totalid", param.getPayorder());
		Infototal infototal = infototalMapper.getInfototal(pmap);
		if (infototal == null) {
			return 1;
		}
		
		if (infototal.getSflag() != null && infototal.getSflag().intValue() == 1) { //已处理
			return 0;
		}
		
		if (StrUtils.isBlank(param.getPaypalid())) { //ping++支付
		
			//支付结果
			Integer stype = param.getStype();
			Infototalorder infototalorder = null;
			switch (stype) {
				case 0: //支付成功
					
					pmap.clear();
					pmap.put("totalid", infototal.getTotalid());
					pmap.put("paydate", System.currentTimeMillis());
					pmap.put("sflag", 1);
					pmap.put("updatedate", System.currentTimeMillis());
					infototalMapper.updateInfototal_map(pmap);
					
					//
					pmap.clear();
					pmap.put("totalid", infototal.getTotalid());
					infototalorder = infototalorderMapper.getInfototalorder(pmap);
					if (infototalorder.getStype() == 0) { //类型：0-itemid；1-tradeid；
					
						pmap.clear();
						pmap.put("itemid", infototalorder.getTradeid());
						pmap.put("sstatus", 99);
						pmap.put("paydate", System.currentTimeMillis());
						pmap.put("updatedate", System.currentTimeMillis());
						infoitemMapper.updateInfoitem_map(pmap);
					} else { //兑换交易类型，涉及平台向双方帐户转钱
						pmap.clear();
						pmap.put("tradeid", infototalorder.getTradeid());
						Infotrade infotrade = infotradeMapper.getInfotrade(pmap);
						
						pmap.clear();
						pmap.put("tradeid", infotrade.getTradeid());
						pmap.put("sstatus", 2);
						pmap.put("paydate", System.currentTimeMillis());
						pmap.put("updatedate", System.currentTimeMillis());
						infotradeMapper.updateInfotrade_map(pmap);
						
						//
						Long orderNo = -1L;
						Integer exchange_stype = -1;
						Integer exchange_intype = -1;
						String ids[] = infotrade.getItemids().split(",");
						for (String id : ids) {
							pmap.clear();
							pmap.put("itemid", id);
							Infoitem infoitem = infoitemMapper.getInfoitem(pmap);
							
							pmap.clear();
							pmap.put("itemid", id);
							pmap.put("sstatus", 2);
							pmap.put("updatedate", System.currentTimeMillis());
							infoitemMapper.updateInfoitem_map(pmap);
							
							if (exchange_stype == -1 || exchange_intype == -1) {
								exchange_stype = infoitem.getStype();
								exchange_intype = infoitem.getIntype();
							}
							
							totalclean(infotrade.getTradeid(), 11, infoitem.getItemid());
							orderNo = totalnew(infotrade.getTradeid(), 11, infoitem.getStype(), infoitem.getItemid());
							
							batchtransfer = exchange_system_user(Long.toString(orderNo), infoitem.getUserid(),
									infoitem.getStype(), infoitem.getMoney(), 0); //需求方;
							if (batchtransfer != null) { //更新需求方
								pmap.clear();
								pmap.put("itemid", infoitem.getItemid());
								List<Recipients_obj> recipients = batchtransfer.getRecipients();
								if (recipients != null && recipients.size() > 0) {
									pmap.put("transfer", recipients.get(0).getTransfer());
								}
								pmap.put("failure_msg",
										batchtransfer.getFailureMsg().replace(WebConstants.FAILURE_MSG_ALIPAY, "")
												.trim());
								pmap.put("udpatedate", System.currentTimeMillis());
								
								infoitemMapper.updateInfoitem_map(pmap);
							}
						}
						
						totalclean(infotrade.getTradeid(), 12, null);
						orderNo = totalnew(infotrade.getTradeid(), 12, exchange_intype, null);
						batchtransfer = exchange_system_user(Long.toString(orderNo), infotrade.getBuyuserid(),
								exchange_intype, infotrade.getCost(), 1); //应答方；
						if (batchtransfer != null) { //更新应答方
							pmap.clear();
							pmap.put("tradeid", infotrade.getTradeid());
							List<Recipients_obj> recipients = batchtransfer.getRecipients();
							if (recipients != null && recipients.size() > 0) {
								pmap.put("transfer", recipients.get(0).getTransfer());
							}
							pmap.put("failure_msg",
									batchtransfer.getFailureMsg().replace(WebConstants.FAILURE_MSG_ALIPAY, "").trim());
							pmap.put("udpatedate", System.currentTimeMillis());
							
							infotradeMapper.updateInfotrade_map(pmap);
						}
					}
					
					break;
				case 1: //支付失败
					
					pmap.clear();
					pmap.put("totalid", infototal.getTotalid());
					pmap.put("sflag", 1);
					pmap.put("updatedate", System.currentTimeMillis());
					infototalMapper.updateInfototal_map(pmap);
					
					//
					pmap.clear();
					pmap.put("totalid", infototal.getTotalid());
					infototalorder = infototalorderMapper.getInfototalorder(pmap);
					if (infototalorder.getStype() == 0) { //类型：0-itemid；1-tradeid；
					} else {
						pmap.clear();
						pmap.put("tradeid", infototalorder.getTradeid());
						Infotrade infotrade = infotradeMapper.getInfotrade(pmap);
						
						pmap.clear();
						pmap.put("tradeid", infotrade.getTradeid());
						pmap.put("sstatus", 200);
						pmap.put("updatedate", System.currentTimeMillis());
						infotradeMapper.updateInfotrade_map(pmap);
						
						//
						String ids[] = infotrade.getItemids().split(",");
						for (String id : ids) {
							pmap.clear();
							pmap.put("itemid", id);
							pmap.put("sstatus", 99); //需求解锁
							pmap.put("updatedate", System.currentTimeMillis());
							infoitemMapper.updateInfoitem_map(pmap);
						}
					}
					
					break;
				default:
					break;
			}
		} else { // paypal支付
			PayPalVerifyPayment paym = new PayPalVerifyPayment();
			if (paym.verifyPayment(param.getPaypalid())) {
				param.setStype(0);
			} else {
				param.setStype(1);
			}
			
			//测试期间，统一设置成功paypal支付成功
			//			param.setStype(0);
			
			//支付结果
			Integer stype = param.getStype();
			Infototalorder infototalorder = null;
			switch (stype) {
				case 0: //支付成功
					
					pmap.clear();
					pmap.put("totalid", infototal.getTotalid());
					pmap.put("paydate", System.currentTimeMillis());
					pmap.put("sflag", 1);
					pmap.put("tradeno", param.getPaypalid());
					pmap.put("updatedate", System.currentTimeMillis());
					infototalMapper.updateInfototal_map(pmap);
					
					//
					pmap.clear();
					pmap.put("totalid", infototal.getTotalid());
					infototalorder = infototalorderMapper.getInfototalorder(pmap);
					if (infototalorder.getStype() == 0) { //类型：0-itemid；1-tradeid；
					
						pmap.clear();
						pmap.put("itemid", infototalorder.getTradeid());
						pmap.put("sstatus", 99);
						pmap.put("paydate", System.currentTimeMillis());
						pmap.put("updatedate", System.currentTimeMillis());
						infoitemMapper.updateInfoitem_map(pmap);
					} else {
						pmap.clear();
						pmap.put("tradeid", infototalorder.getTradeid());
						Infotrade infotrade = infotradeMapper.getInfotrade(pmap);
						
						pmap.clear();
						pmap.put("tradeid", infotrade.getTradeid());
						pmap.put("sstatus", 2);
						pmap.put("paydate", System.currentTimeMillis());
						pmap.put("updatedate", System.currentTimeMillis());
						infotradeMapper.updateInfotrade_map(pmap);
						
						//
						String ids[] = infotrade.getItemids().split(",");
						for (String id : ids) {
							pmap.clear();
							pmap.put("itemid", id);
							pmap.put("sstatus", 2);
							pmap.put("updatedate", System.currentTimeMillis());
							infoitemMapper.updateInfoitem_map(pmap);
						}
					}
					
					break;
				case 1: //支付失败
					
					pmap.clear();
					pmap.put("totalid", infototal.getTotalid());
					pmap.put("sflag", 1);
					pmap.put("tradeno", param.getPaypalid());
					pmap.put("updatedate", System.currentTimeMillis());
					infototalMapper.updateInfototal_map(pmap);
					
					//
					pmap.clear();
					pmap.put("totalid", infototal.getTotalid());
					infototalorder = infototalorderMapper.getInfototalorder(pmap);
					if (infototalorder.getStype() == 0) { //类型：0-itemid；1-tradeid；
					} else {
						pmap.clear();
						pmap.put("tradeid", infototalorder.getTradeid());
						Infotrade infotrade = infotradeMapper.getInfotrade(pmap);
						
						pmap.clear();
						pmap.put("tradeid", infotrade.getTradeid());
						pmap.put("sstatus", 200);
						pmap.put("updatedate", System.currentTimeMillis());
						infotradeMapper.updateInfotrade_map(pmap);
						
						//
						String ids[] = infotrade.getItemids().split(",");
						for (String id : ids) {
							pmap.clear();
							pmap.put("itemid", id);
							pmap.put("sstatus", 99); //需求解锁
							pmap.put("updatedate", System.currentTimeMillis());
							infoitemMapper.updateInfoitem_map(pmap);
						}
					}
					
					break;
				default:
					break;
			}
		}
		
		return ret;
	}
	
	/**
	 * 兑换交易，应答方付款后。平台分别向需求方/应答方转帐；
	 * 
	 * @param userid
	 *            收款方
	 * @param stype
	 *            转帐类型：0-CNY, 1-USD
	 * @param money
	 *            转帐金额（单位：元/美元，注意：实际转帐时CNY"元"要兑换成"分"）
	 * @param request_reponse
	 *            0-需求方；1-应答方；
	 */
	private Batch_transfer exchange_system_user(String orderNo, Long userid, Integer stype, BigDecimal money,
			Integer request_reponse) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		pmap.put("userid", userid);
		pmap.put("stype", stype);
		Infopayconfig infopayconfig = infopayconfigMapper.getInfopayconfig(pmap);
		if (infopayconfig == null) {
			return null;
		}
		
		Map<String, Object> transferMap = new HashMap<String, Object>();
		List<Recipients_obj> recipients = null;
		Recipients_obj recipients_obj = null;
		Batch_transfer batchtransfers = null;
		BigDecimal cost = BigDecimal.valueOf(0);
		
		switch (stype) {
			case 0: //转CNY
				cost = money.multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_DOWN);
				
				recipients = new ArrayList<Recipients_obj>();
				recipients_obj = new Recipients_obj();
				recipients_obj.setAccount(infopayconfig.getCarnum());
				recipients_obj.setAmount(cost.intValue());
				recipients_obj.setName(infopayconfig.getSname());
				recipients.add(recipients_obj);
				
				// 设置 API Key
				Pingpp.apiKey = PayConstants.apiKey;
				// 设置私钥路径，用于请求签名
				Pingpp.privateKeyPath = PayConstants.privateKeyFilePath;
				
				// 转帐；
				transferMap.clear();
				transferMap.put("channel", "alipay");// 目前支持 wx(新渠道)、 wx_pub
				transferMap.put("batch_no", orderNo);// 企业转账使用的商户内部订单号。wx(新渠道)、wx_pub 规定为 1 ~ 50 位不能重复的数字字母组合
				transferMap.put("amount", cost.intValue());// 订单总金额, 人民币单位：分（如订单总金额为 1 元，此处请填 100,企业付款最小发送金额为 1 元）
				transferMap.put("type", "b2c");// 付款类型，当前仅支持 b2c 企业付款
				transferMap.put("recipients", JSON.toJSON(recipients));// 接收者 id， 为用户在 wx(新渠道)、wx_pub 下的 open_id
				transferMap.put("description", "亿家商城转帐");
				
				batchtransfers = TransferExample.batch_transfer_alipay(transferMap, PayConstants.appId);
				
				break;
			case 1: // 转USD
				break;
			default:
				break;
		}
		return batchtransfers;
	}
	
	@Override
	public ApiResponse passwordreset(RequestParam param) throws RcException {
		ApiResponse objResponse = null;
		
		Integer ret = passwordreset_fun(param);
		if (ret == 1) {
			MyRcUtil.MyRcException(RcCode.SCODE_INVALID);
		} else if (ret == 2) {
			MyRcUtil.MyRcException(RcCode.USERNAME_NOTEXIST);
		} else if (ret == 3) {
			MyRcUtil.MyRcException(RcCode.SUBMIT_INVALID);
		}
		return objResponse;
	}
	
	private Integer passwordreset_fun(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		Integer ret = 0;
		
		pmap.clear();
		pmap.put("username", param.getUsername());
		pmap.put("scode", param.getScode());
		pmap.put("sflag", 0);
		if (logscodeMapper.checkSname(pmap) == 0) {
			return 1;
		}
		
		pmap.clear();
		pmap.put("username", param.getUsername());
		if (infouserMapper.checkSname(pmap) == 0) {
			return 2;
		}
		
		pmap.clear();
		pmap.put("username", param.getUsername());
		Infouser infouser = infouserMapper.getInfouser(pmap);
		if (infouser.getUpdatedate() != null && infouser.getUpdatedate().longValue() > 0L) {
			Long spe = System.currentTimeMillis() - Long.valueOf(infouser.getUpdatedate());
			if (spe < 60 * 1000) {
				return 3;
			}
		}
		
		pmap.clear();
		pmap.put("userid", infouser.getUserid());
		pmap.put("password", param.getPassword());
		pmap.put("updatedate", System.currentTimeMillis());
		infouserMapper.updateInfouser_map(pmap);
		
		return ret;
	}
	
	@Override
	public ApiResponse register(RequestParam param) throws RcException {
		ApiResponse objResponse = null;
		
		Integer ret = register_fun(param);
		if (ret == 1) {
			MyRcUtil.MyRcException(RcCode.SCODE_INVALID);
		} else if (ret == 2) {
			MyRcUtil.MyRcException(RcCode.USERNAME_INVALID);
		} else if (ret == 3) {
			MyRcUtil.MyRcException(RcCode.USERNAME_INVALID);
		}
		
		return objResponse;
	}
	
	private Integer register_fun(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		Integer ret = 0;
		
		// ret=1 验证码错误
		// TODO scode时间有效性判断；
		pmap.clear();
		pmap.put("username", param.getUsername());
		pmap.put("scode", param.getScode());
		pmap.put("sflag", 0);
		if (logscodeMapper.checkSname(pmap) == 0) {
			return 1;
		}
		
		// 手机号已注册
		pmap.clear();
		pmap.put("username", param.getUsername());
		if (infouserMapper.checkSname(pmap) != 0) {
			return 2;
		}
		
		Infouser infouser = new Infouser();
		infouser.setUsername(param.getUsername());
		infouser.setPassword(param.getPassword());
		infouser.setNickname(CommonUtil.hidePhonenum(param.getUsername()));
		infouser.setIcon(WebConstants.DEFAULT_ICON);
		infouser.setIconid(WebConstants.DEFAULT_ICONID);
		infouser.setRegisterdate(System.currentTimeMillis());
		infouser.setPayconfig(null);
		infouser.setCreatedate(infouser.getRegisterdate());
		infouser.setUpdatedate(infouser.getRegisterdate());
		infouserMapper.saveInfouser(infouser);
		
		return ret;
	}
	
	@Override
	public ApiResponse scodeget(RequestParam param) throws RcException {
		ApiResponse objResponse = null;
		
		Integer ret = scodeget_fun(param.getUsername());
		if (ret == 1 || ret == 2) {
			MyRcUtil.MyRcException(RcCode.SMS_ERROR);
		} else if (ret == 3) {
			MyRcUtil.MyRcException(RcCode.SMS_MORE);
		}
		
		return objResponse;
	}
	
	private Integer scodeget_fun(String username) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		Integer ret = 0;
		
		String scode = null;
		Long scode_seq = 0L;
		
		pmap.clear();
		pmap.put("username", username);
		pmap.put("sflag", 0);
		Logscode logscode = logscodeMapper.getLogscode(pmap);
		boolean flag = false;
		if (logscode == null || logscode.getSeq() == null) {
			scode = SMSUtil.scodeGet();
			scode_seq = scodeSave(username, scode);
			flag = true;
		} else {
			Long spe = System.currentTimeMillis() - logscode.getCreatedate();
			if (spe.longValue() > 60 * 1000) { // 短信60s有效
				pmap.clear();
				pmap.put("seq", logscode.getSeq());
				pmap.put("sflag", 1);
				pmap.put("updatedate", System.currentTimeMillis());
				logscodeMapper.updateLogscode_map(pmap);
				
				//
				scode = SMSUtil.scodeGet();
				scode_seq = scodeSave(username, scode);
				flag = true;
			} else {
				scode = logscode.getScode();
				scode_seq = logscode.getSeq();
				flag = false;
			}
		}
		
		if (flag) {
			HttpSenderTest Esms = new HttpSenderTest();
			String str = Esms.sms_send(username, scode);
			if (!str.equals("0")) {
				return 2;
			}
		} else {
			return 3;
		}
		
		return ret;
	}
	
	private Long scodeSave(String username, String scode) {
		Logscode logscode = new Logscode();
		logscode.setUsername(username);
		logscode.setScode(scode);
		logscode.setSflag(0);
		logscode.setCreatedate(System.currentTimeMillis());
		logscode.setUpdatedate(logscode.getCreatedate());
		logscodeMapper.saveLogscode(logscode);
		
		return logscode.getSeq();
	}
	
	@Override
	public ApiResponse sysrecom(RequestParam param) throws RcException {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		ApiResponse objResponse = new ApiResponse();
		
		TmpRet tmpRet = sysrecom_fun(param);
		if (tmpRet == null) {
			//			MyRcUtil.MyRcException(RcCode.ITEM_INVALID);
		} else {
			objResponse.setItemlist(tmpRet.getItemlist());
			objResponse.setSflag(tmpRet.getSflag());
		}
		return objResponse;
	}
	
	private TmpRet sysrecom_fun(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		Integer stype = param.getStype();
		Integer intype = 0;
		if (stype == 0) {
			intype = 1;
		} else {
			intype = 0;
		}
		
		//
		pmap.clear();
		pmap.put("sdate", DateUtil.long2DateString(System.currentTimeMillis(), "yyyyMMdd"));
		pmap.put("stype", stype);
		pmap.put("intype", intype);
		Confratio confratio = confratioMapper.getConfratio(pmap);
		if (confratio == null) {
			return null;
		}
		BigDecimal cost = param.getMoney().multiply(BigDecimal.valueOf(confratio.getRatio() / 100.0))
				.setScale(2, BigDecimal.ROUND_DOWN);
		
		TmpRet tmpret = null;
		List<Infoitem> itemlist = null;
		
		//***************************************************
		// sflag = 1，第一种推荐
		//***************************************************
		pmap.clear();
		pmap.put("sflag", 0); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		pmap.put("sstatus", 99); //0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；98-登记；99-登记并转帐；
		pmap.put("stype", intype);
		pmap.put("cost", param.getMoney());
		pmap.put("star", 0);
		pmap.put("size", 100);
		pmap.put("limit", 1);
		itemlist = infoitemMapper.getInfoitemList_simple(pmap);
		List<Infoitem> itemlist_only = new ArrayList<Infoitem>();
		if (itemlist != null && !itemlist.isEmpty()) { //满足#1推荐情况
			itemlist_only.add(itemlist.get(0));
			
			tmpret = new TmpRet();
			tmpret.setSflag(1);
			tmpret.setItemlist(itemlist_only);
			
			return tmpret;
		}
		
		//***************************************************
		// sflag = 2，第二种推荐
		//***************************************************
		pmap.clear();
		pmap.put("sflag", 0); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		pmap.put("sstatus", 99); //0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；98-登记；99-登记并转帐；
		pmap.put("stype", intype);
		pmap.put("halfup", 1);
		pmap.put("cost", param.getMoney());
		pmap.put("halfcost", param.getMoney().divide(BigDecimal.valueOf(2)).setScale(2, BigDecimal.ROUND_DOWN));
		pmap.put("star", 0);
		pmap.put("size", 100);
		pmap.put("limit", 1);
		List<Infoitem> itemlist_up = infoitemMapper.getInfoitemList_simple(pmap);
		
		pmap.clear();
		pmap.put("sflag", 0); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		pmap.put("sstatus", 99); //0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；98-登记；99-登记并转帐；
		pmap.put("stype", intype);
		pmap.put("halfdown", 1);
		pmap.put("cost", param.getMoney());
		pmap.put("halfcost", param.getMoney().divide(BigDecimal.valueOf(2)).setScale(2, BigDecimal.ROUND_DOWN));
		pmap.put("star", 0);
		pmap.put("size", 100);
		pmap.put("limit", 1);
		List<Infoitem> itemlist_down = infoitemMapper.getInfoitemList_simple(pmap);
		
		BigDecimal cost_cout = param.getMoney();
		int upidx = -1, downidx = -1;
		if (itemlist_up != null && itemlist_up.size() > 0 && itemlist_down != null && itemlist_down.size() > 0) {
			for (int i = 0; i < itemlist_up.size(); i++) {
				if (i >= itemlist_down.size()) {
					break;
				}
				for (int j = i; j < itemlist_down.size(); j++) {
					if (itemlist_up.get(i).getCost().add(itemlist_down.get(j).getCost()).compareTo(cost_cout) == 0) {
						upidx = i;
						downidx = j;
						break;
					}
				}
			}
		}
		if (upidx != -1 && downidx != -1) {
			itemlist = new ArrayList<Infoitem>();
			itemlist.add(itemlist_up.get(upidx));
			itemlist.add(itemlist_down.get(downidx));
			
			tmpret = new TmpRet();
			tmpret.setSflag(2);
			tmpret.setItemlist(itemlist);
			
			return tmpret;
		}
		
		//***************************************************
		// sflag = 3，第三种推荐
		//***************************************************
		pmap.clear();
		pmap.put("sflag", 0); //状态：0-正常；1-超时系统取消；2-用户主动取消；11-已删除；
		pmap.put("sstatus", 99); //0-等待转账；1-需求方完成转账；2-应答方完成转账；3-完成；98-登记；99-登记并转帐；
		pmap.put("stype", intype);
		pmap.put("flagbigcost", 1);
		pmap.put("cost", param.getMoney());
		pmap.put("halfcost", param.getMoney().divide(BigDecimal.valueOf(2)).setScale(2, BigDecimal.ROUND_DOWN));
		pmap.put("star", 0);
		pmap.put("size", 100);
		pmap.put("limit", 1);
		itemlist = infoitemMapper.getInfoitemList_simple(pmap);
		if (itemlist != null && !itemlist.isEmpty()) { //满足#3推荐情况
			tmpret = new TmpRet();
			tmpret.setSflag(3);
			tmpret.setItemlist(itemlist);
			
			return tmpret;
		}
		
		if (tmpret == null) { //不满足任何推荐情况
			tmpret = new TmpRet();
			tmpret.setSflag(0);
			
			return tmpret;
		}
		
		return tmpret;
	}
	
	private TmpRet togetcharge(RequestParam param, Long cost, Long tradeid, Integer stype, Integer paystype) {
		TmpRet tmpret = new TmpRet();
		
		//
		totalclean(tradeid, stype, null);
		
		//
		Long totalid = totalnew(tradeid, stype, paystype, null);
		
		Charge charge = ChargeEx.getcharge(cost, WebConstants.ORDER_TITLE, Long.toString(totalid),
				WebConstants.ORDER_PAY_PG1_ALIPAY);
		tmpret.setChargedata(charge);
		tmpret.setPayorder(totalid);
		
		return tmpret;
	}
	
	private TmpRet togetpaypal(RequestParam param, Long cost, Long tradeid, Integer stype, Integer paystype) {
		TmpRet tmpret = new TmpRet();
		
		//
		totalclean(tradeid, stype, null);
		
		//
		Long totalid = totalnew(tradeid, stype, paystype, null);
		
		tmpret.setPayorder(totalid);
		
		return tmpret;
	}
	
	private String tokenget(Long userid) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		String token = null;
		
		if (userid == null || userid == 0)
			return null;
		
		// #1 存在有效token，先失效。再：新增
		pmap.clear();
		pmap.put("userid", userid);
		pmap.put("sflag", 0);
		if (logtokenMapper.checkSname(pmap) > 1) {
			pmap.clear();
			pmap.put("userid", userid);
			pmap.put("sflag", 1);
			pmap.put("updatedate", System.currentTimeMillis());
			if (pmap != null && !pmap.isEmpty() && pmap.get("userid") != null) {
				logtokenMapper.updateLogtoken_map(pmap);
			}
		}
		
		// #2 新增token
		token = UuidUtils.createToken(userid);
		tokensave(token, userid);
		
		return token;
	}
	
	private void tokenlose(Long userid, String token) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		if (userid == null || userid == 0 || token == null || token.length() == 0)
			return;
		
		pmap.clear();
		pmap.put("userid", userid);
		pmap.put("token", token);
		pmap.put("sflag", 1);
		pmap.put("updatedate", System.currentTimeMillis());
		if (pmap != null && !pmap.isEmpty() && pmap.get("userid") != null) {
			logtokenMapper.updateLogtoken_map(pmap);
		}
	}
	
	private void tokensave(String token, Long userid) {
		Logtoken logtoken = new Logtoken();
		logtoken.setToken(token);
		logtoken.setUserid(userid);
		logtoken.setSflag(0); // 0-正常； 1-失效；
		logtoken.setCreatedate(System.currentTimeMillis());
		logtoken.setUpdatedate(logtoken.getCreatedate());
		logtokenMapper.saveLogtoken(logtoken);
	}
	
	private TmpRet topage(Integer pagenum, Integer psize, Integer totalcount) {
		TmpRet tmpret = null;
		
		if (totalcount == null) {
			return null;
		}
		
		if (totalcount > 0) {
			tmpret = new TmpRet();
			
			Integer pagesize = topagesize(psize);
			
			Integer totalpage = 0;
			if (totalcount % pagesize == 0)
				totalpage = totalcount / pagesize;
			else
				totalpage = (totalcount / pagesize) + 1;
			if (pagenum == null || pagenum.intValue() <= 0) {
				pagenum = 1;
			}
			if (pagenum.intValue() > totalpage.intValue()) {
				return null;
			}
			
			tmpret.setTotalpage(totalpage);
			tmpret.setStar((pagenum - 1) * pagesize);
			tmpret.setSize(pagesize);
			
			if (tmpret.getStar() == null) {
				tmpret.setStar(0);
			}
			if (tmpret.getSize() == null) {
				tmpret.setSize(WebConstants.PAGE_SIZE_DEFAULT_10);
			}
		}
		
		return tmpret;
	}
	
	private Integer topagesize(Integer psize) {
		
		Integer ret = WebConstants.PAGE_SIZE_DEFAULT_10;
		
		if (psize == null || psize == 0) {
			return ret;
		}
		
		if (psize.intValue() != WebConstants.PAGE_SIZE_DEFAULT_10.intValue()
				&& psize.intValue() != WebConstants.PAGE_SIZE_20.intValue()
				&& psize.intValue() != WebConstants.PAGE_SIZE_50.intValue()
				&& psize.intValue() != WebConstants.PAGE_SIZE_100.intValue()) {
			return ret;
		} else {
			return psize;
		}
	}
	
	private Integer topagesize_max(Integer psize) {
		Integer ret = WebConstants.PAGE_SIZE_50;
		
		if (psize == null || psize == 0) {
			return ret;
		}
		
		if (psize.intValue() != WebConstants.PAGE_SIZE_DEFAULT_10.intValue()
				&& psize.intValue() != WebConstants.PAGE_SIZE_20.intValue()
				&& psize.intValue() != WebConstants.PAGE_SIZE_50.intValue()) {
			return ret;
		} else {
			return psize;
		}
	}
	
	private void totalclean(Long tradeid, Integer stype, Long itemid) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		pmap.put("tradeid", tradeid);
		pmap.put("stype", stype);
		if (stype == 11) {
			pmap.put("itemdi", itemid);
		}
		Infototalorder infototalorder = infototalorderMapper.getInfototalorder(pmap);
		if (infototalorder == null) {
			return;
		}
		
		pmap.clear();
		pmap.put("seq", infototalorder.getSeq());
		infototalorderMapper.delInfototalorder(pmap);
		
		pmap.clear();
		pmap.put("totalid", infototalorder.getTotalid());
		infototalMapper.delInfototal(pmap);
	}
	
	private Long totalnew(Long tradeid, Integer stype, Integer paystype, Long itemid) {
		Infototal infototal = new Infototal();
		infototal.setStype(paystype);
		infototal.setAskdate(System.currentTimeMillis());
		infototal.setSflag(0);
		infototal.setCreatedate(System.currentTimeMillis());
		infototal.setUpdatedate(infototal.getCreatedate());
		infototalMapper.saveInfototal(infototal);
		
		Infototalorder obj2 = new Infototalorder();
		obj2.setTotalid(infototal.getTotalid());
		obj2.setTradeid(tradeid);
		obj2.setStype(stype);
		if (stype == 11) {
			obj2.setItemid(itemid);
		}
		obj2.setCreatedate(System.currentTimeMillis());
		obj2.setUpdatedate(obj2.getCreatedate());
		infototalorderMapper.saveInfototalorder(obj2);
		
		return infototal.getTotalid();
	}
	
	@Override
	public ApiResponse userperfect(RequestParam param) throws RcException {
		ApiResponse objResponse = null;
		
		userperfect_fun(param);
		
		return objResponse;
	}
	
	private void userperfect_fun(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		pmap.put("userid", param.getUserid());
		pmap.put("nickname", param.getNickname());
		pmap.put("updatedate", System.currentTimeMillis());
		infouserMapper.updateInfouser_map(pmap);
		
		// 先清，后保存
		pmap.clear();
		pmap.put("userid", param.getUserid());
		if (pmap != null && !pmap.isEmpty() && pmap.get("userid") != null) {
			infopayconfigMapper.delInfopayconfig(pmap);
		}
		
		Infopayconfig infopayconfig = null;
		// save paypal info
		if (!StrUtils.isBlank(param.getPaypalnum()) && !StrUtils.isBlank(param.getPaypalname())) {
			infopayconfig = new Infopayconfig();
			infopayconfig.setUserid(param.getUserid());
			infopayconfig.setStype(1); //绑定类型：0-CNY；1-USD
			infopayconfig.setCarnum(param.getPaypalnum());
			infopayconfig.setSname(param.getPaypalname());
			infopayconfig.setCreatedate(System.currentTimeMillis());
			infopayconfig.setUpdatedate(infopayconfig.getCreatedate());
			infopayconfigMapper.saveInfopayconfig(infopayconfig);
		}
		
		// save alipay info
		if (!StrUtils.isBlank(param.getAlipaynum()) && !StrUtils.isBlank(param.getAlipayname())) {
			infopayconfig = new Infopayconfig();
			infopayconfig.setUserid(param.getUserid());
			infopayconfig.setStype(0); //绑定类型：0-CNY；1-USD
			infopayconfig.setCarnum(param.getAlipaynum());
			infopayconfig.setSname(param.getAlipayname());
			infopayconfig.setCreatedate(System.currentTimeMillis());
			infopayconfig.setUpdatedate(infopayconfig.getCreatedate());
			infopayconfigMapper.saveInfopayconfig(infopayconfig);
		}
	}
	
	@Override
	public ApiResponse userview(RequestParam param) throws RcException {
		
		ApiResponse objResponse = new ApiResponse();
		TmpRet tmpret = userview_fun(param);
		if (tmpret != null) {
			tmpret.getUserdata().setPassword(null);
			objResponse.setUserdata(tmpret.getUserdata());
			objResponse.setMoneylist(tmpret.getMoneylist());
		}
		
		return objResponse;
	}
	
	private TmpRet userview_fun(RequestParam param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		TmpRet tmpret = null;
		
		Long userid = null;
		if (param.getViewuserid() != null) {
			userid = param.getViewuserid();
		} else {
			userid = param.getUserid();
		}
		
		// 非登录情况下
		if (userid == null || userid == 0) {
			return null;
		}
		
		pmap.clear();
		pmap.put("userid", userid);
		if (infouserMapper.checkSname(pmap) == 0) {
			MyRcUtil.MyRcException(RcCode.INFOUSER_INVALID);
		}
		
		pmap.clear();
		pmap.put("userid", userid);
		Infouser infouser = infouserMapper.getInfouser(pmap);
		
		pmap.clear();
		pmap.put("userid", infouser.getUserid());
		List<Infopayconfig> payconfiglist = infopayconfigMapper.getInfopayconfigList(pmap);
		if (payconfiglist != null && payconfiglist.size() > 0) {
			infouser.setPayconfiglist(payconfiglist);
		}
		
		tmpret = new TmpRet();
		tmpret.setUserdata(infouser);
		
		// 追加金额限制
		List<Moneylimit> moneylimit = new ArrayList<Moneylimit>();
		Moneylimit moneylimit_obj = null;
		
		//
		try {
			Confconfig confconfig = null;
			
			//
			moneylimit_obj = new Moneylimit();
			moneylimit_obj.setStype(0);
			
			pmap.clear();
			pmap.put("cfield", WebConstants.MONEY_LIMIT_STYPE0_MIN);
			pmap.put("ctbname", "money_limit");
			confconfig = confconfigMapper.getConfconfig(pmap);
			moneylimit_obj.setMin(BigDecimal.valueOf(Double.valueOf(confconfig.getCkey())));
			
			pmap.clear();
			pmap.put("cfield", WebConstants.MONEY_LIMIT_STYPE0_MAX);
			pmap.put("ctbname", "money_limit");
			confconfig = confconfigMapper.getConfconfig(pmap);
			moneylimit_obj.setMax(BigDecimal.valueOf(Double.valueOf(confconfig.getCkey())));
			
			moneylimit.add(moneylimit_obj);
			
			//
			moneylimit_obj = new Moneylimit();
			moneylimit_obj.setStype(1);
			
			pmap.clear();
			pmap.put("cfield", WebConstants.MONEY_LIMIT_STYPE1_MIN);
			pmap.put("ctbname", "money_limit");
			confconfig = confconfigMapper.getConfconfig(pmap);
			moneylimit_obj.setMin(BigDecimal.valueOf(Double.valueOf(confconfig.getCkey())));
			
			pmap.clear();
			pmap.put("cfield", WebConstants.MONEY_LIMIT_STYPE1_MAX);
			pmap.put("ctbname", "money_limit");
			confconfig = confconfigMapper.getConfconfig(pmap);
			moneylimit_obj.setMax(BigDecimal.valueOf(Double.valueOf(confconfig.getCkey())));
			
			moneylimit.add(moneylimit_obj);
			
			//
			tmpret.setMoneylist(moneylimit);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return tmpret;
	}
}
