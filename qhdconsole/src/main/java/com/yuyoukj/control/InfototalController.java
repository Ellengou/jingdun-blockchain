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

import com.yuyoukj.service.InfototalService;
import com.yuyoukj.model.qhd.Infototal;

@RequestMapping("/infototal")
@Controller
public class InfototalController extends AbstractFrontCommandController {

	@Autowired
	private InfototalService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/infototallist.htm")
	public String infototallist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) {  //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Infototal> page = new Page<Infototal>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getInfototalList(page, pmap));
		model.addAttribute("param", param);

		return "web/infototallist";
	}

	@RequestMapping("/infototaledit.htm")
	public String infototaledit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		model.addAttribute("param", param);

		if (param.getTotalid() != null) {  //参数根据实际情况调整
			pmap.clear();
			pmap.put("totalid", param.getTotalid()); //参数根据实际情况调整
			model.addAttribute("infototal", ftlService.getInfototal(pmap));
		}

		return "web/infototaledit";
	}

	@RequestMapping("/infototalsave.htm")
	@ResponseBody
	public Response infototalsave(Model model, RequestParameter param, @ModelAttribute Infototal infototal) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", infototal.getBrandname());  //根据实际情况调整
		pmap.put("totalid", infototal.getTotalid());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		infototal.setCreatedate(System.currentTimeMillis());
		infototal.setUpdatedate(System.currentTimeMillis());
		if (param.getTotalid() != null) { //参数根据实际情况调整
			ftlService.updateInfototal(infototal);
		} else {
			ftlService.saveInfototal(infototal);
		}

		return res;
	}

	@RequestMapping("/infototaldelete.htm")
	@ResponseBody
	public Response infototaldelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getTotalid() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

        //
		pmap.clear();
		pmap.put("totalid", param.getTotalid());
		ftlService.delInfototal(pmap);

		return res;
	}

}
