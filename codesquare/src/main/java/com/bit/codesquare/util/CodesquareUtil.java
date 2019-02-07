package com.bit.codesquare.util;

public class CodesquareUtil {
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
}
