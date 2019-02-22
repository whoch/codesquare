package com.bit.codesquare.dto.board;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import com.bit.codesquare.dto.member.JoiningAndRecruitmentLog;
import com.bit.codesquare.util.ComparableDateTime;

import lombok.Data;

@Data
public class Board implements ComparableDateTime {
	int id;
	String boardName;
	String userId;
	String groupId;
	String nickName;
	String title;
	String content;
	LocalDateTime writeDate;
	Date modifyDate;
	int hit;
	int fileCount;
	int imageCount;
	int commentCount;
	int likeCount;
	int parentId;
	int status;
	
	String searchOption;
	String keyword;
	
	String writeDateBoard;
	List <JoiningAndRecruitmentLog> wantedPlist;

	@Override
	public LocalDateTime getDateTimeCompare() {
		// TODO Auto-generated method stub
		return writeDate;
	}

	@Override
	public void setDateTimeCompare(String format) {
		this.writeDateBoard=format;
		
	}
	
}
