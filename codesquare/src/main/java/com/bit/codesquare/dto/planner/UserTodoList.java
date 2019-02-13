package com.bit.codesquare.dto.planner;



import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserTodoList {


	String id; 
	String userId;
	String content;
	LocalDateTime writeDate;
	LocalDateTime endDate;
	String status;
}
