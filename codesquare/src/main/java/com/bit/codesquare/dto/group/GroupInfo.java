package com.bit.codesquare.dto.group;


import java.time.LocalDateTime;

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
	LocalDateTime startDate;
	int recruitmentCount;
	int[] meetingDate;
}
