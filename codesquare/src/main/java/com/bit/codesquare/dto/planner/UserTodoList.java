package com.bit.codesquare.dto.planner;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserTodoList {


	String id; 
	String userId;
	String content;
	LocalDateTime writeDate;
	Date endDate;
	String status;
	
	String rowNum;
}
