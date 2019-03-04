package com.bit.codesquare.controller.planner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.codesquare.mapper.planner.DashboardMapper;
import com.bit.codesquare.service.planner.DashboardService;


@Controller
public class DashboardController {
	
	@Autowired
	DashboardMapper dashboardMapper;
	@Autowired
	DashboardService dashboardService;
	Logger logger= LoggerFactory.getLogger(DashboardController.class);
	
	@RequestMapping("/dashboard")
	public String dashBoard(Model model, Authentication auth) {
		String userId = auth.getName();
		
		model.addAttribute("allSchedule", dashboardService.getAllSchedule(userId));
		model.addAttribute("userStats", dashboardService.getUserStats(userId));
		model.addAttribute("cardLecture", dashboardMapper.getUserLectureList(userId));
		model.addAttribute("cardGroupNotice", dashboardService.getUserGroupNoticeList(userId));
		
		logger.info("###TEST : " + dashboardService.getAllSchedule(userId).toString());
		return "planner/dashboard";		
	}
	

}
