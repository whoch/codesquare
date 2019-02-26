package com.bit.codesquare.controller.board;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.mapper.board.BoardMapper;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
@RequestMapping(value= {"/","index","main"})
public class BoardController {
	
	@Autowired
	BoardMapper boardMapper;
	List<Board> board=new ArrayList<Board>();
	
	
	@Autowired
	CodesquareUtil csu;
	
	@RequestMapping(method=RequestMethod.GET)
	public String start(Model model, Authentication auth, HttpSession session) {
		
		
		csu.getSession(auth, session);
		
		try {
			board= boardMapper.getRecommandList();
			model.addAttribute("riList",board);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index3";
	}
//
//	@RequestMapping("new")
//	public String main2(Model model, Authentication auth, HttpSession session) {
//		
//		csu.getSession(auth, session);
//		
//		try {
//			board= boardMapper.getRecommandList();
//			model.addAttribute("riList",board);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return "index3";
//	}
	
	
}
