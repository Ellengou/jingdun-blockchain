package com.yuyoukj.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class AbstractFrontCommandController extends AbstractCommandController {

	public AbstractFrontCommandController() {
		//只是为满足AbstractCommandController的要求
		this.setCommandClass(String.class);
	}

	//	@Override
	//	protected final void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	//		super.initBinder(request, binder);
	//		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false, false));
	//	}

	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		return null;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringEscapeEditor(false, false, true));
	}

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(String.class, new StringEscapeEditor(false, false, true));
	}
}
