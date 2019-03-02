package com.bit.codesquare.controller.planner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public String dashBoard(Model model) {
		logger.info("#test : "+dashboardMapper.getUserLectureList().toString());
		model.addAttribute("allSchedule", dashboardService.getAllSchedule());
		model.addAttribute("userStats", dashboardService.getUserStats());
		model.addAttribute("cardLecture", dashboardMapper.getUserLectureList());
		model.addAttribute("cardGroupNotice", dashboardService.getUserGroupNoticeList());
		return "planner/dashboard";		
	}
	

}
