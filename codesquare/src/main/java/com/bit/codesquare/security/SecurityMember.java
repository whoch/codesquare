package com.bit.codesquare.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.bit.codesquare.dto.member.Member;

public class SecurityMember extends User implements UserDetails {

	
	private static final String ROLE_PREFIX = "ROLE_";
	private static final long serialVersionUID = 1L;


	private int authorId;
	
	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	private String nickName;
	private String isPendingLectureView="true";
	
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getIsPendLectureView() {
		return isPendingLectureView;
	}

	public void setIsPendLectureView(String isPendLectureView) {
		this.isPendingLectureView = isPendLectureView;
	}

	private static List<GrantedAuthority> makeGrantedAuthority(int authorId) {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(ROLE_PREFIX + authorId));
		return list;
	}

	
	public SecurityMember(Member member) {
		super(member.getUserId(), member.getPassword(), makeGrantedAuthority(member.getAuthorId()));
		this.nickName = member.getNickName();
		this.authorId= member.getAuthorId();
		System.out.println(this.authorId+"시큐리티멤버");
	}


	





}
