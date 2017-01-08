package com.pingplusplus.model;

/**
 * Created by sunkai on 15/5/11.
 */

import com.pingplusplus.exception.*;
import com.pingplusplus.net.APIResource;
import java.util.List;
import java.util.Map;

/**
 * 微信企业付款
 */
public class Batch_transfer extends APIResource {

	String id;
	String object;
	Object app;
	Integer amount;
	String batch_no;
	String channel;
	String currency;
	Long created;
	String description;
	Map extra;
	String failureMsg;
	Integer fee;
	Boolean livemode;
	Map<String, String> metadata;
	List<Recipients_obj> recipients;
	String status;
	Long time_succeeded;
	String type;

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

	public Object getApp() {
		return app;
	}

	public void setApp(Object app) {
		this.app = app;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map getExtra() {
		return extra;
	}

	public void setExtra(Map extra) {
		this.extra = extra;
	}

	public String getFailureMsg() {
		return failureMsg;
	}

	public void setFailureMsg(String failureMsg) {
		this.failureMsg = failureMsg;
	}

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public Boolean getLivemode() {
		return livemode;
	}

	public void setLivemode(Boolean livemode) {
		this.livemode = livemode;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public List<Recipients_obj> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<Recipients_obj> recipients) {
		this.recipients = recipients;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTime_succeeded() {
		return time_succeeded;
	}

	public void setTime_succeeded(Long time_succeeded) {
		this.time_succeeded = time_succeeded;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 创建 TransferBatch
	 * 
	 * @param params
	 * @return
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 * @throws ChannelException
	 */
	public static Batch_transfer create(Map<String, Object> params) throws AuthenticationException, InvalidRequestException, APIConnectionException,
			APIException, ChannelException, RateLimitException {
		return request(RequestMethod.POST, classURL(Batch_transfer.class), params, Batch_transfer.class);
	}

}
