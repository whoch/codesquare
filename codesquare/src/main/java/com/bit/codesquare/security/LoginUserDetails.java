package com.bit.codesquare.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.bit.codesquare.dto.member.Member;


public class LoginUserDetails extends User{
	

	
//	private String userId;
//	private String password;
	Member member;
	private String nickName;
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public LoginUserDetails(String userId, String password, String nickName, Collection<? extends GrantedAuthority> authorId) {
		super(userId, password, authorId);
	
		this.nickName=nickName;
		}
//
//	public LoginUserDetails(com.exam.codesquare.domain.User user) {
//		super(user.getUserId(), user.getPassword(), user.getAuthorId());
//		this.userId = user.getUserId();
//		this.password = user.getPassword();
//		this.nickName = user.getNickName();
//	
//		}

		    /* 사실 여기선 이거밖에 안씀. 위에 두개는 어떤거 하는지 모르겠는데 좀더 연구한 후에... */
		public LoginUserDetails (String userId, String password,String nickName, String s) {
		super(userId, password, AuthorityUtils.createAuthorityList(s));
		}


}
