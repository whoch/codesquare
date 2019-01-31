package com.bit.codesquare.dto.planner;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SeminarMeetingDateDetails {

	
	String boardId;
	String userId;
	String locale;
	Timestamp seminarStartDate;
	Timestamp seminarEndDate;	
	String nickname;
	
}
