package com.bit.codesquare.dto.board;

import java.sql.Date;
import java.util.List;

import com.bit.codesquare.dto.member.JoiningAndRecruitmentLog;

import lombok.Data;

@Data
public class Board {
	int id;
	String boardKindId;
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
	
	List <JoiningAndRecruitmentLog> wantedPlist;
	
}
