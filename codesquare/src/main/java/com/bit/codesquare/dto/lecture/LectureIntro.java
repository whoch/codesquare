package com.bit.codesquare.dto.lecture;

import com.bit.codesquare.dto.board.Board;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class LectureIntro extends Board{
	String tag;
	String langKind;
	int studentCount;
	int completeCount;
	String priceInfo;
	String changedName;
	String extension;
	String introContent;
	String history;
	String thumbnailPath;
}
