package com.bit.codesquare.dto.lecture;

import lombok.Data;

@Data
public class LectureIntroContent {
	int id;
	String boardNameEn;
	String title;
	String nickName;
	String changedName;
	String extension;
	String tag;
	String introContent;
	String priceInfo;
	int studentCount;
	int completeCount;
	String thumbnailPath;
}
