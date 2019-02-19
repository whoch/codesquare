package com.bit.codesquare.dto.planner;



import java.time.LocalDateTime;

import com.bit.codesquare.util.ComparableDateTime;

import lombok.Data;

@Data
public class UserBookmarkList implements ComparableDateTime {


	String id; 
	String boardId;
	String boardKindId;
	String boardName;
	String groupId; 
	String nickname; 
	String title; 
	String content; 
	LocalDateTime writeDate;
	String status;

	String writeDateFormat;

	@Override
	public LocalDateTime getDateTimeCompare() {
		// TODO Auto-generated method stub
		return this.writeDate;
	}

	@Override
	public void setDateTimeCompare(String format) {
		// TODO Auto-generated method stub
		this.writeDateFormat = format;
		
	}


}
