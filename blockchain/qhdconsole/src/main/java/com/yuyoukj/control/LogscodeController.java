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

import com.yuyoukj.service.LogscodeService;
import com.yuyoukj.model.qhd.Logscode;

@RequestMapping("/logscode")
@Controller
public class LogscodeController extends AbstractFrontCommandController {

	@Autowired
	private LogscodeService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/logscodelist.htm")
	public String logscodelist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) {  //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Logscode> page = new Page<Logscode>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getLogscodeList(page, pmap));
		model.addAttribute("param", param);

		return "web/logscodelist";
	}

	@RequestMapping("/logscodeedit.htm")
	public String logscodeedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		model.addAttribute("param", param);

		if (param.getSeq() != null) {  //参数根据实际情况调整
			pmap.clear();
			pmap.put("seq", param.getSeq()); //参数根据实际情况调整
			model.addAttribute("logscode", ftlService.getLogscode(pmap));
		}

		return "web/logscodeedit";
	}

	@RequestMapping("/logscodesave.htm")
	@ResponseBody
	public Response logscodesave(Model model, RequestParameter param, @ModelAttribute Logscode logscode) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", logscode.getBrandname());  //根据实际情况调整
		pmap.put("seq", logscode.getSeq());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		logscode.setCreatedate(System.currentTimeMillis());
		logscode.setUpdatedate(System.currentTimeMillis());
		if (param.getSeq() != null) { //参数根据实际情况调整
			ftlService.updateLogscode(logscode);
		} else {
			ftlService.saveLogscode(logscode);
		}

		return res;
	}

	@RequestMapping("/logscodedelete.htm")
	@ResponseBody
	public Response logscodedelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getSeq() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

        //
		pmap.clear();
		pmap.put("seq", param.getSeq());
		ftlService.delLogscode(pmap);

		return res;
	}

}
