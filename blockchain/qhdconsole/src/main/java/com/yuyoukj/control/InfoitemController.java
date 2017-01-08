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
import com.yuyoukj.service.InfoitemService;
import com.yuyoukj.model.qhd.Infoitem;

@RequestMapping("/infoitem")
@Controller
public class InfoitemController extends AbstractFrontCommandController {

	@Autowired
	private ConfconfigService confconfigService;
	@Autowired
	private InfoitemService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/infoitemlist.htm")
	public String infoitemlist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		if (!StringUtils.isBlank(param.getUsername())) { //参数根据实际情况调整
			pmap.put("username", param.getUsername());
		}
		Page<Infoitem> page = new Page<Infoitem>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getInfoitemList(page, pmap));
		model.addAttribute("param", param);

		return "web/infoitemlist";
	}

	@RequestMapping("/infoitemedit.htm")
	public String infoitemedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		model.addAttribute("param", param);

		if (param.getItemid() != null) { //参数根据实际情况调整
			pmap.clear();
			pmap.put("itemid", param.getItemid()); //参数根据实际情况调整
			model.addAttribute("infoitem", ftlService.getInfoitem(pmap));
		}

		pmap.clear();
		pmap.put("cfield", "stype");
		pmap.put("ctbname", "info_user");
		model.addAttribute("stypelist", confconfigService.getConfconfigList(pmap));

		pmap.clear();
		pmap.put("cfield", "sflag");
		pmap.put("ctbname", "info_user");
		model.addAttribute("sflaglist", confconfigService.getConfconfigList(pmap));

		pmap.clear();
		pmap.put("cfield", "sstatus");
		pmap.put("ctbname", "info_user");
		model.addAttribute("sstatuslist", confconfigService.getConfconfigList(pmap));

		return "web/infoitemedit";
	}

	@RequestMapping("/infoitemsave.htm")
	@ResponseBody
	public Response infoitemsave(Model model, RequestParameter param, @ModelAttribute Infoitem infoitem) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", infoitem.getBrandname());  //根据实际情况调整
		pmap.put("itemid", infoitem.getItemid());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		infoitem.setCreatedate(Long.toString(System.currentTimeMillis()));
		infoitem.setUpdatedate(System.currentTimeMillis());
		if (param.getItemid() != null) { //参数根据实际情况调整
			ftlService.updateInfoitem(infoitem);
		} else {
			ftlService.saveInfoitem(infoitem);
		}

		return res;
	}

	@RequestMapping("/infoitemdelete.htm")
	@ResponseBody
	public Response infoitemdelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getItemid() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

		//
		pmap.clear();
		pmap.put("itemid", param.getItemid());
		ftlService.delInfoitem(pmap);

		return res;
	}

}
