package com.bit.codesquare.dto.room;

import java.sql.Date;
import java.sql.Time;
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
	Time reservationtime;
	Time usetime;
	int person;
	Date enddate;
	String roomname;
}
