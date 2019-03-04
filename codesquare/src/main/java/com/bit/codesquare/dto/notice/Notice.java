package com.bit.codesquare.dto.notice;

import java.sql.Date;
import java.time.LocalDateTime;

import com.bit.codesquare.util.ComparableDateTime;

import lombok.Data;

@Data
public class Notice implements ComparableDateTime{
	int noticeId;
	int id;
	String userId;
	String boardKindId;
	String noticeContent;
	String noticeLink;
	LocalDateTime sendDate;
	LocalDateTime confirmDate;
	String readStatus;
	
	String sendDateString;
	@Override
	public LocalDateTime getDateTimeCompare() {
		// TODO Auto-generated method stub
		return sendDate;
	}
	@Override
	public void setDateTimeCompare(String format) {
		// TODO Auto-generated method stub
		this.sendDateString = format;
	}
	
}
