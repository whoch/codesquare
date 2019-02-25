package com.bit.codesquare.controller.board;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.paging.Criteria;
import com.bit.codesquare.dto.paging.PageMaker;
import com.bit.codesquare.mapper.board.NewMapper;
import com.bit.codesquare.service.NewService;

@Controller
public class NewController {
	private static final Logger Logger = LoggerFactory.getLogger(NewController.class);
	
	@Autowired
	NewMapper newMapper;
	@Autowired
	NewService newService;
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String noticeBoard(@ModelAttribute("cri") Criteria cri, Model model) throws Exception {
		model.addAttribute("list", newService.listCriteria(cri)); // 글리스트
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(newService.listCountCriteria(cri));
		model.addAttribute("pageMaker", pageMaker);
		return "board/noticeBoard";
	}
	
	@RequestMapping("/noticeView")
	public String noticeView(Model model, HttpServletRequest request, Criteria cri) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		newMapper.updateCount(id);
		model.addAttribute("list", newMapper.getid(id));
		
		return "board/noticeView";
	}
	
	@RequestMapping("/noticeWrite")
	public String write() {
		return "board/noticeWrite";
	}
	
	@RequestMapping("/writego")
	public String writego(Model model, @ModelAttribute Criteria cri, Board board) throws Exception {
		newMapper.insert(board);
		model.addAttribute("list", newService.listCountCriteria(cri));
		return "redirect:home";
	}
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model, Criteria cri) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		newMapper.delete(id);
		model.addAttribute("list", newService.listCountCriteria(cri));
		return "redirect:home";
	}
	
	@RequestMapping("/modifygo")
	public String modifygo(HttpServletRequest request, Model model) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		model.addAttribute("list", newMapper.getid(id));
		return "board/noticeModify";
	}
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model, @ModelAttribute Board board) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		newMapper.update(board);
		return "redirect:home";
	}
}
