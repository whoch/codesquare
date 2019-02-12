package com.bit.codesquare.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.bit.codesquare.dto.member.Member;

public class SecurityMember extends User implements UserDetails {

	
	private static final String ROLE_PREFIX = "ROLE_";
	private static final long serialVersionUID = 1L;

	private String nickName;

	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	

	public SecurityMember(Member member) {
		super(member.getUserId(), member.getPassword(), makeGrantedAuthority(member));
		
		this.nickName = member.getNickName();
	
	}

	private static List<GrantedAuthority> makeGrantedAuthority(Member member) {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(ROLE_PREFIX + member.getAuthorId()));
		return list;
	}



}
