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
import com.yuyoukj.service.ConfcityService;
import com.yuyoukj.model.qhd.Confcity;

@RequestMapping("/confcity")
@Controller
public class ConfcityController extends AbstractFrontCommandController {

	@Autowired
	private ConfcityService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/confcitylist.htm")
	public String confcitylist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) { //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Confcity> page = new Page<Confcity>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getConfcityList(page, pmap));
		model.addAttribute("param", param);

		return "web/confcitylist";
	}

	@RequestMapping("/confcityedit.htm")
	public String confcityedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		model.addAttribute("param", param);

		if (param.getId() != null) { //参数根据实际情况调整
			pmap.clear();
			pmap.put("id", param.getId()); //参数根据实际情况调整
			model.addAttribute("confcity", ftlService.getConfcity(pmap));
		}

		return "web/confcityedit";
	}

	@RequestMapping("/confcitysave.htm")
	@ResponseBody
	public Response confcitysave(Model model, RequestParameter param, @ModelAttribute Confcity confcity) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", confcity.getBrandname());  //根据实际情况调整
		pmap.put("id", confcity.getId());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		if (param.getId() != null) { //参数根据实际情况调整
			ftlService.updateConfcity(confcity);
		} else {
			ftlService.saveConfcity(confcity);
		}

		return res;
	}

	@RequestMapping("/confcitydelete.htm")
	@ResponseBody
	public Response confcitydelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getId() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

		//
		pmap.clear();
		pmap.put("id", param.getId());
		ftlService.delConfcity(pmap);

		return res;
	}

}
