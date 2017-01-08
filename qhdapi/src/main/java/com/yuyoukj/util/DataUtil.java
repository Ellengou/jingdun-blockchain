package com.yuyoukj.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

public class DataUtil {
	/**
	 * 
	 * @param s
	 * @param type
	 * @return
	 */
	public static String checkMac(String s, int type) {
		if (s == null) {
			return null;
		}
		switch (type) {
			case 0: {
				s = s.replaceAll(":", "");
				StringBuffer b = new StringBuffer();
				b.append(s.substring(0, 2)).append(":").append(s.substring(2, 4)).append(":").append(s.substring(4, 6)).append(":")
						.append(s.substring(6, 8)).append(":").append(s.substring(8, 10)).append(":").append(s.substring(10, 12));
				return b.toString().toLowerCase();
			}
			case 1:
				return s.replaceAll(":", "").toLowerCase();
			case 2: {
				s = s.replaceAll(":", "");
				StringBuffer b = new StringBuffer();
				b.append(s.substring(0, 2)).append(":").append(s.substring(2, 4)).append(":").append(s.substring(4, 6)).append(":")
						.append(s.substring(6, 8)).append(":").append(s.substring(8, 10)).append(":").append(s.substring(10, 12));
				return b.toString().toUpperCase();
			}
			case 3:
				return s.replaceAll(":", "").toUpperCase();
			default:
				return s;
		}
	}

	/**
	 * URL-encodes a string.
	 */
	public static String escape(String s, String... enc) {
		if (s == null) {
			return null;
		}
		try {
			return URLEncoder.encode(s, enc.length > 0 ? enc[0] : "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static int getRandomDigit(int max) {
		Random rand = new Random();
		return rand.nextInt(max); // 生成0-max以内的随机数
	}

	/**
	 * 签名生成算法
	 * 
	 * @param HashMap
	 *            <String,String> params 请求参数集，所有参数必须已转换为字符串类 型
	 * @param String
	 *            secret 签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature(HashMap<String, String> params, String secret) {
		// 先将参数以其参数名的字典序升序进行排序
		Map<String, String> sortedParams = new TreeMap<String, String>(params);
		Set<Entry<String, String>> entrys = sortedParams.entrySet();
		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		for (Entry<String, String> param : entrys) {
			basestring.append(param.getKey()).append("=").append(param.getValue());
		}
		basestring.append(secret);
		// 使用MD5对待签名串求签
		byte[] bytes = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// 将MD5输出的二进制结果转换为小写的十六进制
		StringBuilder sign = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(b & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex);
		}
		return sign.toString();
	}

	public static String getToken() {
		return UUID.randomUUID().toString();
	}

	public static String getUrlParams(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(map.keySet());
		int icount = 0;
		for (String key : keys) {
			if (icount > 0) {
				sb.append("&");
			}
			sb.append(key);
			sb.append("=");
			sb.append(map.get(key));
			icount++;
		}
		return sb.toString();
	}

	/**
	 * e14f2938-8083-4f85-b78f-9af2df88dae0
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		System.out.println(getToken());
	}

	public static Map<String, String> splitQueryString(String queryString) {
		Map<String, String> result = new HashMap<String, String>(0);
		String[] keyValuePairs = queryString.split("\\&");
		for (String keyValuePair : keyValuePairs) {
			String[] keyValue = keyValuePair.split("=");
			result.put(keyValue[0], keyValue[1]);
		}
		return result;
	}

	public static String toFullHex(byte data) {
		int val = (data) & 0xff;
		if (val < 16) {
			return "0" + Integer.toHexString(val);
		} else {
			return Integer.toHexString(val);
		}
	}

	public static String toHex(int value, int bit) {
		String temp = Integer.toHexString(value);
		for (int i = temp.length(); i < bit; i++) {
			temp = "0" + temp;
		}
		return temp;
	}

	public static String toString(Serializable[] sers, String... separator) {
		String result = "";
		String _separator = separator.length > 0 ? separator[0] : ",";
		if (sers != null && sers.length > 0) {
			for (Serializable ser : sers) {
				if (ser != null) {
					result = result + ser + _separator;
				}
			}
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	/**
	 * URL-decodes a string.
	 */
	public static String unescape(String s, String... enc) {
		if (s == null) {
			return null;
		}
		try {
			return URLDecoder.decode(s, enc.length > 0 ? enc[0] : "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean validateAbility(long a, long t) {
		if (a == (a | t)) {
			return true;
		}
		return false;
	}
}
