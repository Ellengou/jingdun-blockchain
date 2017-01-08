package com.yuyoukj.util.third.pingpp;

import com.yuyoukj.util.constants.WebConstants;

public class PayConstants {
	/**
	 * pingpp 管理平台对应的 API key
	 */
	//	public final static String apiKey = "sk_test_m1ir98fvDm54r5mXPK8OCm90";
	public final static String apiKey = "sk_live_effvjP1m9qDKTiLir1rjfDSK";
	/**
	 * pingpp 管理平台对应的应用 ID
	 */
	public final static String appId = "app_PKCS0G8iTSCKmj9y";

	//	public final static String privateKeyFilePath = "/Users/kkk/wssrc/qhdconsole/res/rsa_private_key.pem";
	public final static String privateKeyFilePath = WebConstants.DEFAULT_FILE_DIRECTORY + "/qhdconsole/rsa_private_key.pem";

	//
	public final static String SUBJECT = "亿家商城支付";
	public final static String CLIENT_IP = "127.0.0.1";

}
