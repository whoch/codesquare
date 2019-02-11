package com.bit.codesquare.controller.planner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.codesquare.mapper.planner.MyplannerMapper;
import com.bit.codesquare.service.planner.MyplannerService;

@Controller
public class MyPlannerController {

	@Autowired
	MyplannerMapper myplannerMapper;
	
	@Autowired
	MyplannerService myplannerService;
	
	Logger logger = LoggerFactory.getLogger(MyPlannerController.class);
	
	@RequestMapping("/myplanner")
	public String myPlanner(Model model) {
		
		model.addAttribute("groupWorkList", myplannerService.getUsergetGroupWorkList());
		model.addAttribute("bookmarkList", myplannerService.getUserBookmarkList());
		model.addAttribute("bookmarkKind", myplannerMapper.getUserBookmarkKinds());
		
		String[] strArr=myplannerMapper.getUserBookmarkKinds();
		for(String list : strArr) {
			logger.info("list : " + list.toString());
		}
		
		
		return "planner/myPlanner";
	}
}
