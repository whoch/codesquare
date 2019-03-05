package com.bit.codesquare.dto.group;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class GroupInfoForm {

/*	String id;
	int memberCount;
	int recruitmentCount;
	String meetingDate;*/
	
	String groupId;
	String levelId;
	String tagId;
	String localeId;
	String goal;
	String content;
	
	LocalDate startDate;
	LocalTime startTime;
	
	String meetingDay;
	String guGun;
	
	String siDo;
	
}
