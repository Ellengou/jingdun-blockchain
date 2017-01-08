package com.yuyoukj.ao.interfaceframework.exception;
public class RcCode {
	public static final int RC = 0; // 成功
	public static final int TOKEN_INVALID = 1000; // token无效
	public static final int PARAM_INVALID = 1001; // 参数无效
	public static final int APPKEY_INVALID = 1002; // appkey无效
	public static final int APPSIG_INVALID = 1003; // 校验无效
	public static final int METHOD_INVALID = 1004; // 方法无效
	// --------------------------------------------------------
	public static final int SCODE_INVALID = 2000; // 验证码错误
	public static final int USERNAME_INVALID = 2001; // 手机号已注册
	public static final int INFOUSER_INVALID = 2002; // 用户名或密码错误
	public static final int EXIT_INVALID = 2003; // 退出失败
	public static final int USENAMENULL_INVALID = 2004; // 用户名不存在
	public static final int SUBMIT_INVALID = 2005; // 请勿重复提交申请
	public static final int USERID_INVALID = 2006; // 用户信息不存在

	public static final int ITEM_INVALID = 2007; // 数据错误
	public static final int ORDER_INVALID = 2008; // 订单数据错误
	
	public static final int USERNAME_NOTEXIST = 2009; // 用户名不存在
	public static final int SMS_ERROR = 2010; // 短信验证码发送失败
	public static final int SMS_MORE = 2011; // 短信验证码发送请求太频繁
	public static final int ITEM_ERR = 2012; // 需求数据错误

}
