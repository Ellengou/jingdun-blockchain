package www.yuyoukj.third.paypal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.yuyoukj.util.constants.WebConstants;

public class PayPalVerifyPayment {

	private Logger log = Logger.getLogger(PayPalVerifyPayment.class);

	//	private static final String TOKEN_URL = "https://api.sandbox.paypal.com/v1/oauth2/token";
	//	private static final String PAYMENT_DETAIL = "https://api.sandbox.paypal.com/v1/payments/payment/";
	//	private static final String clientId = "AYAo1SDqerLUXdmTGTP67sx6JpKwg7gf-y5Trr9qmjekRUX8I-tjJbeAxwMOilcYkF1o4Q6hnCAcUE9o";
	//	private static final String secret = "EKpGB46u3JTxBMRZ_AASrLcuoyjIQsumX0ipwfaGrxIKT06drKYGplEUwYNp6Mgw6YftwKh_ZsyFXck-";

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
	private String getAccessToken() {
		long a = System.currentTimeMillis();

		try {
			URL url = new URL(WebConstants.PAYPAL_TOKEN_URL);
			String authorization = WebConstants.PAYPAL_clientId + ":" + WebConstants.PAYPAL_secret;
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
			log.error("===paypal accesstoken: " + accessTokey + " " + (System.currentTimeMillis() - a) / 1000.0);

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
	private String getPaymentDetails(String paymentId) {
		long a = System.currentTimeMillis();

		try {
			URL url = new URL(WebConstants.PAYPAL_PAYMENT_DETAIL + paymentId);
			log.error("===verify url: " + WebConstants.PAYPAL_PAYMENT_DETAIL + paymentId);
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
			log.error("===paypal verify result: " + (System.currentTimeMillis() - a) / 1000.0);

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
	public boolean verifyPayment(String paymentId) {
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
	}

	public static void main(String[] args) {
		PayPalVerifyPayment paym = new PayPalVerifyPayment();
		boolean success = paym.verifyPayment("PAY-2MG69566X0552733KLAKZSCY");
		System.out.println(success ? "支付完成" : "支付校验失败");
	}
}
