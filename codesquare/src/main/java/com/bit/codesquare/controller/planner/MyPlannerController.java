package com.bit.codesquare.controller.planner;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.codesquare.dto.planner.UserTodoList;
import com.bit.codesquare.mapper.planner.MyplannerMapper;
import com.bit.codesquare.service.planner.MyplannerService;
import com.bit.codesquare.util.CodesquareUtil;

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
		model.addAttribute("todoList", myplannerMapper.getUserTodoList());
		return "planner/myPlanner";
	}
	
	@PostMapping("/loadBookmark")
	public String loadBookmark(@RequestBody String userId, Model model) {
		model.addAttribute("bookmarkList", myplannerService.getUserBookmarkList());
		model.addAttribute("bookmarkKind", myplannerMapper.getUserBookmarkKinds());
		return "planner/ajax/userBookmarkList";		
	}
	
	@PostMapping("writeTodo")
	@ResponseBody
	public UserTodoList writeTodo(UserTodoList userTodoList){
		myplannerMapper.writeTodo(userTodoList);
		return userTodoList;
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public void deleteTodo(@RequestBody Map<String, String> data) {
		myplannerMapper.deleteUsingId(data);
	}
	
	@PostMapping("/updateTodo")
	@ResponseBody
	public void updateTodo(@RequestBody Map<String, String> data) {
		myplannerMapper.updateTodo(data);
	}

	@PostMapping("/checkedTodo")
	@ResponseBody
	public String[] checkedTodo(@RequestBody Map<String, String> data) {
		myplannerMapper.updateTodoStatus(data);
		return myplannerMapper.getRowNumTodo();
	}
	
}
