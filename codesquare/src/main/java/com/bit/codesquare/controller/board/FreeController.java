package com.bit.codesquare.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.paging.Criteria;
import com.bit.codesquare.mapper.board.FreeMapper;

@Controller
public class FreeController {

	@Autowired
	FreeMapper freeMapper;
	
	@RequestMapping("/getfree")
	public String getfree (Model model) throws Exception {
		model.addAttribute("list", freeMapper.getfree());
		return "board/freeBoard";
	}
	@RequestMapping("/freeWrite")
	public String write() {
		return "board/freeWrite";
	}
	@RequestMapping("/freewritego")
	public String freewritego(Model model, @ModelAttribute Criteria cri, Board board) throws Exception {
		freeMapper.insert(board);
		return "redirect:getfree";
	}
}
