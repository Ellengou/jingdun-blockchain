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
import com.yuyoukj.ao.model.Page;
import com.yuyoukj.ao.model.Response;
import com.yuyoukj.async.service.MyAsyncService;
import com.yuyoukj.filter.AbstractFrontCommandController;
import com.yuyoukj.model.qhd.Infototalordertrade;
import com.yuyoukj.service.InfototalordertradeService;
import com.yuyoukj.util.RequestParameter;
import com.yuyoukj.util.constants.WebConstants;

@RequestMapping("/infototalordertrade")
@Controller
public class InfototalordertradeController extends AbstractFrontCommandController {

	@Autowired
	private InfototalordertradeService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/infototalordertradelist.htm")
	public String infototalordertradelist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) { //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Infototalordertrade> page = new Page<Infototalordertrade>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getInfototalordertradeList(page, pmap));
		model.addAttribute("param", param);

		return "web/infototalordertradelist";
	}

	@RequestMapping("/infototalordertradeedit.htm")
	public String infototalordertradeedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		model.addAttribute("param", param);

		if (param.getSeq() != null) { //参数根据实际情况调整
			pmap.clear();
			pmap.put("seq", param.getSeq()); //参数根据实际情况调整
			model.addAttribute("infototalordertrade", ftlService.getInfototalordertrade(pmap));
		}

		return "web/infototalordertradeedit";
	}

	@RequestMapping("/infototalordertradesave.htm")
	@ResponseBody
	public Response infototalordertradesave(Model model, RequestParameter param, @ModelAttribute Infototalordertrade infototalordertrade) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", infototalordertrade.getBrandname());  //根据实际情况调整
		pmap.put("seq", infototalordertrade.getSeq());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		infototalordertrade.setCreatedate(System.currentTimeMillis());
		infototalordertrade.setUpdatedate(System.currentTimeMillis());
		if (param.getSeq() != null) { //参数根据实际情况调整
			ftlService.updateInfototalordertrade(infototalordertrade);
		} else {
			ftlService.saveInfototalordertrade(infototalordertrade);
		}

		return res;
	}

	@RequestMapping("/infototalordertradedelete.htm")
	@ResponseBody
	public Response infototalordertradedelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getSeq() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

		//
		pmap.clear();
		pmap.put("seq", param.getSeq());
		ftlService.delInfototalordertrade(pmap);

		return res;
	}

}
