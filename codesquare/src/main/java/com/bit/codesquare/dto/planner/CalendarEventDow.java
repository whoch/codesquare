package com.bit.codesquare.dto.planner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class CalendarEventDow{
	
	String title;
	LocalTime start;
/*	LocalDateTime end;*/
	String description;
	int[] dow;
	LocalDate dowStart;
	LocalDate dowEnd;
}
