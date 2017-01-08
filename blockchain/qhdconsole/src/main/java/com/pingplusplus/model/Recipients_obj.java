package com.pingplusplus.model;

public class Recipients_obj {
	//	account //	接收者支付宝账号。
	//	amount //	付款金额。
	//	name //	接收者姓名。
	//	description //	批量企业付款描述，不超过200个字节，默认值为外层参数中的 description 。

	private String account;
	private Integer amount;
	private String name;
	private String description;
	private String transfer;
	private String status;

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
