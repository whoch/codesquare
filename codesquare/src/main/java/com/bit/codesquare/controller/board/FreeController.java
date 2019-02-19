package com.bit.codesquare.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
