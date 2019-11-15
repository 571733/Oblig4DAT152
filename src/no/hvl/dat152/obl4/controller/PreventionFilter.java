package no.hvl.dat152.obl4.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class PreventionFilter implements Filter{
	  private String mode = "SAMEORIGIN";
	  private String mode1 = "NOSNIFF";
	   
	// Add X-FRAME-OPTIONS response header to tell any other browsers who   not to display this //content in a frame.
	     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	         HttpServletResponse res = (HttpServletResponse)response;
	         res.addHeader("X-FRAME-OPTIONS", mode );
	         res.addHeader("X-Content-Type-Options", mode1);
	         chain.doFilter(request, response);
	     }
	     public void destroy() {
	     }
	      
	     public void init(FilterConfig filterConfig) {
	    	 String configMode = filterConfig.getInitParameter("mode");
	    	 String configMode1 = filterConfig.getInitParameter("mode1");
	         
	    	 if ( configMode != null ) {
	             mode = configMode;
	             mode1 = configMode1;
	         }
	     }
	}