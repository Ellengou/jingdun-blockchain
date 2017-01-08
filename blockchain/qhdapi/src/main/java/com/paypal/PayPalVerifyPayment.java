package com.paypal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import com.alibaba.fastjson.JSON;

public class PayPalVerifyPayment {

	private static final String TOKEN_URL = "https://api.sandbox.paypal.com/v1/oauth2/token";
	private static final String PAYMENT_DETAIL = "https://api.sandbox.paypal.com/v1/payments/payment/";
	private static final String clientId = "AYAo1SDqerLUXdmTGTP67sx6JpKwg7gf-y5Trr9qmjekRUX8I-tjJbeAxwMOilcYkF1o4Q6hnCAcUE9o";
	private static final String secret = "EKpGB46u3JTxBMRZ_AASrLcuoyjIQsumX0ipwfaGrxIKT06drKYGplEUwYNp6Mgw6YftwKh_ZsyFXck-";

	//PayPalEnvironmentProduction : @"AbX_1hNVSAX8FMM-pEA_BnA2BQFaarOYZu0fRhTZZBOPQQKBbEJRVUTmA4GkVgtfEIFxUuBS-jJqTUbS", 
	//EHaWqeM6w9lFwYXTmEWQI4mCpQaKyqMoQQlMFiK8M09gQ6qvkJcSNsq2o2YcMD2R7YRBx2JmSH1OOhW1

	//PayPalEnvironmentSandbox : @"AYAo1SDqerLUXdmTGTP67sx6JpKwg7gf-y5Trr9qmjekRUX8I-tjJbeAxwMOilcYkF1o4Q6hnCAcUE9o"
	//EKpGB46u3JTxBMRZ_AASrLcuoyjIQsumX0ipwfaGrxIKT06drKYGplEUwYNp6Mgw6YftwKh_ZsyFXck-

	/**
	 * 获取token
	 * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
	 * 
	 * @return
	 */
	private static String getAccessToken() {
		try {
			URL url = new URL(TOKEN_URL);
			String authorization = clientId + ":" + secret;
			authorization = Base64.encodeBase64String(authorization.getBytes());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");// 提交模式
			//设置请求头header
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Accept-Language", "en_US");
			conn.setRequestProperty("Authorization", "Basic " + authorization);
			// conn.setConnectTimeout(10000);//连接超时 单位毫秒
			// conn.setReadTimeout(2000);//读取超时 单位毫秒
			conn.setDoOutput(true);// 是否输入参数
			String params = "grant_type=client_credentials";
			conn.getOutputStream().write(params.getBytes());// 输入参数

			InputStreamReader inStream = new InputStreamReader(conn.getInputStream());
			BufferedReader reader = new BufferedReader(inStream);
			StringBuilder result = new StringBuilder();
			String lineTxt = null;
			while ((lineTxt = reader.readLine()) != null) {
				result.append(lineTxt);
			}
			reader.close();
			String accessTokey = JSONObject.fromObject(result.toString()).optString("access_token");
			System.out.println("getAccessToken:" + accessTokey);
			return accessTokey;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取支付详情
	 * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
	 * 
	 * @param paymentId
	 *            支付ID，来自于用户客户端
	 * @return
	 */
	public static String getPaymentDetails(String paymentId) {
		try {
			//			String str = WeixinUtil_HTTP.httpRequest_str(PAYMENT_DETAIL + paymentId, "GET", "", false, "application/json", "Bearer "
			//					+ getAccessToken());
			//			System.out.println(str);
			//			return str;

			URL url = new URL(PAYMENT_DETAIL + paymentId);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");// 提交模式
			//设置请求头header
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "Bearer " + getAccessToken());
			// conn.setConnectTimeout(10000);//连接超时 单位毫秒
			// conn.setReadTimeout(2000);//读取超时 单位毫秒
			InputStreamReader inStream = new InputStreamReader(conn.getInputStream());
			BufferedReader reader = new BufferedReader(inStream);
			StringBuilder result = new StringBuilder();
			String lineTxt = null;
			while ((lineTxt = reader.readLine()) != null) {
				result.append(lineTxt);
			}
			reader.close();
			return result.toString();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取支付详情
	 * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
	 * 
	 * @param paymentId
	 *            支付ID，来自于用户客户端
	 * @return
	 */
	public static boolean verifyPayment(String paymentId) {
		String str = getPaymentDetails(paymentId);
		if (str == null || str.length() == 0) {
			return false;
		}

		try {
			PaypalVerifyRet ret = JSON.parseObject(str, PaypalVerifyRet.class);
			if (ret != null && ret.getState().equals("approved")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {

		}

		return false;

		//		JSONObject detail = JSONObject.fromObject(str);
		//		//校验订单是否完成
		//		if ("approved".equals(detail.optString("state"))) {
		//			JSONObject transactions = detail.optJSONArray("transactions").optJSONObject(0);
		//			JSONObject amount = transactions.optJSONObject("amount");
		//			JSONArray relatedResources = transactions.optJSONArray("related_resources");
		//			//从数据库查询支付总金额与Paypal校验支付总金额
		//			double total = 0;
		//			if (total != amount.optDouble("total")) {
		//				return false;
		//			}
		//			//校验交易货币类型
		//			String currency = "USD";
		//			if (!currency.equals(amount.optDouble("currency"))) {
		//				return false;
		//			}
		//			//校验每个子订单是否完成
		//			for (int i = 0, j = relatedResources.size(); i < j; i++) {
		//				JSONObject sale = relatedResources.optJSONObject(i).optJSONObject("sale");
		//				if (!"completed".equals(sale.optString("state"))) {
		//					System.out.println("子订单未完成,订单状态:" + sale.optString("state"));
		//				}
		//			}
		//			return true;
		//		}
		//		return false;
	}

	public static void main(String[] args) {
		boolean success = verifyPayment("PAY-2MG69566X0552733KLAKZSCY");
		System.out.println(success ? "支付完成" : "支付校验失败");
	}
}
