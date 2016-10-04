package com.tm.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.tools.ConnectionTools;

/**
 * Servlet Filter implementation class LoginFilter
 */
// @WebFilter("/*")
public class LoginFilter implements Filter {

	private static final String VUE_LOGIN = "/SignIn";
	private static final String[] PRIVATE_PAGES = { "/Profile" };

	@Override
	public void init(FilterConfig config) throws ServletException {
		// If you have any <init-param> in web.xml, then you could get them
		// here by config.getInitParameter("name") and assign it as field.
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String uri = request.getRequestURI();

		if (isPrivatePage(uri)) {
			if (ConnectionTools.getUserConnected(request) == null) {
				// user not found
				response.sendRedirect(request.getContextPath() + VUE_LOGIN);
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	private boolean isPrivatePage(String uri) {
		for (String s : PRIVATE_PAGES) {
			if (uri.endsWith(s)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {
		// If you have assigned any expensive resources as field of
		// this Filter class, then you could clean/close them here.
	}
}