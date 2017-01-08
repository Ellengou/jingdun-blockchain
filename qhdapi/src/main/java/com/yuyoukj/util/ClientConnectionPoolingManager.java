package com.yuyoukj.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;

public class ClientConnectionPoolingManager {
	final static Log logger = LogFactory.getLog(PoolingClientConnectionManager.class);

	private static Map<String, PoolingClientConnectionManager> cms = new ConcurrentHashMap<String, PoolingClientConnectionManager>();

	private static Map<String, HttpClient> clients = new ConcurrentHashMap<String, HttpClient>();

	private static HttpClient client;
	private static PoolingClientConnectionManager cm;
	private static int poolSize = 200;
	private static int maxSize = 300;

	public static HttpClient getClient(String api) {
		if (null == clients.get(api)) {
			HttpClient client = new DefaultHttpClient(getCm(api));
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			clients.put(api, client);
		}
		return clients.get(api);
		/*
		 * if (null == client) { client = new DefaultHttpClient(getCm(api)); client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
		 * client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT , 5000); } return client;
		 */
	}

	public static PoolingClientConnectionManager getCm(String api) {
		if (null == cms.get(api)) {
			PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
			cm.setMaxTotal(maxSize);
			cm.setDefaultMaxPerRoute(poolSize);
			cms.put(api, cm);
		}
		return cms.get(api);
		/*
		 * if (null == cm) { cm = new PoolingClientConnectionManager(); cm.setMaxTotal(maxSize); cm.setDefaultMaxPerRoute(poolSize); } return cm;
		 */
	}

	public static void releaseClientPoolingConnection() {
		if (null != cms) {
			for (Map.Entry entry : cms.entrySet()) {
				PoolingClientConnectionManager cm = (PoolingClientConnectionManager) entry.getValue();
				// 关闭过期的连接
				cm.closeExpiredConnections();
				// 关闭空闲时间超过30秒的连接
				cm.closeIdleConnections(10, TimeUnit.SECONDS);
			}
		}
	}
}
