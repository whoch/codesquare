package com.bit.codesquare.util;

public class CodesquareUtil {
	
	public String getPath(int postId, String imgName) {
		return postId+"/"+imgName;
	}
	public String getPath(int postId, String imgName, String extension) {
		return postId+"/"+imgName+"."+extension;
	}
	public String getPath(String userId, String imgName) {
		return userId+"/"+imgName;
	}
}
