package com.bit.codesquare.dto.planner;


import java.time.LocalDateTime;

import lombok.Data;


/*
 * status는 DB에서 가져올 때 true,false,none의 문자열로 변환해서 가져오고있어서 자료형을 String으로 했습니다
 * */
@Data
public class GroupMeetingDateDetails {

	String id;
	String groupId;
	String userId;
	String locale;
	LocalDateTime meetingDate;
	int cost;
	String status;
}
