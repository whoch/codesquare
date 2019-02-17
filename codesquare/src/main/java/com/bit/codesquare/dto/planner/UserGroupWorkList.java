package com.bit.codesquare.dto.planner;

import java.time.LocalDateTime;

import com.bit.codesquare.util.ComparableDateTime;

import lombok.Data;

/**
 * @author Chanyoung
 * @brief 유저가 가입한 그룹들의 과제 리스트를 가져오는 Dto
 * */
@Data
public class UserGroupWorkList implements ComparableDateTime{

	String id;
	String groupId;
	String title;
	String content;
	LocalDateTime writeDate;
	
	String writeDateFormat;

	@Override
	public LocalDateTime getDateTimeCompare() {
		// TODO Auto-generated method stub
		return this.writeDate;
	}

	@Override
	public void setDateTimeCompare(String format) {
		// TODO Auto-generated method stub
		this.writeDateFormat = format;
	}
	
}
