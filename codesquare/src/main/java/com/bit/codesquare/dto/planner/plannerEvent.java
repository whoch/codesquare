package com.bit.codesquare.dto.planner;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class plannerEvent{
	
	String title;
	LocalDateTime start;
/*	LocalDateTime end;*/
	String description;
	String id;
}
