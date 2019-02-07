package com.bit.codesquare.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.security.SecurityMember;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SocialService {
   MemberMapper mm;
   
   public UsernamePasswordAuthenticationToken doAuthentication(Member member) {
      if (mm.checkUser(member.getUserId())!=null) {
           // 기존 회원일 경우에는 데이터베이스에서 조회해서 인증 처리
           final User user = new SecurityMember(member);
           
           return setAuthenticationToken(user);
       } else {
           // 새 회원일 경우 회원가입 이후 인증 처리
          mm.signUp(member);
          Member member1 = mm.checkUser(member.getUserId());
          
           final SecurityMember user = new SecurityMember(member1);
           
           
         
           return setAuthenticationToken(user);
       }
   }
   
    private UsernamePasswordAuthenticationToken setAuthenticationToken(Object user) {
           return new UsernamePasswordAuthenticationToken(user,
                 null, getAuthorities("ROLE_1"));
       }
    
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
           List<GrantedAuthority> authorities = new ArrayList<>();
           authorities.add(new SimpleGrantedAuthority(role));
           return authorities;
       }
}
