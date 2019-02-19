package com.bit.codesquare.controller.study;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.codesquare.dto.board.BoardKind;
import com.bit.codesquare.mapper.study.StudyMapper;
import com.bit.codesquare.service.study.StudyService;
import com.bit.codesquare.util.CodesquareUtil;
import com.bit.codesquare.util.DayEnum;


@Controller
public class StudyController {
	
	@Autowired
	StudyService studyService;
	
	@Autowired
	StudyMapper studyMapper;
	
	@Autowired
	CodesquareUtil util;
	
	Logger logger = LoggerFactory.getLogger(StudyController.class);
	
	/*
	 * 아직 보드게시판이 만들어지지 않아 임시로 목록을 뿌려주기만 하는 페이지	
	 * */
	@RequestMapping("{boardNameEn}")
	public String getBoardList(@PathVariable("boardNameEn") String boardNameEn,Model model) {
		
		BoardKind boardKind = studyMapper.getBoardKind(boardNameEn);
		String boardName = boardKind.getBoardName();
		String boardMainSubjectName = boardKind.getMainSubjectName();
		
		if(boardName==null || boardName.isEmpty()) {
			return "이 부분 메인페이지로 가거나 404 페이지 만들어서 거기로 가게 만들기";
		}
		
		model.addAttribute("boardList", studyMapper.getBoardList(boardName));
//		return "study/studyWanted";
		return boardMainSubjectName+"/"+boardNameEn;
	}
	
	@RequestMapping(value = "/{boardNameEn}/{boardId}")
	public String getBoardView(@PathVariable("boardNameEn") String boardNameEn, @PathVariable("boardId") int boardId, Model model) {
		
		BoardKind boardKind = studyMapper.getBoardKind(boardNameEn);
		String boardName = boardKind.getBoardName();
		String boardMainSubjectName = boardKind.getMainSubjectName();
		
	
		model.addAttribute("board", studyMapper.getBoardView(boardName, boardId));
		model.addAttribute("group", studyMapper.getGroupInfo(boardId));
		model.addAttribute("bookmarkId", studyMapper.getBookmark(boardId));
		
		
		logger.info(" ## getBookmark : "+ studyMapper.getBookmark(boardId));
		
//		return "study/studyWantedView";
		return boardMainSubjectName+"/"+boardNameEn+"View";

	}

	
	@PostMapping("/clickBookmark")
	@ResponseBody
	public Map clickBookmark(@RequestBody Map<String, String> data) {
		studyMapper.addBookmark(data);
		logger.info(data.toString());
		logger.info("안될텐데 " );
		return data;
	}
	
	
	
}
