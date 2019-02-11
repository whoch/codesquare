package com.bit.codesquare.dto.planner;



import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserBookmarkList {


	String id; 
	String boardId;
	String boardKindId;
	String groupId; 
	String nickname; 
	String title; 
	String content; 
	LocalDateTime writeDate;
	String status;

	String writeDateFormat;
}
