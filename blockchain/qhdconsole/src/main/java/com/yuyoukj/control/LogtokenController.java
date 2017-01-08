package com.yuyoukj.control;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yuyoukj.filter.AbstractFrontCommandController;
import com.yuyoukj.ao.model.Page;
import com.yuyoukj.ao.model.Response;
import com.yuyoukj.async.service.MyAsyncService;
import com.yuyoukj.util.RequestParameter;
import com.yuyoukj.util.constants.WebConstants;

import com.yuyoukj.service.LogtokenService;
import com.yuyoukj.model.qhd.Logtoken;

@RequestMapping("/logtoken")
@Controller
public class LogtokenController extends AbstractFrontCommandController {

	@Autowired
	private LogtokenService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/logtokenlist.htm")
	public String logtokenlist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) {  //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Logtoken> page = new Page<Logtoken>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getLogtokenList(page, pmap));
		model.addAttribute("param", param);

		return "web/logtokenlist";
	}

	@RequestMapping("/logtokenedit.htm")
	public String logtokenedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		model.addAttribute("param", param);

		if (param.getSeq() != null) {  //参数根据实际情况调整
			pmap.clear();
			pmap.put("seq", param.getSeq()); //参数根据实际情况调整
			model.addAttribute("logtoken", ftlService.getLogtoken(pmap));
		}

		return "web/logtokenedit";
	}

	@RequestMapping("/logtokensave.htm")
	@ResponseBody
	public Response logtokensave(Model model, RequestParameter param, @ModelAttribute Logtoken logtoken) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", logtoken.getBrandname());  //根据实际情况调整
		pmap.put("seq", logtoken.getSeq());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		logtoken.setCreatedate(System.currentTimeMillis());
		logtoken.setUpdatedate(System.currentTimeMillis());
		if (param.getSeq() != null) { //参数根据实际情况调整
			ftlService.updateLogtoken(logtoken);
		} else {
			ftlService.saveLogtoken(logtoken);
		}

		return res;
	}

	@RequestMapping("/logtokendelete.htm")
	@ResponseBody
	public Response logtokendelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getSeq() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

        //
		pmap.clear();
		pmap.put("seq", param.getSeq());
		ftlService.delLogtoken(pmap);

		return res;
	}

}
