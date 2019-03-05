package com.bit.codesquare.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.member.MemberMapper;

public class GoogleFilter extends OAuth2ClientAuthenticationProcessingFilter {
	private LoginUserDetailsService service;

	public GoogleFilter(LoginUserDetailsService service) {
		super("/login/google");
		this.service = service;
	}
	//로그인 성공했을때
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		final OAuth2AccessToken accessToken = restTemplate.getAccessToken(); // 토큰 정보 가져옴
		final OAuth2Authentication auth = (OAuth2Authentication) authResult;
		final Map<String, String> map = (HashMap<String, String>) auth.getUserAuthentication().getDetails(); // 소셜에서 넘겨
																												// 받은
		int ranNum = (int) (Math.random() * 999) + 1;

//		String userId = map.get("email");
		Member member = new Member();
		
		member.setUserId(map.get("email"));
		member.setNickName(map.get("name")+ranNum);
		member.setEmail(map.get("email"));
		member.setName(map.get("name"));
		member.setPassword("socialMember");
									// 저장


		final UsernamePasswordAuthenticationToken authenticationToken = service.doAuthentication(member); // SocialService를
																											// 절차 진행

		super.successfulAuthentication(request, response, chain, authenticationToken);

	}

	@Autowired
	MemberMapper mm;

	public void getSession(Authentication auth, HttpSession session) {

		if (auth != null && session.getAttribute("userId") == null) {
			SecurityMember sc = (SecurityMember) auth.getPrincipal();
			Member member = mm.getUser(sc.getUsername());
			session.setAttribute("userId", member.getUserId());
			session.setAttribute("nickName", member.getNickName());
			session.setAttribute("authorId", member.getAuthorId());
		}
	}
}
