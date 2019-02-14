package com.bit.codesquare.util;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.security.SecurityMember;

@Component
public class CodesquareUtil {
	/**
	 * 
	 * @param postId  글번호
	 * @param imgName 이미지의 이름
	 * @return 글번호+ 이미지의 이름
	 */
	public String getPath(int postId, String imgName) {
		return postId + "/" + imgName;
	}

	/**
	 * 
	 * @param postId    글번호
	 * @param imgName   이미지의 이름
	 * @param extension 확장자 이름
	 * @return 글번호+이미지의 이름+확장자의 이름
	 */
	public String getPath(int postId, String imgName, String extension) {
		return postId + "/" + imgName + "." + extension;
	}

	/**
	 * 
	 * @param userId  회원아이디
	 * @param imgName 이미지의 이름
	 * @return 회원아이디+이미지의 이름
	 */
	public String getPath(String userId, String imgName) {
		return userId + "/" + imgName;
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
			session.setAttribute("profileImagePath", member.getProfileImagePath());
		}
	}

	
    public void uploadProfile(MultipartFile[] uploadForm, Authentication auth) {

    	String userId = auth.getName();
    	String uploadFolder = "/Users/jiyeon/git/codesquare/codesquare/src/main/resources/static/codesquareDB/UserThumbnail/"+userId;
    	//db에 저장할 상대경로
    	//String uploadRelativeDirectory = "/static/codesquareDB/UserThumbnail/"+userId;
    	
    	File uploadPath= new File(uploadFolder); //안에 여러개 쓰면 합쳐짐
    	
    	if (!uploadPath.exists()) {
    		uploadPath.mkdirs(); //존재하지 않으면 경로를 만든다
        }
    	
    	String uploadFileName = userId+"_Thumbnail.jpg"; //+multipartFile.getOriginalFilename()하면 업로드한 파일네임으로 들어감

        try {
        	File saveFile = new File(uploadPath, uploadFileName);
        	uploadForm[0].transferTo(saveFile); //실제저장되는단계. savefile:경로랑 파일명 합친거
        } catch (Exception e) {
        	e.getMessage();
        }

    }

}
