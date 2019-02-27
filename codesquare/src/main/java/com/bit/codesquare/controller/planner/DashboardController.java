package com.bit.codesquare.controller.planner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.codesquare.mapper.group.GroupMapper;
import com.bit.codesquare.mapper.planner.DashboardMapper;
import com.bit.codesquare.service.planner.DashboardService;


@Controller
public class DashboardController {
	
	@Autowired
	DashboardMapper dashboardMapper;
	@Autowired
	DashboardService dashboardService;
	Logger logger= LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	GroupMapper gm;
	
	@RequestMapping("/dashboard")
	public String dashBoard(Model model, Authentication auth) {
		String userId = auth.getName();
		model.addAttribute("allSchedule", dashboardService.getAllSchedule());
		model.addAttribute("userStats", dashboardService.getUserStats());
		model.addAttribute("cardLecture", dashboardMapper.getUserLectureList());
		model.addAttribute("cardGroupNotice", dashboardService.getUserGroupNoticeList());
		model.addAttribute("glist", gm.getMyGroupList(userId));
		
		return "planner/dashboard";		
	}
//	@GetMapping("glist")
//	public String getMyGroupList (Model model, Authentication auth) {
//		String userId = auth.getName();
//		model.addAttribute("glist", gm.getMyGroupList(userId));
//		
//		return "group/groupList";
//	}
//	
//	@GetMapping("group/{groupId}")
//	public String getGroupInfo(Model model, @PathVariable String groupId) {
//		model.addAttribute("ginfo", gm.getGroupInfo(groupId));
//		model.addAttribute("gMember", gm.getGroupMember(groupId));
//		model.addAttribute("gLeader", gm.getGroupLeader(groupId));
//		return "group/groupInfo";
//	}

}
