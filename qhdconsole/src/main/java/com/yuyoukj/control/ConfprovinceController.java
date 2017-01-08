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
import com.yuyoukj.service.ConfprovinceService;
import com.yuyoukj.model.qhd.Confprovince;

@RequestMapping("/confprovince")
@Controller
public class ConfprovinceController extends AbstractFrontCommandController {

	@Autowired
	private ConfprovinceService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/confprovincelist.htm")
	public String confprovincelist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) { //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Confprovince> page = new Page<Confprovince>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getConfprovinceList(page, pmap));
		model.addAttribute("param", param);

		return "web/confprovincelist";
	}

	@RequestMapping("/confprovinceedit.htm")
	public String confprovinceedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		model.addAttribute("param", param);

		if (param.getId() != null) { //参数根据实际情况调整
			pmap.clear();
			pmap.put("id", param.getId()); //参数根据实际情况调整
			model.addAttribute("confprovince", ftlService.getConfprovince(pmap));
		}

		return "web/confprovinceedit";
	}

	@RequestMapping("/confprovincesave.htm")
	@ResponseBody
	public Response confprovincesave(Model model, RequestParameter param, @ModelAttribute Confprovince confprovince) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", confprovince.getBrandname());  //根据实际情况调整
		pmap.put("id", confprovince.getId());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		if (param.getId() != null) { //参数根据实际情况调整
			ftlService.updateConfprovince(confprovince);
		} else {
			ftlService.saveConfprovince(confprovince);
		}

		return res;
	}

	@RequestMapping("/confprovincedelete.htm")
	@ResponseBody
	public Response confprovincedelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getId() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

		//
		pmap.clear();
		pmap.put("id", param.getId());
		ftlService.delConfprovince(pmap);

		return res;
	}

}
