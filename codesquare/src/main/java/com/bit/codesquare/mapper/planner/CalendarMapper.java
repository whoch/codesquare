package com.bit.codesquare.mapper.planner;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.planner.GroupMeetingDateDetails;
import com.bit.codesquare.dto.planner.SeminarMeetingDateDetails;

@Mapper
public interface CalendarMapper {
	/**
	 * @brief 사용자가 참여중인 세미나의 일정을 가져온다
	 * @author 김찬영
	 * @todo 사용자 아이디 참조변수로 받는것
	 */
	public List<SeminarMeetingDateDetails> getScheduleSeminar();
	
	/**
	 * @brief 사용자가 참여중인 그룹의 모임 일정을 가져온다
	 * @author 김찬영
	 * @todo 사용자 아이디 참조변수로 받는것
	 */
	public List<GroupMeetingDateDetails> getScheduleGroup();
	
}
