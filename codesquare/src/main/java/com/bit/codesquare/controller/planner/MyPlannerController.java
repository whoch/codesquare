package com.bit.codesquare.controller.planner;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.board.BoardKind;
import com.bit.codesquare.dto.group.GroupInfo;
import com.bit.codesquare.dto.planner.UserTodoList;
import com.bit.codesquare.mapper.planner.MyplannerMapper;
import com.bit.codesquare.mapper.study.StudyMapper;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
public class MyPlannerController {

	@Autowired
	MyplannerMapper myplannerMapper;
	
	@Autowired
	CodesquareUtil csu;
	
	Logger logger = LoggerFactory.getLogger(MyPlannerController.class);
	
	@RequestMapping("/myplanner")
	public String myPlanner(Model model, Authentication auth) {
		String userId = auth.getName();
		model.addAttribute("groupWorkList", csu.getDateTimeCompareObject(myplannerMapper.getGroupWorkList(userId)));
		model.addAttribute("todoList", myplannerMapper.getUserTodoList(userId));
		return "planner/myPlanner";
	}
	
	@PostMapping("/loadBookmark")
	public String loadBookmark(Model model, Authentication auth) {
		String userId = auth.getName();
		model.addAttribute("bookmarkList", csu.getDateTimeCompareObject(myplannerMapper.getUserBookmarkList(userId)));
		model.addAttribute("bookmarkKind", myplannerMapper.getUserBookmarkKinds(userId));
		return "planner/ajax/userBookmarkList";		
	}
	
	@PostMapping("writeTodo")
	@ResponseBody
	public UserTodoList writeTodo(UserTodoList userTodoList){
		myplannerMapper.writeTodo(userTodoList);
		return userTodoList;
	}
	
	@PostMapping("/deleteTodo")
	@ResponseBody
	public void deleteTodo(@RequestBody Map<String, String> data) {
		myplannerMapper.deleteUsingIdAndBoardKind(data);
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
		return myplannerMapper.getRowNumTodo(data.get("userId"));
	}
	
	@PostMapping("/planner/deleteBookMark")
	@ResponseBody
	public void deleteBookMark(@RequestBody Map<String, String> data) {
		myplannerMapper.deleteBookmarkUsingId(data);
		myplannerMapper.updateBookmarkDeleteCount(data);
	}
	
	@RequestMapping(value = "/go/{boardId}")
	public String getBoardViewUseBoardId(@PathVariable("boardId") int boardId) {
		try {
			BoardKind boardKind = myplannerMapper.getBoardkindDetailUseBoardId(boardId);
			String boardKindId=boardKind.getId();
			switch (boardKindId) {
			case "StdMo":
				return "redirect:/"+boardKind.getMainSubjectName()+"/"+boardKindId+"/"+boardId;
			case "SmnMo":
				return "redirect:/"+boardKind.getMainSubjectName()+"/"+boardKindId+"/"+boardId;
			case "LrnPr":
				return "redirect:/"+boardKind.getMainSubjectName()+"/intro/"+boardId;
			default:
				return "redirect:/freeView/"+boardKindId+"?id="+boardId;
			}
		} catch (Exception e) {
			return "redirect:/";
		}
	}
	
}
