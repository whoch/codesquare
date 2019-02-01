package com.bit.codesquare.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.member.MemberMapper;



@Service
public class LoginUserDetailsService implements UserDetailsService{
	@Autowired
    MemberMapper mm; 
	
	private String nickName;
    
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
       Member user = mm.checkUser(userId); 
       String prefix = "ROLE_";
       this.nickName = user.getNickName();
        /* 그냥 String 으로 security User 를 상속한 도메인 객체에 때려박음... 맞는지는 모르겠음 */
        return new LoginUserDetails(user.getUserId(), user.getPassword(), user.getNickName(), prefix+user.getAuthorId());

    }

}
