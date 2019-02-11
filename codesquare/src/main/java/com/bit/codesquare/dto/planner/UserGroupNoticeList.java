package com.bit.codesquare.dto.planner;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author Chanyoung
 * @brief 유저가 가입한 그룹들의 공지사항을 가져오는 Dto
 * */
@Data
public class UserGroupNoticeList {

	String groupId;
	String nickname;
	String title;
	String content;
	LocalDateTime writeDate;
	
	String writeDateFormat;
	
}
