package com.yuyoukj.sys.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.yuyoukj.ao.model.CSessionUser;
import com.yuyoukj.ao.model.Page;
import com.yuyoukj.ao.model.Response;
import com.yuyoukj.filter.AbstractFrontCommandController;
import com.yuyoukj.service.CommonsService;
import com.yuyoukj.sys.model.Function;
import com.yuyoukj.sys.model.FunctionSimple;
import com.yuyoukj.sys.model.InfoCUser;
import com.yuyoukj.sys.model.InterRoleUser;
import com.yuyoukj.sys.model.Menu;
import com.yuyoukj.sys.model.Role;
import com.yuyoukj.sys.service.FunctionService;
import com.yuyoukj.sys.service.InfoCUserService;
import com.yuyoukj.sys.service.MenuService;
import com.yuyoukj.sys.service.RoleService;
import com.yuyoukj.util.AoConstants;
import com.yuyoukj.util.RequestParameter;
import com.yuyoukj.util.YyCommonUtil;
import com.yuyoukj.util.YyDateUtil;

@RequestMapping("/auth")
@Controller
public class AuthController extends AbstractFrontCommandController {

	@Autowired
	private InfoCUserService infoCUserService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private FunctionService functionService;
	@Autowired
	private CommonsService commonsService;
	@Autowired
	private MenuService menuService;

	@RequestMapping("/index.htm")
	public String index(HttpSession session) {
		return "auth/login";
	}

	@RequestMapping("/home.htm")
	public String home(Model model, RequestParameter param) {
		return "auth/home";
	}

	@RequestMapping("/login.htm")
	@ResponseBody
	public Response login(HttpSession session, String username, String password) {

		Response res = new Response();
		CSessionUser sUser = (CSessionUser) session.getAttribute(AoConstants.SESSION_USER_KEY);
		if (null == sUser || YyCommonUtil.isBlank(sUser.getLoginName())) {
			if (YyCommonUtil.isBlank(username) || YyCommonUtil.isBlank(password)) {
				res.setErrorMessage("用户名或密码错误");
				return res;
			}
			InfoCUser u = infoCUserService.getUser(username);
			if (null == u || null == u.getUserid() || null == u.getPassword() || !password.equals(u.getPassword())) {
				res.setErrorMessage("用户名或密码错误");
				return res;
			}
			if (u.getUserstate() == AoConstants.STATE_DOWN) {
				res.setErrorMessage("该帐号还没启用\n请与管理员联系！");
				return res;
			}

			//
			if (u.getNickname() == null || u.getNickname().length() == 0) {
				u.setNickname(u.getUsername());
			}

			sUser = new CSessionUser(u.getUserid(), u.getUsername(), u.getNickname(), u.getUserstate());
			session.setAttribute(AoConstants.SESSION_USER_KEY, sUser);

			// 登录成功后加载缓存信息
		}
		return res;
	}

	/**
	 * 用户退出登入
	 * 
	 * @return
	 */
	@RequestMapping("/logout.htm")
	public String logout(HttpSession session) {
		session.removeAttribute(AoConstants.SESSION_USER_KEY);
		return "auth/login";
	}

	@RequestMapping("/changepwd.htm")
	public String changepwd(HttpSession session) {
		return "auth/changepwd";
	}

	@RequestMapping("/changepwd_save.htm")
	@ResponseBody
	public Response changepwd_save(HttpSession session, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		CSessionUser sUser = (CSessionUser) session.getAttribute(AoConstants.SESSION_USER_KEY);

		if (null == sUser) {
			res.setErrorMessage("请先登录");
			return res;
		}

		pmap.clear();
		pmap.put("userid", sUser.getId());
		InfoCUser u = infoCUserService.getUser(pmap);
		if (u == null || u.getPassword() == null || !u.getPassword().equals(param.getOldpasswd())) {
			res.setErrorMessage("原密码不正确");
			return res;
		}

		pmap.clear();
		pmap.put("userid", sUser.getId());
		pmap.put("password", param.getPassword());

		infoCUserService.changepwd(pmap);

		return res;
	}

	@RequestMapping("/functionform.htm")
	public String functionform(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		if (param.getId() != null) {
			model.addAttribute("function", functionService.getFunctionById(param.getId()));
		}

		pmap.clear();

		pmap.put("cfield", "FFLAG");
		model.addAttribute("sflaglist", commonsService.getCKVList(pmap));

		pmap.put("cfield", "ISSHOW");
		model.addAttribute("isshowlist", commonsService.getCKVList(pmap));

		return "auth/functionedit";
	}

	@RequestMapping("/functionlist.htm")
	public String function(HttpSession session, Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		List<FunctionSimple> listFunSimp = functionService.getFunctionSimpleList(pmap);
		if (listFunSimp != null && listFunSimp.size() > 0) {
			model.addAttribute("initnodes", JSON.toJSON(listFunSimp).toString().replace("\"", "\'"));
		}

		return "auth/functionlist";
	}

	@RequestMapping("/addztree")
	@ResponseBody
	public Response addztree(HttpSession session, Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();

		CSessionUser sUser = (CSessionUser) session.getAttribute(AoConstants.SESSION_USER_KEY);
		if (null == sUser) {
			res.setErrorMessage("请先登录");
			return res;
		}

		Function function = new Function();
		Function fun = new Function();

		fun = functionService.getFunctionById(param.getPid());

		function.setSlevel(fun.getSlevel() + 1);
		function.setPids(fun.getPids() + Long.toString(param.getId()) + ",");
		function.setCreateby(sUser.getId());
		function.setCreatedate(Long.toString(System.currentTimeMillis()));
		function.setPid(param.getPid());
		function.setSname(param.getSname());
		function.setId(param.getId());

		functionService.addZtree(function);

		InterRoleUser roleuser = infoCUserService.getRoleUser(sUser.getId());

		pmap.clear();
		pmap.put("rid", roleuser.getRid());
		pmap.put("fid", function.getFid());
		roleService.saveFunRole(pmap);

		return res;
	}

	@RequestMapping("/renameztree")
	@ResponseBody
	public Response renameztree(HttpSession session, Model model, RequestParameter param) {
		Response res = new Response();

		Function function = new Function();

		CSessionUser sUser = (CSessionUser) session.getAttribute(AoConstants.SESSION_USER_KEY);
		if (null == sUser) {
			res.setErrorMessage("请先登录");
			return res;
		}

		function.setCreateby(sUser.getId());
		function.setId(param.getId());
		function.setSname(param.getSname());

		functionService.renameZtree(function);

		return res;
	}

	@RequestMapping("/removeztree")
	@ResponseBody
	public Response removeztree(HttpSession session, Model model, RequestParameter param) {
		Response res = new Response();

		Function function = functionService.getFunctionById(param.getId());
		functionService.delFunction(function.getFid(), function.getSlevel());
		functionService.delInterFunRole(function.getFid(), function.getSlevel());

		functionService.removeZtree(Long.toString(param.getId()));

		return res;
	}

	@RequestMapping("/functionsave.htm")
	@ResponseBody
	public Response functionsave(HttpSession session, Model model, RequestParameter param, @ModelAttribute Function function) {
		Response res = new Response();

		function.setCreatedate(Long.toString(YyDateUtil.dataString2long(function.getCreatedate(), "YYYY-MM-DD")));
		functionService.saveFunction(function);

		return res;
	}

	@RequestMapping("/rolelist.htm")
	public String projectlist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		if (!StringUtils.isBlank(param.getSname())) {
			pmap.put("sname", param.getSname());
		}
		model.addAttribute("param", param);

		Page<Role> page = new Page<Role>();
		page.setPageNo(param.getPageNo());
		model.addAttribute("page", roleService.getRoleList(page, pmap));
		model.addAttribute("param", param);

		return "auth/rolelist";
	}

	@RequestMapping("/roleform.htm")
	public String roleform(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		if (param.getRid() != null) {
			model.addAttribute("role", roleService.getRole(param.getRid()));
		}

		pmap.clear();
		List<FunctionSimple> listFunSimp = functionService.getFunctionSimpleList(pmap);
		if (listFunSimp != null && listFunSimp.size() > 0) {
			model.addAttribute("initnodes", JSON.toJSON(listFunSimp).toString().replace("\"", "\'"));
		}

		return "auth/roleedit";
	}

	@RequestMapping("/rolesave.htm")
	@ResponseBody
	public Response rolesave(HttpSession session, Model model, RequestParameter param, @ModelAttribute Role role) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		pmap.clear();
		//

		pmap.put("rid", role.getRid());
		pmap.put("sname", role.getSname());
		if (roleService.checkSname(pmap) != 0) {
			res.setErrorMessage("角色名重复");
			return res;
		}
		//		
		role.setCreatedate(Long.toString(System.currentTimeMillis()));

		CSessionUser sUser = (CSessionUser) session.getAttribute(AoConstants.SESSION_USER_KEY);
		if (null == sUser) {
			res.setErrorMessage("请先登录");
			return res;
		}
		role.setCreateby(sUser.getId());

		roleService.saveRole(role);

		if (role.getRid() != null && role.getRid() > 0) {
			roleService.delCheckedNodes(role.getRid());

			if (param.getChknodes() != null && param.getChknodes().length() > 0) {
				String s[] = param.getChknodes().split(",");
				if (s != null && s.length > 0) {
					try {
						for (String str : s) {
							Long id = Long.valueOf(str);
							pmap.put("id", id);
							pmap.put("rid", role.getRid());
							roleService.addCheckedNodes(pmap);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		return res;
	}

	@RequestMapping("/addCheckedNodes")
	@ResponseBody
	public Response addCheckedNodes(HttpSession session, Model model, RequestParameter param, List<Long> nodesid) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		Response res = new Response();
		pmap.clear();

		CSessionUser sUser = (CSessionUser) session.getAttribute(AoConstants.SESSION_USER_KEY);
		if (null == sUser) {
			res.setErrorMessage("请先登录");
			return res;
		}

		pmap.put("rid", param.getRid());
		pmap.put("nodesid", nodesid);
		roleService.addCheckedNodes(pmap);

		return res;
	}

	@RequestMapping("/roledelete.htm")
	@ResponseBody
	public Response roledelete(Model model, RequestParameter param) {
		Response res = new Response();

		if (param.getRid() == null) {
			res.setErrorMessage("请选择记录");
			return res;
		}

		roleService.delRole(param.getRid());
		roleService.delCheckedNodes(param.getRid());
		roleService.delInterRoleUser(param.getRid());

		return res;
	}

	@RequestMapping("/infocuserlist.htm")
	public String infocuserlist(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		pmap.clear();
		if (!StringUtils.isBlank(param.getUsername())) {
			pmap.put("username", param.getUsername());
		}
		model.addAttribute("param", param);

		pmap.put("cfield", "USERSTATE");
		model.addAttribute("userstatelist", commonsService.getCKVList(pmap));

		Page<InfoCUser> page = new Page<InfoCUser>();
		page.setPageNo(param.getPageNo());
		model.addAttribute("page", infoCUserService.getInfoCUserList(page, pmap));
		model.addAttribute("param", param);

		return "auth/infocuserlist";
	}

	@RequestMapping("/infocuserform.htm")
	public String infocuserform(Model model, RequestParameter param) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		if (param.getUserid() != null) {
			model.addAttribute("infocuser", infoCUserService.getInfoCUser(param.getUserid()));
		}

		pmap.clear();
		pmap.put("cfield", "USERSTATE");
		model.addAttribute("userstatelist", commonsService.getCKVList(pmap));

		model.addAttribute("rolelist", roleService.getRoleList(pmap));

		return "auth/infocuseredit";
	}

	@RequestMapping("/infocusersave.htm")
	@ResponseBody
	public Response infocusersave(Model model, RequestParameter param, @ModelAttribute InfoCUser infoCUser) {
		Response res = new Response();
		infoCUser.setPassword(param.getPasswd());

		if (infoCUser.getUserid() == null && infoCUser.getRid() == null) { //新增时
			infoCUser.setRid(37L); //默认超级管理员角色
		}
		infoCUserService.saveInfoCUser(infoCUser);

		if (infoCUser.getUserid() != null && infoCUser.getUserid() > 0 && infoCUser.getRid() != null) {
			infoCUserService.saveInterRoleUser(infoCUser);
		}

		return res;
	}

	@RequestMapping("/infocuserdelete.htm")
	@ResponseBody
	public Response infocuserdelete(Model model, RequestParameter param) {
		Response res = new Response();

		if (param.getUserid() == null) {
			res.setErrorMessage("请选择记录");
			return res;
		}

		infoCUserService.delInfoCUser(param.getUserid());

		return res;
	}

	@RequestMapping("/menu")
	public String menu(HttpSession session, Model model, RequestParameter reqParam) {
		Map<String, Object> pmap = new HashMap<String, Object>();

		CSessionUser sUser = (CSessionUser) session.getAttribute(AoConstants.SESSION_USER_KEY);

		String username = sUser.getLoginName();

		pmap.clear();
		pmap.put("username", username);
		pmap.put("slevel", 1);
		pmap.put("isshow", 1);
		pmap.put("sflag", 0);

		List<Menu> menuList = menuService.findAllList(pmap);
		for (int i = 0; i < menuList.size(); i++) {

			Menu menu = menuList.get(i);
			pmap.put("id", menu.getId());
			pmap.put("slevel", 2);
			menuList.get(i).setMenulist(menuService.findAllList(pmap));
		}

		model.addAttribute("menuList", menuList);

		return "/auth/menu";
	}
}
