package com.java.mh.frame.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

/**
 * 改写原生的DispatcherServlet
 * 1.添加response便捷获取
 * 2.
 * @author 辛晟昊
 *
 */
public class MHDispatcherServlet extends DispatcherServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.DispatcherServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("response", response);
		super.doService(request, response);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.DispatcherServlet#render(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//super.render(mv, request, response);
	}
	
	
	
	

}
