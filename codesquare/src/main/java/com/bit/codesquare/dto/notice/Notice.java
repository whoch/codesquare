package com.bit.codesquare.dto.notice;

import java.sql.Date;

import lombok.Data;

@Data
public class Notice {
	int noticeId;
	int id;
	String userId;
	String boardKindId;
	String noticeContent;
	String noticeLink;
	Date sendDate;
	Date confirmDate;
	String readStatus;	
}
