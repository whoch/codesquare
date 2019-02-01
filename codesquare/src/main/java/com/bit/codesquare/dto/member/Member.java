package com.bit.codesquare.dto.member;

import java.sql.Date;

import lombok.Data;

@Data
public class Member {
	
	private String userId;
	private int authorId;
	private int restrictId;
	private String nickName;
	private String password;
	private String email;
	private String name;
	private String dateOfBirth;
	private Date signUpDate;
	private int point;
	private String profileImagePath;
	private int messageCount;
	private int reservationCount;
	private int myStudyCount;
	private int myPostCount;
	private int banCount;
	

}
