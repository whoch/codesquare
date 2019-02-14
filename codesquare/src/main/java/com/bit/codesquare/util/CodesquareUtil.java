package com.bit.codesquare.util;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.security.SecurityMember;

@Component
public class CodesquareUtil {
	@Value("${user.thumbnail.directory}")
	String userThumbPath;
	/**
	 * 
	 * @param postId  글번호
	 * @param imgName 이미지의 이름
	 * @return   글번호+ 이미지의 이름
	 */
	public String getPath(int postId, String imgName) {
		return postId+"/"+imgName;
	}
	
	/**
	 * 
	 * @param postId 글번호
	 * @param imgName 이미지의 이름
	 * @param extension 확장자 이름
	 * @return  글번호+이미지의 이름+확장자의 이름
	 */
	public String getPath(int postId, String imgName, String extension) {
		return postId+"/"+imgName+"."+extension;
	}
	
	/**
	 * 
	 * @param userId  회원아이디
	 * @param imgName 이미지의 이름
	 * @return 회원아이디+이미지의 이름
	 */
	public String getPath(String userId, String imgName) {
		return userId+"/"+imgName;
	}
	
	/**
	 * 
	 * @param cTime 회원이 영상을 본 현재시간
	 * @param dTime 영상의 총 길이
	 * @return 회원이 영상을 완강했는지 여부
	 */
	public String isClearcalc(Double cTime, Double dTime) {
		double val=(cTime/dTime)*100;
		return (val>=90)?"END":"ING";
	}
	
	
	
	@Autowired
	MemberMapper mm;
	
	public void getSession(Authentication auth, HttpSession session) {
		
		if ( auth != null && session.getAttribute("userId")==null ) {
			SecurityMember sc = (SecurityMember) auth.getPrincipal();
			Member member =mm.getUser(sc.getUsername());
			member.setProfileImagePath(userThumbPath+=getPath(member.getUserId(), member.getProfileImagePath()));
			
			session.setAttribute("userId", member.getUserId());
			session.setAttribute("nickName", member.getNickName());
			session.setAttribute("authorId", member.getAuthorId());
			session.setAttribute("profileImagePath", member.getProfileImagePath());
		}
	}
	
}
