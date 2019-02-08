package com.bit.codesquare.dto.board;

import java.sql.Date;

import lombok.Data;

@Data
public class Board {
	int id;
	String boardName;
	String userId;
	String groupId;
	String nickName;
	String title;
	String content;
	Date writeDate;
	Date modifyDate;
	int hit;
	int fileCount;
	int imageCount;
	int commentCount;
	int likeCount;
	int parentId;
	int status;
}
