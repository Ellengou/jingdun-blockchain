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
import com.yuyoukj.util.weixin.http.util.StrUtils;
import com.yuyoukj.service.InfopayconfigService;
import com.yuyoukj.service.InfouserService;
import com.yuyoukj.model.qhd.Infouser;

@RequestMapping("/infouser")
@Controller
public class InfouserController extends AbstractFrontCommandController {

	@Autowired
	private InfouserService ftlService;
	@Autowired
	private InfopayconfigService infopayconfigService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/infouserlist.htm")
	public String infouserlist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		if (!StringUtils.isBlank(param.getUsername())) { //参数根据实际情况调整
			pmap.put("username", param.getUsername());
		}
		Page<Infouser> page = new Page<Infouser>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getInfouserList(page, pmap));
		model.addAttribute("param", param);

		return "web/infouserlist";
	}

	@RequestMapping("/infouseredit.htm")
	public String infouseredit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		model.addAttribute("param", param);

		if (param.getUserid() != null) { //参数根据实际情况调整
			pmap.clear();
			pmap.put("userid", param.getUserid()); //参数根据实际情况调整
			Infouser infouser = ftlService.getInfouser(pmap);
			model.addAttribute("infouser", infouser);

			if (!StrUtils.isBlank(infouser.getPayconfig())) {
				String payconfig[] = infouser.getPayconfig().split(",");
				for (String stype : payconfig) {
					if (stype.equals("0")) {
						pmap.clear();
						pmap.put("userid", infouser.getUserid());
						pmap.put("stype", stype);
						model.addAttribute("infopayconfig0", infopayconfigService.getInfopayconfig(pmap));
					}

					if (stype.equals("1")) {
						pmap.clear();
						pmap.put("userid", infouser.getUserid());
						pmap.put("stype", stype);
						model.addAttribute("infopayconfig1", infopayconfigService.getInfopayconfig(pmap));
					}
				}
			}
		}

		return "web/infouseredit";
	}

	@RequestMapping("/infousersave.htm")
	@ResponseBody
	public Response infousersave(Model model, RequestParameter param, @ModelAttribute Infouser infouser) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", infouser.getBrandname());  //根据实际情况调整
		pmap.put("userid", infouser.getUserid());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		infouser.setCreatedate(System.currentTimeMillis());
		infouser.setUpdatedate(System.currentTimeMillis());
		if (param.getUserid() != null) { //参数根据实际情况调整
			ftlService.updateInfouser(infouser);
		} else {
			ftlService.saveInfouser(infouser);
		}

		return res;
	}

	@RequestMapping("/infouserdelete.htm")
	@ResponseBody
	public Response infouserdelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getUserid() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

		//
		pmap.clear();
		pmap.put("userid", param.getUserid());
		ftlService.delInfouser(pmap);

		return res;
	}

}
