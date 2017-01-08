package com.pingplusplus.model;

import java.util.List;

public class Event_obj {

	//	事件类型为 charge.succeeded :
	//	{
	//	    "id": "evt_ugB6x3K43D16wXCcqbplWAJo",
	//	    "created": 1427555101,
	//	    "livemode": true,
	//	    "type": "charge.succeeded",
	//	    "object": "event",
	//	    "pending_webhooks": 0,
	//	    "request": "iar_qH4y1KbTy5eLGm1uHSTS00s"
	//	    "data": {
	//	        "object": {
	//	            "id": "ch_Xsr7u35O3m1Gw4ed2ODmi4Lw",
	//	            "object": "charge",
	//	            "created": 1427555076,
	//	            "livemode": true,
	//	            "paid": true,
	//	            "refunded": false,
	//	            "app": "app_1Gqj58ynP0mHeX1q",
	//	            "channel": "upacp",
	//	            "order_no": "123456789",
	//	            "client_ip": "127.0.0.1",
	//	            "amount": 100,
	//	            "amount_settle": 100,
	//	            "currency": "cny",
	//	            "subject": "Your Subject",
	//	            "body": "Your Body",
	//	            "extra": {},
	//	            "time_paid": 1427555101,
	//	            "time_expire": 1427641476,
	//	            "time_settle": null,
	//	            "transaction_no": "1224524301201505066067849274",
	//	            "refunds": {
	//	                "object": "list",
	//	                "url": "/v1/charges/ch_L8qn10mLmr1GS8e5OODmHaL4/refunds",
	//	                "has_more": false,
	//	                "data": []
	//	            },
	//	            "amount_refunded": 0,
	//	            "failure_code": null,
	//	            "failure_msg": null,
	//	            "metadata": {},
	//	            "credential": {},
	//	            "description": null
	//	        }
	//	    },
	//	}

	//	事件类型为 batch_transfer.succeeded :
	//	{
	//	    "id": "evt_ca16CoQsiP2ja1JKs5gx3j4q",
	//	    "object": "event",
	//	    "created": 1475924802,
	//	    "livemode": false,
	//	    "pending_webhooks": 0,
	//	    "request": "iar_1KeD0GHi58yLfDyHK4HCOyDS",
	//	    "type": "batch_transfer.succeeded"
	//	    "data": {
	//	        "object": {
	//	            "id": "181610101014367590",
	//	            "object": "batch_transfer",
	//	            "app": "app_ribgW1n2SOqcPxn1",
	//	            "amount": 8000,
	//	            "batch_no": "2016101010380007",
	//	            "channel": "alipay",
	//	            "currency": "cny",
	//	            "created": 1476067087,
	//	            "description": "付款说明",
	//	            "extra": {},
	//	            "failure_msg": null,
	//	            "fee": 200,
	//	            "livemode": true,
	//	            "metadata": {},
	//	            "recipients": [
	//	                {
	//	                    "account": "account01@alipay.com",
	//	                    "amount": 5000,
	//	                    "name": "张三",
	//	                    "transfer": "tr_jHWfvDnTKG0SiPmbfPbHW1eH",
	//	                    "status": "paid"
	//	                },
	//	                {
	//	                    "account": "account02@alipay.com",
	//	                    "amount": 3000,
	//	                    "name": "李四",
	//	                    "transfer": "tr_8u1yPK1eHWv9D08OePzDe1CK",
	//	                    "status": "paid"
	//	                }
	//	            ],
	//	            "status": "succeeded",
	//	            "time_succeeded": 1476067147,
	//	            "type": "b2c"
	//	        }
	//	    },
	//	}

	private String id;
	private String object;
	private Long created;
	private boolean livemode;
	private Integer pending_webhooks;
	private String request;
	private String type;
	private List<Data_obj> data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public boolean isLivemode() {
		return livemode;
	}

	public void setLivemode(boolean livemode) {
		this.livemode = livemode;
	}

	public Integer getPending_webhooks() {
		return pending_webhooks;
	}

	public void setPending_webhooks(Integer pending_webhooks) {
		this.pending_webhooks = pending_webhooks;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Data_obj> getData() {
		return data;
	}

	public void setData(List<Data_obj> data) {
		this.data = data;
	}

}
