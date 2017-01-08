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

import com.yuyoukj.service.InfopayconfigService;
import com.yuyoukj.model.qhd.Infopayconfig;

@RequestMapping("/infopayconfig")
@Controller
public class InfopayconfigController extends AbstractFrontCommandController {

	@Autowired
	private InfopayconfigService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/infopayconfiglist.htm")
	public String infopayconfiglist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) {  //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Infopayconfig> page = new Page<Infopayconfig>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getInfopayconfigList(page, pmap));
		model.addAttribute("param", param);

		return "web/infopayconfiglist";
	}

	@RequestMapping("/infopayconfigedit.htm")
	public String infopayconfigedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		model.addAttribute("param", param);

		if (param.getSeq() != null) {  //参数根据实际情况调整
			pmap.clear();
			pmap.put("seq", param.getSeq()); //参数根据实际情况调整
			model.addAttribute("infopayconfig", ftlService.getInfopayconfig(pmap));
		}

		return "web/infopayconfigedit";
	}

	@RequestMapping("/infopayconfigsave.htm")
	@ResponseBody
	public Response infopayconfigsave(Model model, RequestParameter param, @ModelAttribute Infopayconfig infopayconfig) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", infopayconfig.getBrandname());  //根据实际情况调整
		pmap.put("seq", infopayconfig.getSeq());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		infopayconfig.setCreatedate(System.currentTimeMillis());
		infopayconfig.setUpdatedate(System.currentTimeMillis());
		if (param.getSeq() != null) { //参数根据实际情况调整
			ftlService.updateInfopayconfig(infopayconfig);
		} else {
			ftlService.saveInfopayconfig(infopayconfig);
		}

		return res;
	}

	@RequestMapping("/infopayconfigdelete.htm")
	@ResponseBody
	public Response infopayconfigdelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getSeq() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

        //
		pmap.clear();
		pmap.put("seq", param.getSeq());
		ftlService.delInfopayconfig(pmap);

		return res;
	}

}
