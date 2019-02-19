package com.bit.codesquare.dto.planner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SeminarMeetingDateDetails {

	
	String boardId;
	String userId;
	String locale;
	LocalDateTime seminarStartDate;
	LocalDate seminarEndDate;
	LocalDateTime meetingDate;
	String nickname;
	
}
