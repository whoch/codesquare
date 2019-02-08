package com.bit.codesquare.security;

import org.springframework.beans.factory.annotation.Autowired;
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

		if (member != null) {
			return new SecurityMember(member);
		} else {
			return null;
		}
	}
}
