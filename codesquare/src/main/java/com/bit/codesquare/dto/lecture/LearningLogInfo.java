package com.bit.codesquare.dto.lecture;

import java.sql.Date;
import lombok.Data;

@Data
public class LearningLogInfo {
	int parentId;
	int boardId;
	double currentTime;
	double duration;
	Date hearingDate;
	String isClear;
	String userId;
}
