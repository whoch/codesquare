package com.bit.codesquare.controller.planner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.codesquare.dto.planner.CalendarEvent;
import com.bit.codesquare.dto.planner.CalendarEventDow;
import com.bit.codesquare.dto.planner.GroupMeetingDateDetails;
import com.bit.codesquare.dto.planner.SeminarMeetingDateDetails;
import com.bit.codesquare.mapper.group.GroupMapper;
import com.bit.codesquare.mapper.planner.CalendarMapper;
import com.bit.codesquare.mapper.seminar.SeminarMapper;



@Controller
public class CalendarController {
   @Autowired
   GroupMapper groupMapper;
   @Autowired
   SeminarMapper seminarMapper;
   @Autowired
   CalendarMapper calendarMapper;
   Logger logger = LoggerFactory.getLogger(CalendarController.class);   
   
   CalendarEvent event;
   CalendarEventDow eventDow;
   List<CalendarEvent> eventList;
   List<CalendarEventDow> eventDowList;
   
   @RequestMapping("/calendar")
   public String calendar(Model model, Authentication auth) {   
      String userId = auth.getName();

      model.addAttribute("GroupConfirmedEventTRUE",getConfirmedEventInGroup(calendarMapper.getGroupScheduleTRUE(userId)));
      model.addAttribute("GroupConfirmedEventFALSE",getConfirmedEventInGroup(calendarMapper.getGroupScheduleFALSE(userId)));
      model.addAttribute("GroupConfirmedEventNULL",getConfirmedEventInGroup(calendarMapper.getGroupScheduleNULL(userId)));
      model.addAttribute("GroupUpComingEvent",getUpcomingEventInGroup(userId));
      model.addAttribute("SeminarEvent", getEventSeminar(userId));
      return "planner/calendar";   
   }
   
   enum Day {
      일(0),월(1),화(2),수(3),목(4),금(5),토(6);
      private int index;
      Day(int index){
         this.index=index;
      }
   }
   
   public List<CalendarEvent> getConfirmedEventInGroup(List<GroupMeetingDateDetails> data) {
      eventList = new ArrayList<CalendarEvent>();
      for(GroupMeetingDateDetails item : data) {
         event = new CalendarEvent();
         String status = item.getStatus();
         event.setTitle("그룹");
         event.setId("E"+item.getId());
         event.setStart(item.getMeetingDate());
         event.setDescription( makeCalendarModalHTML(item)+
               "<div class='attend'>"+(status!=null&&!status.isEmpty()?"이 날 모임은 "+
               (status.equals("1")?"<span class='yes tag-custom'>참석</span>":"<span class='no tag-custom'>불참</span>")+"으로 체크했어요!</div>":
               "<span id='none'>아직 참석 여부를 체크하지 않았습니다!</span></div></div>"+
               "<div id='attend-check'><div id='"+item.getId()+"' class='yes btn btn-purple shadow'>참석</div><div id='"+item.getId()+"' class='no btn btn-grey shadow'>불참</div></div>"));
         eventList.add(event);
      }
      return eventList;
   }//getConfirmedEventInGroup
   

   public List<CalendarEventDow> getUpcomingEventInGroup(String userId) {
      eventDowList = new ArrayList<CalendarEventDow>();
      List<GroupMeetingDateDetails> groupInfo = groupMapper.getGroupInfoUserJoinning(userId);
      LocalDate startDate;
      LocalDate recentMeetingDate;
      LocalDate dowStart;
      LocalTime meetingTime;
      int[] dow;
      
      for(GroupMeetingDateDetails item : groupInfo) {
         eventDow = new CalendarEventDow();
         meetingTime = item.getStartDate().toLocalTime();
         startDate = item.getStartDate().toLocalDate();
         dow = getDowArray(item.getMeetingDay());
         recentMeetingDate = groupMapper.getRecentMeetingDate(item.getGroupId());
         dowStart= (recentMeetingDate!=null?recentMeetingDate.plusDays(1):startDate);
         
         eventDow.setTitle("그룹");
         eventDow.setDow(dow);
         eventDow.setStart(meetingTime);
         eventDow.setDowStart(dowStart);
         eventDow.setDowEnd(dowStart.plusYears(1));
         eventDow.setDescription(makeCalendarModalHTML(item)+
               "<div class='attend'><span id='yet'>아직 예정 상태인 일정입니다!</span></div></div>");
         eventDowList.add(eventDow);
      }
      return eventDowList;
   }//getUpcomingEventInGroup
   
   
   public String makeCalendarModalHTML(GroupMeetingDateDetails item) {
       String html = "<div class='row'>";
            html +=   "<div class='col-md-5'>";
            html +=   "  <img class='rounded-circle shadow' src='/static/codesquareDB/default/default_user_image4.jpg'>";
            html +=   "</div>";
            html +=   "<div class='content col-md-7'>";
            html +=   "  <div class='groupId'>"+item.getGroupId()+"</div>";
            html +=   "  <div><span>언어 :</span> <span>"+item.getTagId()+"</span></div>";
            html +=   "  <div><span>레벨 :</span> <span>"+item.getLevelId()+"</span></div>";
            html +=   "  <div><span>지역 :</span> <span>"+item.getLocaleId()+"</span></div>";
            html += (item.getCost()==null?"":"  <div><span>비용 :</span> <span>"+item.getCost()+"</span></div>");
            html += "</div>";
      return html;
   }//makeCalendarModalHTML
   
   public List<CalendarEventDow> getEventSeminar(String userId) {
      List<SeminarMeetingDateDetails> seminarInfo = seminarMapper.getSeminarInfoUseUserid(userId);
      eventDow = new CalendarEventDow();
      eventDowList = new ArrayList<CalendarEventDow>();
      LocalDateTime seminarStartDate;
      LocalTime meetingTime;
      int[] dow;
      
      for(SeminarMeetingDateDetails item : seminarInfo) {
         seminarStartDate = item.getSeminarStartDate();
         meetingTime = seminarStartDate.toLocalTime();
         dow = getDowArray(item.getMeetingDay());
         
         eventDow.setTitle("세미나");
         eventDow.setDow(dow);
         eventDow.setStart(meetingTime);
         eventDow.setDowStart(seminarStartDate.toLocalDate());
         eventDow.setDowEnd(item.getSeminarEndDate());
         eventDow.setDescription("<div class='row'>"+"<div class='col-md-5'><img class='rounded-circle shadow' src="+
               (item.getProfileImagePath().equals("DefaultThumbnail")?
               "'/static/images/DefaultThumbnail.png'":
               "'/static/codesquareDB/UserThumbnail/"+item.getUserId()+"/"+item.getProfileImagePath()+"'")+   
               "></div>"+
               "<div class='content col-md-7'>"+
               "  <div class='groupId'>"+item.getNickname()+"</div>"+
               "  <div class='title'>"+item.getTitle()+"</div>"+
               "  <div class='locale'><small>"+item.getLocale()+"</small></div>"+
               "</div></div>"+
               "<div class='goUrl'><div class='btn-deepPurple shadow'><a href='/go/"+item.getBoardId()+"'>해당글 이동</a></div></div>");
         eventDowList.add(eventDow);
      }
      return eventDowList;
   }//getEventSeminar
   
   public int[] getDowArray(String day) {
      String[] days = day.split(",");
      int[] dow = new int[days.length];
      for(int i=0,size=dow.length; i<size; i++) {
         dow[i]=Day.valueOf(days[i]).index;
      }
      return dow;
   }//getDowArray
   
   @PostMapping("/groupAttendCheck")
   @ResponseBody
   public void updateGroupMeetingStatus(@RequestBody Map<String, String> data) {
      groupMapper.updateGroupMeetingStatus(data);
      logger.info("##test : "+data.toString());
   }// 미완성  ajax 안먹힘 :: updateGroupMeetingStatus
}

