package com.bit.codesquare.dto.group;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class GroupInfo {

	String id;
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
	
	String week;
}
