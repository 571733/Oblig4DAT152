package no.hvl.dat152.obl4.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import no.hvl.dat152.obl4.util.CRSFHandler;

public class RequestHelper {

	public static String getCookieValue(HttpServletRequest request,
			String cookieName) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(cookieName)) {
					return c.getValue();
				}
			}
		}
		return null;
	}
	
	public static boolean isLoggedIn(HttpServletRequest request) {
		return request.getSession().getAttribute("user") != null;
	
	}
	
	public static boolean CRSF(HttpServletRequest request) {
		boolean CRSF = request.getParameter("token") != null;
		
		if(CRSF) {
			return CRSFHandler.isCRSFTokenMatch(request);
		}else {
			return false;
		}
	}
}
