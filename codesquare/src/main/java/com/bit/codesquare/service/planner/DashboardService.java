package com.bit.codesquare.service.planner;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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

import com.bit.codesquare.dto.planner.SeminarMeetingDateDetails;
import com.bit.codesquare.dto.planner.UserGroupNoticeList;
import com.bit.codesquare.mapper.planner.DashboardMapper;

@Service
public class DashboardService {
	
	@Autowired
	DashboardMapper dashboardMapper;
	Logger logger = LoggerFactory.getLogger(DashboardService.class);
	
	
	public List<Object> getAllSchedule() {
		List<SeminarMeetingDateDetails> seminarData = dashboardMapper.getWeeklyScheduleSeminar();
		List<SeminarMeetingDateDetails> seminarSchedule = new ArrayList<SeminarMeetingDateDetails>();
		
		List<Object> allSchedule = new ArrayList<Object>();
		LocalDate today=LocalDate.now();
		
		for(SeminarMeetingDateDetails list : seminarData) {
//			이부분 주 3회,5회 이런식으로 요일로 선택해서 받아와야됨
			int[] days = new int[] {2,5};
			LocalDate endDate = list.getSeminarEndDate();
			LocalTime meetingTime=list.getSeminarStartDate().toLocalTime();
			
			for(int i=0,day=days.length; i<day; i++) {
				LocalDate meetingDate=today.with(TemporalAdjusters.nextOrSame(DayOfWeek.of(days[i])));
				if(!meetingDate.isAfter(endDate)) {
					SeminarMeetingDateDetails schedule = new SeminarMeetingDateDetails();
					BeanUtils.copyProperties(list, schedule);
					schedule.setMeetingDate(meetingDate.atTime(meetingTime));
					seminarSchedule.add(schedule);
				}
			}			
		}// end foreach(seminarData)
		
		allSchedule.addAll(dashboardMapper.getWeeklyScheduleGroup());
		allSchedule.addAll(seminarSchedule);
		
		return allSchedule;
	}
	

	public Map<String, Integer> getUserStats() {
		Map<String, Integer> cardContent = new HashMap<String, Integer>();
		cardContent.put("groupCnt", dashboardMapper.getCountJoiningGroup());
		cardContent.put("seminarCnt", dashboardMapper.getCountJoiningSeminar());
		cardContent.put("lectureCnt", dashboardMapper.getCountLearningLecture());
		cardContent.put("todoCnt", dashboardMapper.getCountToDo());
		return cardContent;
	}
	
	public List<UserGroupNoticeList> getUserGroupNoticeList(){
		List<UserGroupNoticeList> groupNoticeLists = dashboardMapper.getUserGroupNoticeList();
		for(UserGroupNoticeList list : groupNoticeLists) {
			list.setWriteDateFormat(getDifferenceInTime(list.getWriteDate()));			
		}
		return groupNoticeLists;
	}
	
	
	public static String getDifferenceInTime(LocalDateTime writeDateTime) {
		LocalDate writeDate = writeDateTime.toLocalDate();
		LocalTime writeTime = writeDateTime.toLocalTime();
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		String result;
		if(writeDateTime!=null && writeDate.isEqual(currentDate)) { //오늘일 때
			int differenceInMinutes = (int)Duration.between(writeTime,currentTime).toMinutes();
			if(differenceInMinutes<1) {
				result="방금 전";
			}else if(differenceInMinutes<60) {
				result=differenceInMinutes+"분 전";
			}else {
				result=differenceInMinutes/60+"시간 전";
			}
		}else { //오늘이 아닐 때
			int differenceInDates = Period.between(writeDate, currentDate).getDays();
			if(differenceInDates<=7) {
				result=differenceInDates+"일 전";
			}else {
				result=writeDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
			}
		}
		return result;
	}

}
