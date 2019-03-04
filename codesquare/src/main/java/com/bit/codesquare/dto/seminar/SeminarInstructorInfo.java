package com.bit.codesquare.dto.seminar;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SeminarInstructorInfo {

	int boardid;
	String userid;
	String localeid;
	LocalDateTime seminarstartdate;
	LocalDate seminarenddate;
	LocalDate seminarjoinstartdate;
	LocalDate seminarjoinenddate;
	int recruitpersonnel;
	String coordinates;
	String locale;
	String introContent;
	String history;
	String meetingDate;
	

}
