package com.thelionking.datafetch.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class CharacterEncodingFilter implements Filter {
	private FilterConfig config;
	private String encoding;
	private boolean ignore;
	
	@Override
	public void destroy() {
		this.config = null;
		this.encoding = null;
		CommonUtil.print("CharacterEncodingFilter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(!ignore && request.getCharacterEncoding() != null){
			request.setCharacterEncoding(this.encoding);
			response.setCharacterEncoding("encoding");
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		this.encoding = config.getInitParameter("encoding");
		String value = config.getInitParameter("ignore");
		if(value.equalsIgnoreCase("true")){
			this.ignore = true;
		}else{
			this.ignore = false;
		}
	}
	
}
