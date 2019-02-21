package com.bit.codesquare.dto.planner;

import java.sql.Date;
import java.time.LocalDateTime;

import com.bit.codesquare.util.ComparableDateTime;

import lombok.Data;

@Data
public class UserTodoList implements ComparableDateTime {


	String id; 
	String userId;
	String content;
	LocalDateTime writeDate;
	Date endDate;
	String status;
	
	String rowNum;
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
