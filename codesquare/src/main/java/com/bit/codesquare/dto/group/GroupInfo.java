package com.bit.codesquare.dto.group;


import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GroupInfo {

	String id;
	String groupId;
	String levelId;
	String tagId;
	String localeId;
	String coordinates;
	int memberCount;
	String goal;
	String content;
	
	LocalDateTime startDate;
	int recruitmentCount;
	String meetingDate;
	String applicationForm;
	
//	얜 뭐였지
	String week;
}
