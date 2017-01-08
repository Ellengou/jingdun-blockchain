package com.yuyoukj.service.impl;

import static com.yuyoukj.util.Validator.isBlank;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.yuyoukj.ao.common.util.UuidUtils;
import com.yuyoukj.ao.interfaceframework.exception.RcCode;
import com.yuyoukj.commons.Constants;
import com.yuyoukj.mapper.qhd.InfouserMapper;
import com.yuyoukj.mapper.qhd.LogtokenMapper;
import com.yuyoukj.service.CommonService;
import com.yuyoukj.util.Str8Code;

public class CommonServiceImpl implements CommonService {
	// 配置文件初始化
	private Map<String, String> notValidateTokenMethodMap = new HashMap<String, String>();
	private Map<String, String> validateMethodMap = new HashMap<String, String>();// 开放的有效接口
	private Map<String, String> notValidateAppSigMethodMap = new HashMap<String, String>();
	private Map<String, String> validateDeviceMethodMap = new HashMap<String, String>();
	private Map<String, String> notAppsigParamMap = new HashMap<String, String>();// 不需要参与加密的appsig参数
	private Map<String, String> properties = new HashMap<String, String>();
	private Map<String, String> encodeParam = new HashMap<String, String>();
	private Map<String, String> specParam = new HashMap<String, String>();
	@Autowired
	private InfouserMapper infoUserMapper;
	@Autowired
	private LogtokenMapper logTokenMapper;

	@Override
	public int checkParams(Map<String, String> pmap) {
		int retValue = 0;
		// 有效接口验证
		if (null == pmap.get(Constants.PARAM_METHOD) || !validateMethodMap.containsKey(pmap.get(Constants.PARAM_METHOD))) {
			return RcCode.METHOD_INVALID;
		}
		if (!"test".equals(Str8Code.de8Code(pmap.get(Constants.PARAM_APPKEY)))) {
			// 基本参数校验
			if (!notValidateAppSigMethodMap.containsKey(pmap.get(Constants.PARAM_METHOD))) {
				retValue = validateBaseParam(pmap);
				if (retValue != 0) {
					return retValue;
				}
			}
		}
		// token验证
		if (!notValidateTokenMethodMap.containsKey(pmap.get(Constants.PARAM_METHOD))) {
			retValue = validateTokenValue(pmap);
			if (retValue != 0) {
				return retValue;
			}
		}
		return retValue;
	}

	public Map<String, String> getEncodeParam() {
		return encodeParam;
	}

	public Map<String, String> getNotAppsigParamMap() {
		return notAppsigParamMap;
	}

	public Map<String, String> getNotValidateAppSigMethodMap() {
		return notValidateAppSigMethodMap;
	}

	public Map<String, String> getNotValidateTokenMethodMap() {
		return notValidateTokenMethodMap;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public Map<String, String> getSpecParam() {
		return specParam;
	}

	public Map<String, String> getValidateDeviceMethodMap() {
		return validateDeviceMethodMap;
	}

	public Map<String, String> getValidateMethodMap() {
		return validateMethodMap;
	}

	@Override
	public void init() {
		try {
			// 初始化加载缓存；
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setEncodeParam(Map<String, String> encodeParam) {
		this.encodeParam = encodeParam;
	}

	public void setNotAppsigParamMap(Map<String, String> notAppsigParamMap) {
		this.notAppsigParamMap = notAppsigParamMap;
	}

	public void setNotValidateAppSigMethodMap(Map<String, String> notValidateAppSigMethodMap) {
		this.notValidateAppSigMethodMap = notValidateAppSigMethodMap;
	}

	public void setNotValidateTokenMethodMap(Map<String, String> notValidateTokenMethodMap) {
		this.notValidateTokenMethodMap = notValidateTokenMethodMap;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public void setSpecParam(Map<String, String> specParam) {
		this.specParam = specParam;
	}

	public void setValidateDeviceMethodMap(Map<String, String> validateDeviceMethodMap) {
		this.validateDeviceMethodMap = validateDeviceMethodMap;
	}

	public void setValidateMethodMap(Map<String, String> validateMethodMap) {
		this.validateMethodMap = validateMethodMap;
	}

	private int validateBaseParam(Map<String, String> pmap) {
		// String appsecret = getAppsecret(Str8Code.de8Code(pmap.get(Constants.PARAM_APPKEY)));
		// if (isBlank(appsecret)) {
		// return RcCode.APPKEY_INVALID;
		// }
		// String appsig = BaseSDKService.createAppsig(pmap, appsecret, "UTF-8", notAppsigParamMap, null);
		// String _appsig = isBlank(pmap.get(Constants.PARAM_APPSIG)) ? "" : pmap.get(Constants.PARAM_APPSIG);// 从url取得appsig值。
		// if (!appsig.equals(_appsig)) {
		// return RcCode.APPSIG_INVALID;
		// }
		return RcCode.RC;
	}

	private int validateTokenValue(Map<String, String> pmap) {
		String inTokenValue = Str8Code.de8Code(pmap.get(Constants.PARAM_TOKEN));
		if (isBlank(inTokenValue)) {
			return RcCode.TOKEN_INVALID;
		}
		Map<String, Object> xmap = new HashMap<String, Object>();
		xmap.clear();
		xmap.put("userid", UuidUtils.tokenToUserid(inTokenValue));
		xmap.put("token", inTokenValue);
		xmap.put("sflag", 0);
		// xmap.put("tablename", Constant.TABLE_INFO_USER);
		if (UuidUtils.tokenToUserid(inTokenValue) == null || infoUserMapper.checkInfouser(xmap) == 0 || logTokenMapper.checkToken(xmap) == 0) {
			return RcCode.TOKEN_INVALID;
		}

		return RcCode.RC;
	}
}
