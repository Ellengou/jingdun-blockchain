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

import com.yuyoukj.service.InfototalorderService;
import com.yuyoukj.model.qhd.Infototalorder;

@RequestMapping("/infototalorder")
@Controller
public class InfototalorderController extends AbstractFrontCommandController {

	@Autowired
	private InfototalorderService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/infototalorderlist.htm")
	public String infototalorderlist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) {  //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Infototalorder> page = new Page<Infototalorder>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getInfototalorderList(page, pmap));
		model.addAttribute("param", param);

		return "web/infototalorderlist";
	}

	@RequestMapping("/infototalorderedit.htm")
	public String infototalorderedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		model.addAttribute("param", param);

		if (param.getSeq() != null) {  //参数根据实际情况调整
			pmap.clear();
			pmap.put("seq", param.getSeq()); //参数根据实际情况调整
			model.addAttribute("infototalorder", ftlService.getInfototalorder(pmap));
		}

		return "web/infototalorderedit";
	}

	@RequestMapping("/infototalordersave.htm")
	@ResponseBody
	public Response infototalordersave(Model model, RequestParameter param, @ModelAttribute Infototalorder infototalorder) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", infototalorder.getBrandname());  //根据实际情况调整
		pmap.put("seq", infototalorder.getSeq());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		infototalorder.setCreatedate(System.currentTimeMillis());
		infototalorder.setUpdatedate(System.currentTimeMillis());
		if (param.getSeq() != null) { //参数根据实际情况调整
			ftlService.updateInfototalorder(infototalorder);
		} else {
			ftlService.saveInfototalorder(infototalorder);
		}

		return res;
	}

	@RequestMapping("/infototalorderdelete.htm")
	@ResponseBody
	public Response infototalorderdelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getSeq() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

        //
		pmap.clear();
		pmap.put("seq", param.getSeq());
		ftlService.delInfototalorder(pmap);

		return res;
	}

}
