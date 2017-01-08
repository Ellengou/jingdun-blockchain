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

import com.yuyoukj.service.InfoimgService;
import com.yuyoukj.model.qhd.Infoimg;

@RequestMapping("/infoimg")
@Controller
public class InfoimgController extends AbstractFrontCommandController {

	@Autowired
	private InfoimgService ftlService;
	@Autowired
	private MyAsyncService myAsyncService;
	@Autowired
	private HttpSession httpSession;

	@RequestMapping("/infoimglist.htm")
	public String infoimglist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) {  //参数根据实际情况调整
			pmap.put("sname", param.getSname());
		}
		Page<Infoimg> page = new Page<Infoimg>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(WebConstants.PAGE_SIZE_8);
		model.addAttribute("page", ftlService.getInfoimgList(page, pmap));
		model.addAttribute("param", param);

		return "web/infoimglist";
	}

	@RequestMapping("/infoimgedit.htm")
	public String infoimgedit(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		
		pmap.clear();
		model.addAttribute("param", param);

		if (param.getImgid() != null) {  //参数根据实际情况调整
			pmap.clear();
			pmap.put("imgid", param.getImgid()); //参数根据实际情况调整
			model.addAttribute("infoimg", ftlService.getInfoimg(pmap));
		}

		return "web/infoimgedit";
	}

	@RequestMapping("/infoimgsave.htm")
	@ResponseBody
	public Response infoimgsave(Model model, RequestParameter param, @ModelAttribute Infoimg infoimg) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		//
		pmap.clear();
		//pmap.put("brandname", infoimg.getBrandname());  //根据实际情况调整
		pmap.put("imgid", infoimg.getImgid());
		if (ftlService.checkSname(pmap) != 0) {
			res.setErrorMessage("名称重复");
			return res;
		}

		//
		infoimg.setCreatedate(System.currentTimeMillis());
		infoimg.setUpdatedate(System.currentTimeMillis());
		if (param.getImgid() != null) { //参数根据实际情况调整
			ftlService.updateInfoimg(infoimg);
		} else {
			ftlService.saveInfoimg(infoimg);
		}

		return res;
	}

	@RequestMapping("/infoimgdelete.htm")
	@ResponseBody
	public Response infoimgdelete(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		if (param.getImgid() == null) { //参数根据实际情况调整
			res.setErrorMessage("请选择记录");
			return res;
		}

        //
		pmap.clear();
		pmap.put("imgid", param.getImgid());
		ftlService.delInfoimg(pmap);

		return res;
	}

}
