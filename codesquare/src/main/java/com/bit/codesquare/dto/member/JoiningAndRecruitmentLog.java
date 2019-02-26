package com.bit.codesquare.dto.member;

import java.time.LocalDateTime;
import java.util.Map;

import com.bit.codesquare.util.ComparableDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper=true)
@Data
public class JoiningAndRecruitmentLog  extends Member implements ComparableDateTime {
	
	private int boardId;
	private String boardKindId;
	private String applyUserId;
	private String title;
	private String nickname;
	private LocalDateTime applyDate;
	private String applyDateString;
	private LocalDateTime writeDate;
	private String writeDateString;
	private String applyContent;
	private String declineContent;
	private String status;
	
	Map<String, Object> applyContentMap;

	@Override
	public LocalDateTime getDateTimeCompare() {
		// TODO Auto-generated method stub
		return applyDate;
	}

	@Override
	public void setDateTimeCompare(String format) {
		// TODO Auto-generated method stub
		this.applyDateString = format;
		
	}

	
}
