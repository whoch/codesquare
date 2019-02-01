package com.bit.codesquare.dto.member;

import java.sql.Date;
import java.sql.Time;

import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper=true)
@Data
public class ReservationInfo extends Member{

	
	private String id;
	private String roomId;
	private String roomName;
	private int boardId;
	private Date reservationDate;
	private Date paymentDate;
	private int payAmount;
	private Time reservationTime;
	private Time useTime;
	
}
