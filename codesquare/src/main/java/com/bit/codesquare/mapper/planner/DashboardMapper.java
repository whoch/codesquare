package com.bit.codesquare.mapper.planner;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.planner.GroupMeetingDateDetails;
import com.bit.codesquare.dto.planner.SeminarMeetingDateDetails;
import com.bit.codesquare.dto.planner.UserGroupNoticeList;
import com.bit.codesquare.dto.planner.UserLectureList;

@Mapper
public interface DashboardMapper {
	public List<GroupMeetingDateDetails> getWeeklyScheduleGroup(String userId);
	public List<SeminarMeetingDateDetails> getWeeklyScheduleSeminar(String userId);
	public int getCountJoiningGroup(String userId);
	public int getCountJoiningSeminar(String userId);
	public int getCountLearningLecture(String userId);
	public int getCountToDo(String userId);
	public List<UserLectureList> getUserLectureList(String userId);
	public List<UserGroupNoticeList> getUserGroupNoticeList(String userId);
}
