package com.yuyoukj.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yuyoukj.async.service.MyAsyncService;
import com.yuyoukj.util.RequestParameter;

/**
 * 网页展示类、跳转类
 * 
 * @author karv
 * 
 */
@RequestMapping("/open")
@Controller
public class OpenController {

	private static Logger log = Logger.getLogger(OpenController.class);

	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private HttpSession httpsession;

	@RequestMapping("/open.htm")
	@ResponseBody
	public String open(HttpServletRequest request, HttpServletResponse response, RequestParameter param) {
		String ret = null;

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");

		/** 读取接收到的xml消息 */
		StringBuilder sb = new StringBuilder();
		InputStream is;
		try {
			is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}

			br.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String xml = sb.toString();
		log.error("===xml: " + xml);
		sb = null;

		return ret;
	}

}
