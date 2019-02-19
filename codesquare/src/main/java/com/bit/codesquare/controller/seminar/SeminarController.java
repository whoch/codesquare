package com.bit.codesquare.controller.seminar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.mapper.seminar.SeminarMapper;

@Controller
public class SeminarController {
	
	@Autowired
	SeminarMapper seminarMapper;
	
	@RequestMapping("/seminarList")
	public String noticeBoard(Model model) throws Exception {
		model.addAttribute("list", seminarMapper.seminarList());
//		model.addAttribute("info", seminarMapper.seminarInfoList());
		return "seminar/seminarList";
	}
	
//	@GetMapping("/seminarList")
//	public ModelAndView seminarList() throws Exception {
//		List<Board> seminarList = seminarMapper.seminarList();
//		ModelAndView nextPage = new ModelAndView("seminar/seminarList");
//		nextPage.addObject("list", seminarList);
//		return nextPage;
//	}
	
//	@PostMapping("/seminarList")
//	public void create(Board board) throws Exception {
//		seminarMapper.in
//	}
//	@RequestMapping("/seminarView")
//	public String noticeView(Model model, HttpServletRequest request) throws Exception {
//		int id = Integer.parseInt(request.getParameter("id"));
//		model.addAttribute("list", seminarMapper.getid(id));
//		return "seminar/seminarList";
//	}
}
