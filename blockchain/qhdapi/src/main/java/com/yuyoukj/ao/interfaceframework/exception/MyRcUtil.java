/**
 *
 */
package com.yuyoukj.ao.interfaceframework.exception;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import com.yuyoukj.ao.interfaceframework.controller.Response;

public abstract class MyRcUtil {

	protected static List<ResourceBundle> resourceBundleList = new LinkedList<ResourceBundle>();

	static {
		addResourceBundle("com.yuyoukj.ao.interfaceframework.exception.RcResource");
	}

	public static void addResourceBundle(String bundleName) {
		ResourceBundle rb = ResourceBundle.getBundle(bundleName);
		resourceBundleList.add(0, rb);
	}

	public static String getValue(String key) {
		try {
			for (ResourceBundle rb : resourceBundleList) {
				String value = rb.getString(key);
				if (value != null) {
					return value;
				}
			}
		} catch (Exception e) {
			// nothing to do
		}
		return "";
	}

	public static void MyRcException(int rc) {
		Response res = new Response();
		res.setRc(RcCode.PARAM_INVALID);
		res.setMsg(MyRcUtil.getValue("rc_" + rc));
		throw new RcException(rc, res);// 异常数据
	}

	public static void MyRcExceptionSpec(int rc, String msg) {
		Response res = new Response();
		res.setRc(RcCode.PARAM_INVALID);
		res.setMsg(msg);
		throw new RcException(rc, res);// 异常数据
	}

}
