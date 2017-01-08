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
import com.yuyoukj.service.ConfareaService;
import com.yuyoukj.model.qhd.Confarea;

@RequestMapping("/confarea")
@Controller
public class ConfareaController extends AbstractFrontCommandController {

	@Autowired
	private ConfareaService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/confarealist.htm")
	public String confarealist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) { //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Confarea> page = new Page<Confarea>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getConfareaList(page, pmap));
		model.addAttribute("param", param);

		return "web/confarealist";
	}

	@RequestMapping("/confareaedit.htm")
	public String confareaedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		model.addAttribute("param", param);

		if (param.getId() != null) { //参数根据实际情况调整
			pmap.clear();
			pmap.put("id", param.getId()); //参数根据实际情况调整
			model.addAttribute("confarea", ftlService.getConfarea(pmap));
		}

		return "web/confareaedit";
	}

	@RequestMapping("/confareasave.htm")
	@ResponseBody
	public Response confareasave(Model model, RequestParameter param, @ModelAttribute Confarea confarea) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", confarea.getBrandname());  //根据实际情况调整
		pmap.put("id", confarea.getId());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		if (param.getId() != null) { //参数根据实际情况调整
			ftlService.updateConfarea(confarea);
		} else {
			ftlService.saveConfarea(confarea);
		}

		return res;
	}

	@RequestMapping("/confareadelete.htm")
	@ResponseBody
	public Response confareadelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getId() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

		//
		pmap.clear();
		pmap.put("id", param.getId());
		ftlService.delConfarea(pmap);

		return res;
	}

}
