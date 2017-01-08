package com.yuyoukj.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import com.yuyoukj.ao.common.util.UuidUtils;
import com.yuyoukj.ao.interfaceframework.controller.Action;
import com.yuyoukj.ao.interfaceframework.controller.Interceptor;
import com.yuyoukj.ao.interfaceframework.controller.Response;
import com.yuyoukj.ao.interfaceframework.exception.RcException;
import com.yuyoukj.service.impl.CommonServiceImpl;
import com.yuyoukj.util.Str8Code;

public class ApiParameterInterceptor implements Interceptor {
	private Logger log = Logger.getLogger(ApiParameterInterceptor.class);

	// 业务方法执行完成后，对调用接口做日志记录。
	@Override
	public void after(Action a, Object obj) {
	}

	/*
	 * 对于所有的接口，都要执行本步参数验证，在调用接口前都要对传入的url参数及访问源（IP地址）实行验证，对于不需要验证的service方法，可以conditions里标注明确。
	 *
	 * @Conditions({
	 *
	 * @Condition(service = "account", method = "registerInfoUser")})
	 */
	@Override
	public void before(Action a) {
		long m = System.currentTimeMillis();
		long n = 0;
		CommonServiceImpl commonService = a.fetchService("commonservice");
		Map<String, String> encodeParam = commonService.getEncodeParam();
		Map<String, String> pmap = new HashMap<String, String>();
		for (Entry<String, Object[]> e : a.getParameters().entrySet()) {
			if (e.getValue()[0] instanceof String) {
				if (encodeParam.containsKey(e.getKey())) {
					pmap.put(e.getKey(), Str8Code.en8Code((String) e.getValue()[0]));
				} else {
					pmap.put(e.getKey(), (String) e.getValue()[0]);
				}
			}
		}
		int rc = commonService.checkParams(pmap);
		if (rc != 0) {
			Response res = new Response();
			res.setRc(rc);
			throw new RcException(rc, res);// 异常数据
		}
		// 验证完后，处理参数/转换参数/补充参数
		// 特殊处理pagesize
		if (pmap.get("pagesize") != null) {
			String pagesize = pmap.get("pagesize");
			if (!pagesize.equals("10") && !pagesize.equals("20") && !pagesize.equals("50")) {
				a.getParameters().put("pagesize", new String[] { "10" });
			}
		}
		// token合法，插入转换后的userid
		if (pmap.get("token") != null) {
			String token = pmap.get("token");
			if (token.length() > 0) {
				a.getParameters().put("userid", new String[] { UuidUtils.tokenToUserid(Str8Code.de8Code(pmap.get("token"))).toString() });
			} else {
				pmap.put("token", null);
			}
		}

		a.getParameters().put("ip", new String[] { a.getIp() });
		n = System.currentTimeMillis() - m;
		log.debug("0---------------" + n);
	}
}
