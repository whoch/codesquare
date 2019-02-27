package com.bit.codesquare.controller.planner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.codesquare.dto.planner.GroupMeetingDateDetails;
import com.bit.codesquare.dto.planner.SeminarMeetingDateDetails;
import com.bit.codesquare.mapper.group.GroupMapper;
import com.bit.codesquare.mapper.planner.CalendarMapper;



@Controller
public class CalendarController {

	@Autowired
	CalendarMapper calendarMapper;
	
	Logger logger = LoggerFactory.getLogger(CalendarController.class);	
	
	@Autowired
	GroupMapper gm;
	
	/**
	 * 
	 * @param model
	 * @brief "/calendar" 페이지 출력
	 * @ToDo 리팩토링 필요, TODOLIST 불러오기 추가구현 필요(TODOLIST 구현이 아직 안된상태), content정보 추가 보강 필요,클릭시 해당 페이지 이동 구현 필요
	 * 
	 */
	
	@RequestMapping("/calendar")
	public String calendar(Model model, Authentication auth) {	
		String userId = auth.getName();
		
		List<HashMap<String, Object>> seminarScheduleList=new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> groupMeetingScheduleList=new ArrayList<HashMap<String, Object>>();		
		
		List<SeminarMeetingDateDetails> seminarData=calendarMapper.getAllScheduleSeminar();
		List<GroupMeetingDateDetails> groupMeetingData=calendarMapper.getAllScheduleGroup();
		
		for(SeminarMeetingDateDetails list : seminarData) {
			HashMap<String, Object> schedule = new HashMap<String, Object>();
			LocalTime meetingTime = list.getSeminarStartDate().toLocalTime();
			
			schedule.put("title", "세미나" );
			schedule.put("start", list.getSeminarStartDate());
			schedule.put("end", list.getSeminarEndDate().atTime(meetingTime));
			schedule.put("description","강사:"+list.getNickname()+"<BR/>장소:"+list.getLocale());
			
			seminarScheduleList.add(schedule);
		}//seminarScheduleList.add();
		
		for(GroupMeetingDateDetails info : groupMeetingData) {
			HashMap<String, Object> schedule = new HashMap<String, Object>();
			
			schedule.put("title", "그룹");
			schedule.put("start", info.getMeetingDate());
			schedule.put("description", "그룹:"+info.getGroupId()+"<BR/>장소:"+info.getLocale());
			
			groupMeetingScheduleList.add(schedule);
		}//groupMeetingScheduleList.add();				
		
		model.addAttribute("seminar",seminarScheduleList);
		model.addAttribute("study",groupMeetingScheduleList);
		
		model.addAttribute("glist", gm.getMyGroupList(userId));
		return "planner/calendar";	
	
	}
}
