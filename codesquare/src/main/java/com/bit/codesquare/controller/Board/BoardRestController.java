package com.bit.codesquare.controller.Board;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.mapper.board.BoardMapper;

@RestController
@RequestMapping("/Board")
public class BoardRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardRestController.class);
	
	@Autowired
	BoardMapper boardMapper;
	List<Board> board=new ArrayList<Board>();
	
	@GetMapping("/all")
	@ResponseBody
	public List<Board> getQuickBoard(@RequestParam String tag){
		try {
			board=boardMapper.getQuickBoardList(tag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return board;
	}
}
