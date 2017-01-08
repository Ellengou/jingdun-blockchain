package com.yuyoukj.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yuyoukj.filter.AbstractFrontCommandController;
import com.yuyoukj.model.qhd.Ckv;
import com.yuyoukj.service.CkvService;
import com.yuyoukj.util.RequestParameter;

@RequestMapping("/ckv")
@Controller
public class CkvController extends AbstractFrontCommandController {
	@Autowired
	private CkvService ckvService;

	@RequestMapping("/changearea")
	@ResponseBody
	public Map<String, Object> changearea(Model model, RequestParameter param) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (param.getType() != null) {

			Map<String, Object> pmap = new HashMap<String, Object>();

			pmap.clear();
			pmap.put("type", param.getType());
			if (param.getType() == 2)
				pmap.put("fatherid", param.getProvinceid());
			else if (param.getType() == 3)
				pmap.put("fatherid", param.getCityid());

			List<Ckv> addresslist = ckvService.getAddressList(pmap);
			map.put("addresslist", addresslist);
			map.put("type", param.getType());
		}
		return map;
	}
}
