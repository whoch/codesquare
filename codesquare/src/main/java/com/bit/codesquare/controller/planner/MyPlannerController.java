package com.bit.codesquare.controller.planner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.codesquare.dto.planner.UserTodoList;
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
		model.addAttribute("todoList", myplannerMapper.getUserTodoList());
		return "planner/myPlanner";
	}
	
	@PostMapping("/test")
	public String test(@RequestBody Object JSONallSchedule,Model model) {
		logger.info(JSONallSchedule.toString());
		logger.info("#########오긴오는거니############");
		model.addAttribute("allSchedule", JSONallSchedule);
		return "planner/test";		
	}
	
	
	@RequestMapping("todolist")
	public String todolist(UserTodoList userTodoList){
		myplannerMapper.writeTodo(userTodoList);
		return "redirect:/myplanner";
	}
	
	
	
}
