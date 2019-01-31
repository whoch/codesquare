package com.bit.codesquare.controller.Board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.mapper.board.BoardMapper;

@Controller
@RequestMapping(value= {"/","index","main"})
public class BoardController {
	
	@Autowired
	BoardMapper boardMapper;
	List<Board> board=new ArrayList<Board>();
	
//	@RequestMapping(value= {"/","index","main"})
	@RequestMapping(method=RequestMethod.GET)
	public String start(Model model) {
		try {
			board= boardMapper.getRecommandList();
			model.addAttribute("riList",board);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
	}
	
	
	
}
