package no.hvl.dat152.obl4.util;

import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;

public class CRSFHandler {
	
	public static String generateCRSFToken(HttpServletRequest request) {
		SecureRandom sr = new SecureRandom();
		byte[] csrf = new byte[16];
		sr.nextBytes(csrf);
		String token = Base64.encodeBase64URLSafeString(csrf);
		return token;
	}
	
	public static boolean isCRSFTokenMatch(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sessionToken = (String) session.getAttribute("token");
		String requestToken = request.getParameter("token");
		
		if(sessionToken.equals(requestToken)) {
			return true;
		}else {
			return false;
		}
	}
}

