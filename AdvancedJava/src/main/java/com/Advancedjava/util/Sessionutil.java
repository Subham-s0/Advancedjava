package com.Advancedjava.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Sessionutil {
	public static void setAttribute(HttpServletRequest request, String key, Object object) {
		HttpSession session = request.getSession();
		session.setAttribute(key, object);
	}

	public static Object getAttribute(HttpServletRequest request, String key) {
		HttpSession session = request.getSession(false);
		return (session != null) ? session.getAttribute(key) : null;
	}

	public static void removeAttribute(HttpServletRequest request, String key) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(key);
		}
	}

	public static void invalidateSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}
}
