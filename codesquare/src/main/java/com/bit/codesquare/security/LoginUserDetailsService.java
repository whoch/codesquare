package com.bit.codesquare.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.member.MemberMapper;

@Service
public class LoginUserDetailsService implements UserDetailsService {
	@Autowired
	MemberMapper mm;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Member member=mm.checkUser(userId);
		System.out.println(userId + "durl22");
//		System.out.println(member.getUserId());
//		System.out.println(member.getAuthorId() + "auth");
		if (member != null) {
			return new SecurityMember(member);
		} else {
			return null;
		}
	}

	public UsernamePasswordAuthenticationToken doAuthentication(Member member) {
		if (loadUserByUsername(member.getUserId()) != null) {
			// 기존 회원일 경우에는 데이터베이스에서 조회해서 인증 처리
			final SecurityMember sm = new SecurityMember(member);
			System.out.println(sm.getAuthorId() + "아이디");
			System.out.println(sm.getUsername());
			return new UsernamePasswordAuthenticationToken(sm, null, getAuthorities("ROLE_" + member.getAuthorId()));
		} else {
			// 새 회원일 경우 회원가입 이후 인증 처리
			//final OAuth2AccessToken accessToken = restTemplate.getAccessToken(); // 토큰 정보 가져옴
			//final OAuth2Authentication auth = (OAuth2Authentication) authResult;
			
			
//			int ranNum = (int) (Math.random() * 999) + 1;
//			member.setUserId(map.get("email"));
//			member.setNickName(map.get("name") + ranNum);
//			member.setEmail(map.get("email"));
//			member.setName(map.get("name"));
//			member.setPassword("socialMember");
			mm.signUp(member);

			final SecurityMember user = new SecurityMember(member);

			return new UsernamePasswordAuthenticationToken(user, null, getAuthorities("ROLE_" + user.getAuthorId()));
		}
	}
//
//	private UsernamePasswordAuthenticationToken setAuthenticationToken(SecurityMember user) {
//		System.out.println(user.getAuthorId() + "가져");
//		return new UsernamePasswordAuthenticationToken(user, null, getAuthorities("ROLE_" + user.getAuthorId()));
//	}

	private Collection<? extends GrantedAuthority> getAuthorities(String authorId) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(authorId));
		return authorities;
	}
}
