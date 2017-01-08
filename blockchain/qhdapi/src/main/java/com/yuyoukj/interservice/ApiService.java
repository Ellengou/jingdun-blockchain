package com.yuyoukj.interservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yuyoukj.ao.interfaceframework.exception.RcException;
import com.yuyoukj.response.ApiResponse;
import com.yuyoukj.sdk.RequestParam;

public interface ApiService {

	public ApiResponse exit(RequestParam param) throws RcException;

	public ApiResponse iconupload(RequestParam param) throws RcException;

	public ApiResponse itemadd(RequestParam param) throws RcException;

	public ApiResponse itemlist(RequestParam param) throws RcException;

	public ApiResponse login(RequestParam param) throws RcException;

	public ApiResponse orderadd(RequestParam param) throws RcException;

	public ApiResponse orderpay(RequestParam param) throws RcException;

	public ApiResponse passwordreset(RequestParam param) throws RcException;

	public ApiResponse register(RequestParam param) throws RcException;

	public ApiResponse scodeget(RequestParam param) throws RcException;

	public ApiResponse sysrecom(RequestParam param) throws RcException;

	public ApiResponse userperfect(RequestParam param) throws RcException;

	public ApiResponse userview(RequestParam param) throws RcException;

	public ApiResponse itemdel(RequestParam param) throws RcException;

}
