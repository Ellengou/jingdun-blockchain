package com.yuyoukj.sdk;
public class BaseSDKResponse {
	private int Rc;
	private String message;
	private String xmlcontent;

	public String getMessage() {
		return this.message;
	}

	/**
	 * @return the rc
	 */
	public int getRc() {
		return Rc;
	}

	public String getXmlcontent() {
		return xmlcontent;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param rc
	 *            the rc to set
	 */
	public void setRc(int rc) {
		Rc = rc;
	}

	public void setXmlcontent(String xmlcontent) {
		this.xmlcontent = xmlcontent;
	}
}
