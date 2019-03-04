package com.bit.codesquare.dto.comment;

import java.time.LocalDateTime;
import java.util.List;

import com.bit.codesquare.dto.member.JoiningAndRecruitmentLog;
import com.bit.codesquare.util.ComparableDateTime;

import lombok.Data;

@Data
public class ReplyDto implements ComparableDateTime {

	private Integer cno;
	private Integer bno;
	private String content;
	private String writer;
	private LocalDateTime reg_date;
	private Integer coCount;
	
	String writeDateBoard;
	List <JoiningAndRecruitmentLog> wantedPlist;
	@Override
	public LocalDateTime getDateTimeCompare() {
		// TODO Auto-generated method stub
		return reg_date;
	}
	@Override
	public void setDateTimeCompare(String format) {
		this.writeDateBoard=format;
	}

}
