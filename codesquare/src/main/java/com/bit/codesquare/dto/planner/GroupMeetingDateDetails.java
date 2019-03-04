package com.bit.codesquare.dto.planner;


import java.time.LocalDateTime;

import lombok.Data;


@Data
public class GroupMeetingDateDetails {

	String id;
	String groupId;
	String levelId;
	String tagId;
	String localeId;
	String userId;
	String locale;
	Integer cost;
	String status;
	LocalDateTime startDate;
	LocalDateTime meetingDate;
	String meetingDay;
}
