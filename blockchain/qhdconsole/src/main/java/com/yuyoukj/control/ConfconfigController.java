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

import com.yuyoukj.service.ConfconfigService;
import com.yuyoukj.model.qhd.Confconfig;

@RequestMapping("/confconfig")
@Controller
public class ConfconfigController extends AbstractFrontCommandController {

	@Autowired
	private ConfconfigService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/confconfiglist.htm")
	public String confconfiglist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) {  //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Confconfig> page = new Page<Confconfig>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getConfconfigList(page, pmap));
		model.addAttribute("param", param);

		return "web/confconfiglist";
	}

	@RequestMapping("/confconfigedit.htm")
	public String confconfigedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		model.addAttribute("param", param);

		if (param.getSeq() != null) {  //参数根据实际情况调整
			pmap.clear();
			pmap.put("seq", param.getSeq()); //参数根据实际情况调整
			model.addAttribute("confconfig", ftlService.getConfconfig(pmap));
		}

		return "web/confconfigedit";
	}

	@RequestMapping("/confconfigsave.htm")
	@ResponseBody
	public Response confconfigsave(Model model, RequestParameter param, @ModelAttribute Confconfig confconfig) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", confconfig.getBrandname());  //根据实际情况调整
		pmap.put("seq", confconfig.getSeq());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		confconfig.setCreatedate(System.currentTimeMillis());
		confconfig.setUpdatedate(System.currentTimeMillis());
		if (param.getSeq() != null) { //参数根据实际情况调整
			ftlService.updateConfconfig(confconfig);
		} else {
			ftlService.saveConfconfig(confconfig);
		}

		return res;
	}

	@RequestMapping("/confconfigdelete.htm")
	@ResponseBody
	public Response confconfigdelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getSeq() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

        //
		pmap.clear();
		pmap.put("seq", param.getSeq());
		ftlService.delConfconfig(pmap);

		return res;
	}

}
