package com.bit.codesquare.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();

	public CustomLoginSuccessHandler(String defaultTargetUrl) {
		setDefaultTargetUrl(defaultTargetUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest == null) {
			HttpSession session = request.getSession();
			if (session != null) {
				String redirectUrl = (String) session.getAttribute("prevPage");

				if (redirectUrl != null) {
					session.removeAttribute("prevPage");
					getRedirectStrategy().sendRedirect(request, response, redirectUrl);

				} else {
					super.onAuthenticationSuccess(request, response, authentication);
				}
			} else {
				super.onAuthenticationSuccess(request, response, authentication);
			}
			return;
		}
	}
}
