package com.bit.codesquare.dto.planner;

import lombok.Data;

/**
 * @author Chanyoung
 * @brief 유저의 학습 현황을 가져오는 Dto
 * @see userId와 nickname은 강사의 것, LectureCount=각 강좌별 강의챕터 개수, LearningRate는 올림으로 계산
 * */
@Data
public class UserLectureList {
	String parentId;
	String userId;
	String nickname;
	String title;
	String introContent;
	int learningRate;
	
}
