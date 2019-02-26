package com.bit.codesquare.dto.member;

import java.sql.Date;
import java.util.Map;

import lombok.Data;

@Data
public class Member {
	
	private String userId;
	private int authorId;
	private String authorName;
	private int restrictId;
	private String restrictName;
	private String nickName;
	private String password;
	private String email;
	private String name;
	private Date dateOfBirth;
	private Date signUpDate;
	private int point;
	private String profileImagePath;
	private int messageCount;
	private int reservationCount;
	private int myStudyCount;
	private int myPostCount;
	private int banCount;
	


}
