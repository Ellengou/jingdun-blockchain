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

import com.yuyoukj.service.LogpasswdService;
import com.yuyoukj.model.qhd.Logpasswd;

@RequestMapping("/logpasswd")
@Controller
public class LogpasswdController extends AbstractFrontCommandController {

	@Autowired
	private LogpasswdService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/logpasswdlist.htm")
	public String logpasswdlist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) {  //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Logpasswd> page = new Page<Logpasswd>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getLogpasswdList(page, pmap));
		model.addAttribute("param", param);

		return "web/logpasswdlist";
	}

	@RequestMapping("/logpasswdedit.htm")
	public String logpasswdedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		model.addAttribute("param", param);

		if (param.getSeq() != null) {  //参数根据实际情况调整
			pmap.clear();
			pmap.put("seq", param.getSeq()); //参数根据实际情况调整
			model.addAttribute("logpasswd", ftlService.getLogpasswd(pmap));
		}

		return "web/logpasswdedit";
	}

	@RequestMapping("/logpasswdsave.htm")
	@ResponseBody
	public Response logpasswdsave(Model model, RequestParameter param, @ModelAttribute Logpasswd logpasswd) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", logpasswd.getBrandname());  //根据实际情况调整
		pmap.put("seq", logpasswd.getSeq());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		logpasswd.setCreatedate(System.currentTimeMillis());
		logpasswd.setUpdatedate(System.currentTimeMillis());
		if (param.getSeq() != null) { //参数根据实际情况调整
			ftlService.updateLogpasswd(logpasswd);
		} else {
			ftlService.saveLogpasswd(logpasswd);
		}

		return res;
	}

	@RequestMapping("/logpasswddelete.htm")
	@ResponseBody
	public Response logpasswddelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getSeq() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

        //
		pmap.clear();
		pmap.put("seq", param.getSeq());
		ftlService.delLogpasswd(pmap);

		return res;
	}

}
