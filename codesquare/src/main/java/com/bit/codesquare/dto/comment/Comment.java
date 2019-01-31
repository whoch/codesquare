package com.bit.codesquare.dto.comment;

import java.sql.Date;

import lombok.Data;

@Data
public class Comment {
	int id; 
	int boardId; 
	String boardKindId; 
	String userId;
	String nickName; 
	String content;
	int parentId;
	Date writeDate;
}
