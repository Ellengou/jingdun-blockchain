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
import com.yuyoukj.service.InfotradeService;
import com.yuyoukj.service.InfouserService;
import com.yuyoukj.model.qhd.Infotrade;

@RequestMapping("/infotrade")
@Controller
public class InfotradeController extends AbstractFrontCommandController {

	@Autowired
	private InfotradeService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/infotradelist.htm")
	public String infotradelist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		if (!StringUtils.isBlank(param.getBuyusername())) { //参数根据实际情况调整
			pmap.put("buyusername", param.getBuyusername());
		}
		Page<Infotrade> page = new Page<Infotrade>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getInfotradeList(page, pmap));
		model.addAttribute("param", param);

		return "web/infotradelist";
	}

	@RequestMapping("/infotradeedit.htm")
	public String infotradeedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		model.addAttribute("param", param);

		if (param.getTradeid() != null) { //参数根据实际情况调整
			pmap.clear();
			pmap.put("tradeid", param.getTradeid()); //参数根据实际情况调整
			model.addAttribute("infotrade", ftlService.getInfotrade(pmap));
		}

		return "web/infotradeedit";
	}

	@RequestMapping("/infotradesave.htm")
	@ResponseBody
	public Response infotradesave(Model model, RequestParameter param, @ModelAttribute Infotrade infotrade) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", infotrade.getBrandname());  //根据实际情况调整
		pmap.put("tradeid", infotrade.getTradeid());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		infotrade.setCreatedate(System.currentTimeMillis());
		infotrade.setUpdatedate(System.currentTimeMillis());
		if (param.getTradeid() != null) { //参数根据实际情况调整
			ftlService.updateInfotrade(infotrade);
		} else {
			ftlService.saveInfotrade(infotrade);
		}

		return res;
	}

	@RequestMapping("/infotradedelete.htm")
	@ResponseBody
	public Response infotradedelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getTradeid() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

		//
		pmap.clear();
		pmap.put("tradeid", param.getTradeid());
		ftlService.delInfotrade(pmap);

		return res;
	}

}
