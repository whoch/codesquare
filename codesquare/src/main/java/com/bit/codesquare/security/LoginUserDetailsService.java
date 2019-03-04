package com.bit.codesquare.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.member.MemberMapper;

@Service
public class LoginUserDetailsService implements UserDetailsService {
	@Autowired
	MemberMapper mm;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Member member = mm.checkUser(userId);
		System.out.println(userId+"durl22");
		System.out.println(member.getUserId());
		System.out.println(member.getAuthorId()+"auth");
		if (member != null) {
			return new SecurityMember(member);
		} else {
			return null;
		}
	}
	
	   public UsernamePasswordAuthenticationToken doAuthentication(Member member) {
		      if (loadUserByUsername(member.getUserId())!=null) {
		           // 기존 회원일 경우에는 데이터베이스에서 조회해서 인증 처리
		           final SecurityMember sm = new SecurityMember(member);
		           System.out.println(sm.getAuthorId()+"아이디");
		           System.out.println(sm.getUsername());
		           return setAuthenticationToken(sm);
		       } else {
		           // 새 회원일 경우 회원가입 이후 인증 처리
		    	   
		    	   
		    	   mm.signUp(member);
		          
		           final SecurityMember user = new SecurityMember(member);
		           
		           
		         
		           return setAuthenticationToken(user);
		       }
		   }
		   
		private UsernamePasswordAuthenticationToken setAuthenticationToken(SecurityMember user) {
		    	System.out.println(user.getAuthorId()+"가져");
		    	return new UsernamePasswordAuthenticationToken(user,
		                 null, getAuthorities("ROLE_"+user.getAuthorId()));
		       }
		    
		private Collection<? extends GrantedAuthority> getAuthorities(String authorId) {
		           List<GrantedAuthority> authorities = new ArrayList<>();
		           authorities.add(new SimpleGrantedAuthority(authorId));
		           return authorities;
		       }
}
