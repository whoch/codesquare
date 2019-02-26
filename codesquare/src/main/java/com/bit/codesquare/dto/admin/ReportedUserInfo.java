package com.bit.codesquare.dto.admin;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReportedUserInfo {
	int id;
	String reportingUserId;
	String reportedUserId;
	int commentId;
	int boardId;
	LocalDateTime reportedDate;
	String content;
	String status;
	int reportedCount;
	int rowNum;
}
