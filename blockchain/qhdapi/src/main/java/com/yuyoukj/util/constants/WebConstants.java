package com.yuyoukj.util.constants;

public class WebConstants {
	
	// =========================================
	// 文件上传
	public static final String DEFAULT_URL_HTTP = "http://192.168.0.106:8081";
	public static final String DEFAULT_FILE_DIRECTORY = "/Users/kkk/tool/tomcat_80/webapps";
	//	public static final String DEFAULT_FILE_DIRECTORY = "D:\\tool\\tomcat-7.0.56-windows-x64\\webapps";
	
	//	public static final String DEFAULT_URL_HTTP = "http://121.40.79.113";
	//	public static final String DEFAULT_FILE_DIRECTORY = "/usr/local/tomcat/apache-tomcat-8.0.39/webapps";
	
	//	public static final String DEFAULT_URL_HTTP = "http://www.ibrxy.com";
	//	public static final String DEFAULT_FILE_DIRECTORY = "/home/tool/tomcat_80/webapps";
	
	public static final String DEFAULT_IMG_PATH = "/img/qhdimg";
	public static final String DEFAULT_FILE_PATH = "/img/qhdfile";
	
	public static final String JOIN_URL = "/";
	
	public static final Long DEFAULT_ICONID = 1L;
	public static final String DEFAULT_ICON = "";
	
	// =========================================
	public static final String APP_KEY = "YYKJ_RTY";
	public static final String APP_SECRET = "232ffa13e95100e5af48b3efb75a7aca";
	
	// =========================================
	public static final Integer PAGE_SIZE_DEFAULT_10 = 10;
	public static final Integer PAGE_SIZE_20 = 20;
	public static final Integer PAGE_SIZE_50 = 50;
	public static final Integer PAGE_SIZE_100 = 100;
	
	public static final String IP = "120.55.95.207";
	
	public static final String MONEY_LIMIT_STYPE0_MIN = "money_limit_stype0_min";
	public static final String MONEY_LIMIT_STYPE0_MAX = "money_limit_stype0_max";
	public static final String MONEY_LIMIT_STYPE1_MIN = "money_limit_stype1_min";
	public static final String MONEY_LIMIT_STYPE1_MAX = "money_limit_stype1_max";
	
	//
	public static final long DAY_UNIXTIME = 86400000;
	public static final String CFIELD_TDAY = "T_DAY";
	public static final long NOPAY_UNIXTIME = 5 * 60 * 1000;
	
	public static final String FAILURE_MSG_ALIPAY = "需要打开地址进行下一步付款操作:";
	
	//
	public static final String ORDER_TITLE = "亿家商城订单";
	public static final String ORDER_PAY_PG1_ALIPAY = "alipay";
	public static final double COST_COMMISSION = 0.006;
	
	//	public static final String PAYPAL_TOKEN_URL = "https://api.sandbox.paypal.com/v1/oauth2/token";
	//	public static final String PAYPAL_PAYMENT_DETAIL = "https://api.sandbox.paypal.com/v1/payments/payment/";
	//	public static final String PAYPAL_clientId = "AYAo1SDqerLUXdmTGTP67sx6JpKwg7gf-y5Trr9qmjekRUX8I-tjJbeAxwMOilcYkF1o4Q6hnCAcUE9o";
	//	public static final String PAYPAL_secret = "EKpGB46u3JTxBMRZ_AASrLcuoyjIQsumX0ipwfaGrxIKT06drKYGplEUwYNp6Mgw6YftwKh_ZsyFXck-";
	
	public static final String PAYPAL_TOKEN_URL = "https://api.paypal.com/v1/oauth2/token";
	public static final String PAYPAL_PAYMENT_DETAIL = "https://api.paypal.com/v1/payments/payment/";
	public static final String PAYPAL_clientId = "AbX_1hNVSAX8FMM-pEA_BnA2BQFaarOYZu0fRhTZZBOPQQKBbEJRVUTmA4GkVgtfEIFxUuBS-jJqTUbS";
	public static final String PAYPAL_secret = "EHaWqeM6w9lFwYXTmEWQI4mCpQaKyqMoQQlMFiK8M09gQ6qvkJcSNsq2o2YcMD2R7YRBx2JmSH1OOhW1";
	
	//PayPalEnvironmentProduction : @"AbX_1hNVSAX8FMM-pEA_BnA2BQFaarOYZu0fRhTZZBOPQQKBbEJRVUTmA4GkVgtfEIFxUuBS-jJqTUbS", 
	//EHaWqeM6w9lFwYXTmEWQI4mCpQaKyqMoQQlMFiK8M09gQ6qvkJcSNsq2o2YcMD2R7YRBx2JmSH1OOhW1
	
	//PayPalEnvironmentSandbox : @"AYAo1SDqerLUXdmTGTP67sx6JpKwg7gf-y5Trr9qmjekRUX8I-tjJbeAxwMOilcYkF1o4Q6hnCAcUE9o"
	//EKpGB46u3JTxBMRZ_AASrLcuoyjIQsumX0ipwfaGrxIKT06drKYGplEUwYNp6Mgw6YftwKh_ZsyFXck-
	
	//
	public static final String RATIO_FROM = "http://srh.bankofchina.com/search/whpj/search.jsp";
	public static final String RATIO_PJNAME = "1316";
	//	1314---英镑
	//	1315---港币
	//	1316---美元
	//	1317---瑞士法郎
	//	1318---德国马克
	//	1319---法国法郎
	//	1375---新加坡元
	//	1320---瑞典克朗
	//	1321---丹麦克朗
	//	1322---挪威克朗
	//	1323---日元
	//	1324---加拿大元
	//	1325---澳大利亚元
	//	1326---欧元
	//	1327---澳门元
	//	1328---菲律宾比索
	//	1329---泰国铢
	//	1330---新西兰元
	//	1331---韩元
	//	1843---卢布
	//	2890---林吉特
	//	2895---新台币
	//	1370---西班牙比塞塔
	//	1371---意大利里拉
	//	1372---荷兰盾
	//	1373---比利时法郎
	//	1374---芬兰马克
	//	3030---印尼卢比
	//	3253---巴西里亚尔
	//	3899---阿联酋迪拉姆
	//	3900---印度卢比
	//	3901---南非兰特
	//	4418---沙特里亚尔
	//	4560---土耳其里拉
	
}
