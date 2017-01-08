package com.paypal;

public class PaypalVerifyRet {

	private String id; //PAY-2MG69566X0552733KLAKZSCY,
	private String intent; //sale,
	private String state; //approved,
	private String cart; //0B886528RG3104510,
	private String create_time; //2016-10-30T06;54;10Z,
	private String update_time; //2016-10-30T06;55;07Z,

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCart() {
		return cart;
	}

	public void setCart(String cart) {
		this.cart = cart;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

}
