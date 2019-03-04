package com.bit.codesquare.dto.seminar;

import java.sql.Date;

import lombok.Data;

@Data
public class SeminarInfo {

	int boardid;
	String userid;
	String localeid;
	Date seminarstartdate;
	Date seminarenddate;
	Date seminarjoinstartdate;
	Date seminarjoinenddate;
	int recruitpersonnel;
	String coordinates;
	String locale;
	
	String meetingDate;
}
