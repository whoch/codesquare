package com.bit.codesquare.dto.room;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Reservation {

	int no;
	String roomid;
	int boardid;
	String userid;
	Date reservationdate;
	Timestamp paymentdate;
	int payamount;
	int reservationtime;
	int usetime;
	int person;
	Date enddate;
	
}
