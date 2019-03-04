package com.bit.codesquare.mapper.planner;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.planner.GroupMeetingDateDetails;
import com.bit.codesquare.dto.planner.SeminarMeetingDateDetails;

@Mapper
public interface CalendarMapper {
	
	/**
	 * @brief 사용자가 참여중인 그룹의 모임 일정을 가져온다
	 * @author 김찬영
	 * @todo 사용자 아이디 참조변수로 받는것
	 */
	public List<GroupMeetingDateDetails> getGroupScheduleTRUE(String userId);
	public List<GroupMeetingDateDetails> getGroupScheduleNULL(String userId);
	public List<GroupMeetingDateDetails> getGroupScheduleFALSE(String userId);
	
	
}
