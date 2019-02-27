package com.bit.codesquare.dto.group;


import java.sql.Timestamp;


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
	
	Timestamp startDate;
	int recruitmentCount;
	String meetingDate;
	String ApplicationForm;
	String week;
}
