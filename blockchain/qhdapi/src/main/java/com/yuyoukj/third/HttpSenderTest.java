package com.yuyoukj.third;

import org.apache.log4j.Logger;
import com.bcloud.msg.http.HttpSender;

public class HttpSenderTest {
	private Logger log = Logger.getLogger(HttpSenderTest.class);

	public String sms_send(String phonenum1, String scode) {
		long a = System.currentTimeMillis();
		log.error("===sms start: " + a + "(" + phonenum1 + "," + scode + ")");

		String uri = "http://send.18sms.com/msg/HttpBatchSendSM";// 应用地址
		String account = "003548";// 账号
		String pswd = "Sd123456";// 密码
		String mobiles1 = phonenum1;// 手机号码，多个号码使用","分割
		String content1 = "您好，您的验证码是：" + scode;// 短信内容

		//String mobiles2 = phonenum2;// 手机号码，多个号码使用","分割
		//String content2 = "您好，您的推荐码是：" + tcode;// 短信内容

		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String product = "";// 产品ID(不用填写)
		String extno = "";// 扩展码(不用填写)

		String str = "";
		try {
			//			String returnString1 = HttpSender.send(uri, account, pswd, mobiles1, content1, needstatus, product, extno);
			String returnString1 = HttpSender.batchSend(uri, account, pswd, mobiles1, content1, needstatus, product, extno);
			log.error("===ret back: " + returnString1);
			Integer in = returnString1.indexOf(",");
			Integer end = returnString1.indexOf("\n");
			str = returnString1.substring(in + 1, end);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long b = System.currentTimeMillis();
		log.error("===ret: " + str);
		log.error("===sms end: " + b + "(" + (b - a) * 1.0 / 1000 + ")");

		return str;
	}

	public String sms_change(String phonenum1, String scode) {

		long a = System.currentTimeMillis();
		log.error("===sms start: " + a + "(" + phonenum1 + "," + scode + ")");

		String uri = "http://send.18sms.com/msg/HttpBatchSendSM";// 应用地址
		String account = "003548";// 账号
		String pswd = "Sd123456";// 密码
		String mobiles1 = phonenum1;// 手机号码，多个号码使用","分割
		String content1 = "您好，您的验证码是：" + scode;// 短信内容

		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String product = "";// 产品ID(不用填写)
		String extno = "";// 扩展码(不用填写)

		String str = "";
		try {
			String returnString1 = HttpSender.batchSend(uri, account, pswd, mobiles1, content1, needstatus, product, extno);
			log.error("===ret back: " + returnString1);
			Integer in = returnString1.indexOf(",");
			Integer end = returnString1.indexOf("\n");
			str = returnString1.substring(in + 1, end);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long b = System.currentTimeMillis();
		log.error("===ret: " + str);
		log.error("===sms end: " + b + "(" + (b - a) * 1.0 / 1000 + ")");

		return str;
	}
}
