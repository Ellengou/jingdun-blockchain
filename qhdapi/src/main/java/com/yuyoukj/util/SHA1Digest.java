package com.yuyoukj.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Digest {
	public static final String CHARSET_NAME = "UTF-8";

	public static SHA1Digest getInstance(String content, String... charsetName) {
		SHA1Digest sha1 = new SHA1Digest(content);
		if (charsetName.length > 0) {
			sha1.setCharset(Charset.forName(charsetName[0]));
		}
		return sha1;
	}

	public static String getSHA1Digest(String origin) {
		return getInstance(origin).toString();
	}

	public static void main(String[] args) {
		SHA1Digest sha1 = SHA1Digest.getInstance("180373e97b3f");// 270ce91f6de983ecce7d200e320f690b
		System.out.println(sha1.toString()); // 13f4a00c50719f02f1d82ee5008927a8025dab6a
	}

	private MessageDigest sha1;
	private String content;
	private Charset charset = Charset.forName(CHARSET_NAME);

	private SHA1Digest(String content) {
		try {
			this.setSha1(MessageDigest.getInstance("SHA-1"));
			this.setContent(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object o) {
		return toString().equals(o);
	}

	/**
	 * @return charset
	 */
	public Charset getCharset() {
		return this.charset;
	}

	/**
	 * @return content
	 */
	public String getContent() {
		return this.content;
	}

	public byte[] getDigest() {
		return this.getSha1().digest(getContent().getBytes(this.getCharset()));
	}

	public MessageDigest getSha1() {
		return sha1;
	}

	/**
	 * @param charset
	 *            要设置的 charset
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	/**
	 * @param content
	 *            要设置的 content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public void setSha1(MessageDigest sha1) {
		this.sha1 = sha1;
	}

	// public static String bytesToString(byte[] data) {
	// char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	// 'a', 'b', 'c', 'd',
	// 'e', 'f'};
	// char[] temp = new char[data.length * 2];
	// for (int i = 0; i < data.length; i++) {
	// byte b = data[i];
	// temp[i * 2] = hexDigits[b >>> 4 & 0x0f];
	// temp[i * 2 + 1] = hexDigits[b & 0x0f];
	// }
	// return new String(temp);
	// }
	@Override
	public String toString() {
		byte[] digest = this.getDigest();
		StringBuffer buf = new StringBuffer();
		for (byte temp : digest) {
			buf.append(DataUtil.toFullHex(temp));
		}
		return buf.toString();
	}
}
