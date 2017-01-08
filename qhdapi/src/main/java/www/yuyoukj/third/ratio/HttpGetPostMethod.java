package www.yuyoukj.third.ratio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

public class HttpGetPostMethod {
	private static Logger logger = Logger.getLogger(HttpGetPostMethod.class);
	private static final String ENCODE_UTF8 = "utf-8";
	private static final String ENCODE_GBK = "gbk";
	private static final String ENCODE_ISO8859 = "iso-8859-1";

	/**
	 * 拼装http post参数
	 * 
	 * @param url
	 * @param pamp
	 * @return
	 */
	public static HttpMethod getMethod(String url, Map<String, String> pamp) {
		PostMethod postMethod = new PostMethod(url);
		// List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (pamp != null && pamp.keySet().size() > 0) {
			Iterator iterator = pamp.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				postMethod.setParameter((String) entry.getKey(), (String) entry.getValue());
			}
		}
		return postMethod;
	}

	/**
	 * http post请求
	 * 
	 * @param url
	 * @param pmap
	 * @param httpClient
	 * @param postMethod
	 * @return
	 */
	public static HttpRet postUrl(String url, Map<String, String> pmap, HttpClient httpClient, HttpMethod postMethod) {
		HttpRet httpret = null;
		try {
			postMethod = getMethod(url, pmap);
			httpClient.executeMethod(postMethod);

			httpret = new HttpRet();
			httpret.setStatuscode(postMethod.getStatusCode());

			InputStream is = postMethod.getResponseBodyAsStream();

			if (is == null)
				return null;

			BufferedReader br = null;
			StringBuffer resBuffer = new StringBuffer();
			try {
				br = new BufferedReader(new InputStreamReader(is, ENCODE_ISO8859));
				String resTemp = "";
				while ((resTemp = br.readLine()) != null) {
					// resBuffer.append(resTemp + "\r\n");
					resBuffer.append(new String(resTemp.getBytes(ENCODE_ISO8859), ENCODE_UTF8) + "\r\n");
					// resBuffer.append(new String(new String(resTemp.getBytes(ENCODE_ISO8859), ENCODE_GBK).getBytes(ENCODE_GBK), ENCODE_UTF8) +
					// "\r\n");
				}
				br.close();
				is.close();
				// logger.warn(resBuffer.toString());
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (br != null) {
					br.close();
					br = null;
				}
			}

			// ///////////
			// httpret.setResponseInfo(new String(postMethod.getResponseBodyAsString().getBytes("UTF-8"), ENCODE));
			httpret.setResponseInfo(resBuffer.toString());
			postMethod.releaseConnection();

			logger.warn("");
			logger.warn(url);
			logger.warn(httpret.getResponseInfo());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return httpret;
	}

	/**
	 * 处理http get请求
	 * 
	 * @param url
	 * @param httpClient
	 * @param getPostMethod
	 * @return
	 */
	public static HttpRet getUrl(String url, HttpClient httpClient, HttpMethod getPostMethod) {
		HttpRet httpret = null;
		try {
			getPostMethod = new GetMethod(url);
			httpClient.executeMethod(getPostMethod);
			httpret = new HttpRet();
			httpret.setStatuscode(getPostMethod.getStatusCode());
			// ///////////
			//getPostMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + ENCODE_GBK);
			InputStream resStream = getPostMethod.getResponseBodyAsStream();
			BufferedReader br = null;
			StringBuffer resBuffer = new StringBuffer();
			try {
				br = new BufferedReader(new InputStreamReader(resStream, ENCODE_ISO8859));
				String resTemp = "";
				while ((resTemp = br.readLine()) != null) {
					// resBuffer.append(resTemp + "\r\n");
					resBuffer.append(new String(resTemp.getBytes(ENCODE_ISO8859), ENCODE_UTF8));
					// resBuffer.append(new String(new String(resTemp.getBytes(ENCODE_ISO8859), ENCODE_GBK).getBytes(ENCODE_GBK), ENCODE_UTF8) +
					// "\r\n");
				}
				br.close();
				resStream.close();
				// logger.warn(resBuffer.toString());
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (br != null) {
					br.close();
					br = null;
				}
			}
			// ///////////
			// httpret.setResponseInfo(new String(getPostMethod.getResponseBodyAsString().getBytes("UTF-8"), ENCODE));
			httpret.setResponseInfo(resBuffer.toString());
			getPostMethod.releaseConnection();
			logger.warn("");
			logger.warn(url);
			logger.warn(httpret.getResponseInfo());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return httpret;
	}
}
