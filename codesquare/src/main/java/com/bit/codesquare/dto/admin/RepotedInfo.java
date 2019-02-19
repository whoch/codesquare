package com.bit.codesquare.dto.admin;

import java.sql.Date;

import lombok.Data;

@Data
public class RepotedInfo {
	int iD;
	String reportingUserId;
	String reportedUserId;
	int commentId;
	int boardId;
	Date reportedDate;
	String content;
	String status;
}
