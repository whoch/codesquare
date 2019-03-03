package com.bit.codesquare.service.planner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.codesquare.controller.planner.CalendarController;
import com.bit.codesquare.dto.planner.CalendarEvent;
import com.bit.codesquare.dto.planner.CalendarEventDow;
import com.bit.codesquare.dto.planner.GroupMeetingDateDetails;
import com.bit.codesquare.dto.planner.SeminarMeetingDateDetails;
import com.bit.codesquare.dto.planner.UserGroupNoticeList;
import com.bit.codesquare.mapper.planner.DashboardMapper;
import com.bit.codesquare.util.CodesquareUtil;

@Service
public class DashboardService {
	
	@Autowired
	DashboardMapper dashboardMapper;
	
	@Autowired
	CodesquareUtil util;
	@Autowired
	CalendarController cc;
	Logger logger = LoggerFactory.getLogger(DashboardService.class);
	
	CalendarEventDow eventDow;
	List<CalendarEvent> eventList;
	List<CalendarEventDow> eventDowList;
	
	List<SeminarMeetingDateDetails> seminarList;
	GroupMeetingDateDetails group;
	
	public List<Object> getAllSchedule(String userId) {
		List<Object> allSchedule = new ArrayList<Object>();
		List<SeminarMeetingDateDetails> seminarData = dashboardMapper.getWeeklyScheduleSeminar(userId);
		List<SeminarMeetingDateDetails> seminarSchedule = new ArrayList<SeminarMeetingDateDetails>();
		LocalDate today=LocalDate.now();
		SeminarMeetingDateDetails schedule;
		for(SeminarMeetingDateDetails list : seminarData) {
			int[] days = cc.getDowArray(list.getMeetingDay());
			LocalDate endDate = list.getSeminarEndDate();
			LocalTime meetingTime=list.getSeminarStartDate().toLocalTime();
			
			for(int i=0,day=days.length; i<day; i++) {
				LocalDate meetingDate=today.with(TemporalAdjusters.nextOrSame(DayOfWeek.of(days[i])));
				if(meetingDate.isBefore(endDate)) {
					schedule = new SeminarMeetingDateDetails();
					BeanUtils.copyProperties(list, schedule);
					schedule.setMeetingDate(meetingDate.atTime(meetingTime));
					seminarSchedule.add(schedule);
				}
			}			
		}// end foreach(seminarData)
		allSchedule.addAll(seminarSchedule);
		allSchedule.addAll(dashboardMapper.getWeeklyScheduleGroup(userId));
		return allSchedule;
	}
	

	public Map<String, Integer> getUserStats(String userId) {
		Map<String, Integer> cardContent = new HashMap<String, Integer>();
		cardContent.put("groupCnt", dashboardMapper.getCountJoiningGroup(userId));
		cardContent.put("seminarCnt", dashboardMapper.getCountJoiningSeminar(userId));
		cardContent.put("lectureCnt", dashboardMapper.getCountLearningLecture(userId));
		cardContent.put("todoCnt", dashboardMapper.getCountToDo(userId));
		return cardContent;
	}
	
	public List<UserGroupNoticeList> getUserGroupNoticeList(String userId){
		List<UserGroupNoticeList> groupNoticeLists = dashboardMapper.getUserGroupNoticeList(userId);
		util.getDateTimeCompareObject(groupNoticeLists, "yy-MM-dd");
		return  groupNoticeLists;
	}
	
}
