package com.bit.codesquare.dto.board;

import lombok.Data;

@Data
public class BoardKind {
	String id;
	String boardName;
	String boardNameEn;
	String uploadPath;
	
	/**
	 * @see : 게시판 대분류 이름
	 * */
	String mainSubjectName;
}
