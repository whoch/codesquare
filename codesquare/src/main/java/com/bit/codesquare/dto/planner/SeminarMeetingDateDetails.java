package com.bit.codesquare.dto.planner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SeminarMeetingDateDetails {

	String id;
	String boardId;
	String applyUserId;
	String userId;
	String nickname;
	String title;
	
	LocalDateTime seminarStartDate;
	LocalDate seminarEndDate;
	String locale;
	String meetingDay;
	String profileImagePath;
	
	LocalDateTime meetingDate;
}
