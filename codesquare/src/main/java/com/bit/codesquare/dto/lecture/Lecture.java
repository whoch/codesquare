package com.bit.codesquare.dto.lecture;

import lombok.Data;

@Data
public class Lecture {
	int id; 
	String boardKindId; 
	String nickname;
	String title;
	String content;
	int fileCount;
	int imageCount;
	int commentCount;
	int parentId;
	String videoUrl;
	String userId;
	String lectureStatus;
}

