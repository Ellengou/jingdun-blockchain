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

import com.yuyoukj.service.ConfratioService;
import com.yuyoukj.model.qhd.Confratio;

@RequestMapping("/confratio")
@Controller
public class ConfratioController extends AbstractFrontCommandController {

	@Autowired
	private ConfratioService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/confratiolist.htm")
	public String confratiolist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) {  //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Confratio> page = new Page<Confratio>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getConfratioList(page, pmap));
		model.addAttribute("param", param);

		return "web/confratiolist";
	}

	@RequestMapping("/confratioedit.htm")
	public String confratioedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		model.addAttribute("param", param);

		if (param.getRatioid() != null) {  //参数根据实际情况调整
			pmap.clear();
			pmap.put("ratioid", param.getRatioid()); //参数根据实际情况调整
			model.addAttribute("confratio", ftlService.getConfratio(pmap));
		}

		return "web/confratioedit";
	}

	@RequestMapping("/confratiosave.htm")
	@ResponseBody
	public Response confratiosave(Model model, RequestParameter param, @ModelAttribute Confratio confratio) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", confratio.getBrandname());  //根据实际情况调整
		pmap.put("ratioid", confratio.getRatioid());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		confratio.setCreatedate(System.currentTimeMillis());
		confratio.setUpdatedate(System.currentTimeMillis());
		if (param.getRatioid() != null) { //参数根据实际情况调整
			ftlService.updateConfratio(confratio);
		} else {
			ftlService.saveConfratio(confratio);
		}

		return res;
	}

	@RequestMapping("/confratiodelete.htm")
	@ResponseBody
	public Response confratiodelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getRatioid() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

        //
		pmap.clear();
		pmap.put("ratioid", param.getRatioid());
		ftlService.delConfratio(pmap);

		return res;
	}

}
