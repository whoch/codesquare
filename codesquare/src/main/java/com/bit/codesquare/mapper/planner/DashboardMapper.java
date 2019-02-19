package com.bit.codesquare.mapper.planner;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.planner.GroupMeetingDateDetails;
import com.bit.codesquare.dto.planner.SeminarMeetingDateDetails;
import com.bit.codesquare.dto.planner.UserGroupNoticeList;
import com.bit.codesquare.dto.planner.UserLectureList;

@Mapper
public interface DashboardMapper {
	public List<GroupMeetingDateDetails> getWeeklyScheduleGroup();
	public List<SeminarMeetingDateDetails> getWeeklyScheduleSeminar();
	public int getCountJoiningGroup();
	public int getCountJoiningSeminar();
	public int getCountLearningLecture();
	public int getCountToDo();
	public List<UserLectureList> getUserLectureList();
	public List<UserGroupNoticeList> getUserGroupNoticeList();
}
