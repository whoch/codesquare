package com.bit.codesquare.dto.planner;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CalendarEvent{
	
	String title;
	LocalDateTime start;
/*	LocalDateTime end;*/
	String description;
	String id;
}
