package com.bit.codesquare.dto.planner;

import lombok.Data;

@Data
/**
 * 유저의 북마크한 대분류를 가져오는 Dto
 * */
public class UserBookmarkCatecory{
	

	
	/**
	 * 카테고리는 boardKind의 앞부분 3글자를 자른 대분류
	 * */
	String category;
	String boardName;
	
}
