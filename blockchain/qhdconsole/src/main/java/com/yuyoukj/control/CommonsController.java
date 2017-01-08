package com.yuyoukj.control;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.yuyoukj.ao.model.Response;
import com.yuyoukj.filter.AbstractFrontCommandController;
import com.yuyoukj.service.CommonsService;
import com.yuyoukj.util.AoConstants;
import com.yuyoukj.util.RequestParameter;
import com.yuyoukj.util.YyCommonUtil;
import com.yuyoukj.util.YyDateUtil;
import com.yuyoukj.util.constants.WebConstants;

@RequestMapping("/commons")
@Controller
public class CommonsController extends AbstractFrontCommandController {
	@Autowired
	private CommonsService commonsService;

	@RequestMapping("/saveposition.htm")
	@ResponseBody
	public Response saveposition(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		//		try {
		//			if (param.getMenuid() != null && param.getSlevel() != null && param.getAppid() != null && param.getSORT_METHOD() != null) {
		//				pmap.clear();
		//				pmap.put("menuid", param.getMenuid());
		//				pmap.put("slevel", param.getSlevel());
		//				pmap.put("appid", param.getAppid());
		//				pmap.put("SORT_METHOD", param.getSORT_METHOD());
		//				pmap.put("posflag", "pos_wxmenu"); // 广告图片位置调整；
		//				commonsService.saveposition(pmap);
		//			} else {
		//				res.setErrorMessage("排序参数有误");
		//				return res;
		//			}
		//		} catch (Exception e) {
		//			res.setErrorMessage(e.getMessage());
		//			return res;
		//		}

		return res;
	}

	@RequestMapping("/saveadsposition.htm")
	@ResponseBody
	public Response saveadsposition(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		//		try {
		//			if (param.getAdsid() != null && param.getImgid() != null && param.getLocalid() != null && param.getSORT_METHOD() != null) {
		//				pmap.clear();
		//				pmap.put("adsid", param.getAdsid());
		//				pmap.put("localid", param.getLocalid());
		//				pmap.put("imgid", param.getImgid());
		//				pmap.put("SORT_METHOD", param.getSORT_METHOD());
		//				pmap.put("posflag", "pos_adsimg"); //广告图片位置调整；
		//				commonsService.saveadsposition(pmap);
		//			} else {
		//				res.setErrorMessage("排序参数有误");
		//				return res;
		//			}
		//		} catch (Exception e) {
		//			res.setErrorMessage(e.getMessage());
		//			return res;
		//		}

		return res;
	}

	// @RequestMapping("/onchangect")
	// @ResponseBody
	// public Map<String, Object> onchangect(Model model, RequestParameter param) {
	//
	// Map<String, Object> map = new HashMap<String, Object>();
	// if (param.getSlevel() != null && param.getSlevel() > 1) {
	//
	// map.put("slevel", param.getSlevel());
	//
	// Map<String, Object> pmap = new HashMap<String, Object>();
	//
	// pmap.clear();
	// pmap.put("slevel", param.getSlevel());
	// if (param.getSlevel().intValue() == AoConstants.CATEGORY_SLEVEL_2) {
	// pmap.put("fatherid", param.getFatherid1());
	// } else if (param.getSlevel().intValue() == AoConstants.CATEGORY_SLEVEL_3) {
	// pmap.put("fatherid", param.getFatherid2());
	// }
	// List<InfoCategory> infocategorylist = infoCategoryService.queryCategoryList(pmap);
	// map.put("ctlist", infocategorylist);
	// }
	// return map;
	// }
	//
	// @RequestMapping("/onchangepropertylevel")
	// @ResponseBody
	// public Map<String, Object> onchangepropertylevel(Model model, RequestParameter param) {
	//
	// Map<String, Object> map = new HashMap<String, Object>();
	// if (param.getSlevel() != null && param.getSlevel() > 0) {
	//
	// map.put("slevel", param.getSlevel());
	//
	// Map<String, Object> pmap = new HashMap<String, Object>();
	//
	// pmap.clear();
	// pmap.put("slevel", (param.getSlevel() - 1));
	// if (param.getFatherid() != null) {
	// if (param.getFatherid() == 0)
	// param.setFatherid(null);
	// pmap.put("fatherid", param.getFatherid());
	// }
	// List<InfoProperty> infoPropertylist = infoPropertyService.queryPropertyList(pmap);
	// map.put("propertylist", infoPropertylist);
	// }
	// return map;
	// }

	@RequestMapping(value = "/uploadimg0")
	@ResponseBody
	public Map<String, Object> uploadimg0(@RequestParam(value = "uploadfile0") MultipartFile uploadfile, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();

		map = saveImg(uploadfile);
		return map;
	}

	@RequestMapping(value = "/uploadimg1")
	@ResponseBody
	public Map<String, Object> uploadimg1(@RequestParam(value = "uploadfile1") MultipartFile uploadfile, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();

		map = saveImg(uploadfile);
		return map;
	}

	@RequestMapping(value = "/uploadimg2")
	@ResponseBody
	public Map<String, Object> uploadimg2(@RequestParam(value = "uploadfile2") MultipartFile uploadfile, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();

		map = saveImg(uploadfile);
		return map;
	}

	private Map<String, Object> saveImg(MultipartFile uploadfile) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (uploadfile != null && !uploadfile.isEmpty()) {
			String suploadPath = WebConstants.DEFAULT_IMG_PATH + AoConstants.JOIN_URL + YyDateUtil.getCurrentDate("yyyyMM") + AoConstants.JOIN_URL
					+ YyDateUtil.getCurrentDate("dd") + AoConstants.JOIN_URL + YyDateUtil.getCurrentDate("HH") + AoConstants.JOIN_URL;
			String realPath = WebConstants.DEFAULT_FILE_DIRECTORY + suploadPath;

			String originFileName = uploadfile.getOriginalFilename(); // 文件原名称
			originFileName = YyCommonUtil.changeFileName(originFileName);

			try {
				YyCommonUtil.uploadFile(uploadfile, realPath, originFileName);
				map.put("ret", "ok");
				map.put("url", (WebConstants.DEFAULT_URL_HTTP + suploadPath + originFileName));
			} catch (Exception ex) {
				map.put("ret", "error");
				ex.printStackTrace();
			}
		} else {
			map.put("ret", "empty"); // 未选择文件
		}
		return map;
	}

	@RequestMapping(value = "/uploadimgs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadimgs(@RequestParam(value = "uploadfile") MultipartFile[] uploadfile, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (uploadfile == null || uploadfile.length == 0) {
			map.put("ret", "empty"); // 未选择文件
			return map;
		}

		for (MultipartFile uploadfile1 : uploadfile) {
			if (uploadfile1 != null && !uploadfile1.isEmpty()) {
				String suploadPath = WebConstants.DEFAULT_IMG_PATH + AoConstants.JOIN_URL + YyDateUtil.getCurrentDate("yyyyMM")
						+ AoConstants.JOIN_URL + YyDateUtil.getCurrentDate("dd") + AoConstants.JOIN_URL + YyDateUtil.getCurrentDate("HH")
						+ AoConstants.JOIN_URL;
				String realPath = WebConstants.DEFAULT_FILE_DIRECTORY + suploadPath;

				String originFileName = uploadfile1.getOriginalFilename(); // 文件原名称
				originFileName = YyCommonUtil.changeFileName(originFileName);

				try {
					YyCommonUtil.uploadFile(uploadfile1, realPath, originFileName);
					map.put("ret", "ok");
					map.put("url", (WebConstants.DEFAULT_URL_HTTP + suploadPath + originFileName));
				} catch (Exception ex) {
					map.put("ret", "error");
					ex.printStackTrace();
				}
			} else {
				map.put("ret", "empty"); // 未选择文件
			}
		}
		return map;
	}

	@RequestMapping(value = "/uploadimg")
	@ResponseBody
	public Map<String, Object> uploadimg(@RequestParam(value = "uploadfile") MultipartFile uploadfile, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (uploadfile != null && !uploadfile.isEmpty()) {
			String suploadPath = WebConstants.DEFAULT_IMG_PATH + AoConstants.JOIN_URL + YyDateUtil.getCurrentDate("yyyyMM") + AoConstants.JOIN_URL
					+ YyDateUtil.getCurrentDate("dd") + AoConstants.JOIN_URL + YyDateUtil.getCurrentDate("HH") + AoConstants.JOIN_URL;
			// String realPath = request.getSession().getServletContext().getRealPath(uploadPath) + AoConstants.JOIN_URL;
			String realPath = WebConstants.DEFAULT_FILE_DIRECTORY + suploadPath;

			String originFileName = uploadfile.getOriginalFilename(); // 文件原名称
			originFileName = YyCommonUtil.changeFileName(originFileName);

			try {
				// 这里使用Apache的FileUtils方法来进行保存
				// FileUtils.copyInputStreamToFile(uploadfile.getInputStream(), new File(realPath, originFileName));

				YyCommonUtil.uploadFile(uploadfile, realPath, originFileName);
				map.put("ret", "ok");

				// System.out.println(ContextLoader.getCurrentWebApplicationContext());
				// System.out.println(ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath());
				// System.out.println(basePath);

				map.put("url", (WebConstants.DEFAULT_URL_HTTP + suploadPath + originFileName));
			} catch (Exception ex) {
				map.put("ret", "error");
				ex.printStackTrace();
			}
		} else {
			map.put("ret", "empty"); // 未选择文件
		}
		return map;
	}

	@RequestMapping(value = "/uploadimgsIE", method = RequestMethod.POST)
	@ResponseBody
	public void uploadimgsIE(@RequestParam(value = "uploadfile") MultipartFile[] uploadfile, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject map = new JSONObject();

		//Map<String, Object> map = new HashMap<String, Object>();

		if (uploadfile == null || uploadfile.length == 0) {
			map.put("ret", "empty"); // 未选择文件

			response.setContentType("text/html");
			response.getWriter().write(map.toString());
		}

		for (MultipartFile uploadfile1 : uploadfile) {
			if (uploadfile1 != null && !uploadfile1.isEmpty()) {
				String suploadPath = WebConstants.DEFAULT_IMG_PATH + AoConstants.JOIN_URL + YyDateUtil.getCurrentDate("yyyyMM")
						+ AoConstants.JOIN_URL + YyDateUtil.getCurrentDate("dd") + AoConstants.JOIN_URL + YyDateUtil.getCurrentDate("HH")
						+ AoConstants.JOIN_URL;
				String realPath = WebConstants.DEFAULT_FILE_DIRECTORY + suploadPath;

				String originFileName = uploadfile1.getOriginalFilename(); // 文件原名称
				originFileName = YyCommonUtil.changeFileName(originFileName);

				try {
					YyCommonUtil.uploadFile(uploadfile1, realPath, originFileName);

					map.put("ret", "ok");
					map.put("url", (WebConstants.DEFAULT_URL_HTTP + suploadPath + originFileName));
				} catch (Exception ex) {
					map.put("ret", "error");
					ex.printStackTrace();
				}
			} else {
				map.put("ret", "empty"); // 未选择文件
			}
		}
		response.setContentType("text/html");
		response.getWriter().write(map.toString());
	}

	// @RequestMapping("/onchangelabel")
	// @ResponseBody
	// public Map<String, Object> onchangelabel(Model model, RequestParameter param) {
	//
	// Map<String, Object> map = new HashMap<String, Object>();
	// // if (param.getCtid() != null) {
	//
	// Map<String, Object> pmap = new HashMap<String, Object>();
	//
	// pmap.clear();
	// // pmap.put("ctid", param.getCtid());
	// List<InfoLabel> infoLabellist = infoLabelService.queryLabelList(pmap);
	// map.put("labellist", infoLabellist);
	// // }
	// return map;
	// }
	//
	// @RequestMapping("/onchangeproperty")
	// @ResponseBody
	// public Map<String, Object> onchangeproperty(Model model, RequestParameter param) {
	//
	// Map<String, Object> map = new HashMap<String, Object>();
	// if (param.getCtid() != null) {
	//
	// Map<String, Object> pmap = new HashMap<String, Object>();
	//
	// pmap.clear();
	// pmap.put("ctid", param.getCtid());
	// List<InfoProperty> infoPropertylist = infoPropertyService.queryPropertyList(pmap);
	// map.put("propertylist", infoPropertylist);
	// }
	// return map;

}
