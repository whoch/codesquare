package com.bit.codesquare.dto.planner;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class GroupMeetingDateDetails {

	String Id;
	String GroupId;
	String UserId;
	String Locale;
	Timestamp MeetingDate;
	int Cost;
	
}
