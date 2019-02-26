package com.bit.codesquare.controller.study;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.board.BoardKind;
import com.bit.codesquare.dto.group.GroupInfo;
import com.bit.codesquare.mapper.group.GroupMapper;
import com.bit.codesquare.mapper.study.StudyMapper;
import com.bit.codesquare.service.study.StudyService;
import com.bit.codesquare.util.CodesquareUtil;


@Controller
public class StudyController {
	
	@Autowired
	StudyService studyService;
	
	@Autowired
	StudyMapper studyMapper;
	
	@Autowired
	GroupMapper groupMapper;
	
	@Autowired
	CodesquareUtil util;
	
	Logger logger = LoggerFactory.getLogger(StudyController.class);
	
	/*
	 * 아직 보드게시판이 만들어지지 않아 임시로 목록을 뿌려주기만 하는 페이지	
	 * */
	@RequestMapping("/studyWanted")
	public String getBoardList(Model model) {
		String boardName = "스터디모집";
		model.addAttribute("boardList", studyMapper.getBoardList(boardName));
			
		return "study/studyWanted";
	}
	//게시판 상세보기로 이동
	@RequestMapping(value = "/studyWanted/{boardId}")
	public String getBoardView(@PathVariable("boardId") int boardId, Model model) {
	
		model.addAttribute("board", studyMapper.getBoardView("스터디모집", boardId));
		model.addAttribute("group", groupMapper.getGroupInfoUseBoardId(boardId));
		model.addAttribute("bookmarkId", studyMapper.getBookmarkId(boardId));
		
		return "study/studyWantedView";
//		return boardMainSubjectName+"/"+boardNameEn+"View";
	}

	//게시글에서 북마크 아이콘을 클릭할 경우 찜,찜취소
	@PostMapping("/clickBookmark")
	@ResponseBody
	public Map clickBookmark(@RequestBody Map<String, String> data) {
		boolean status = Boolean.parseBoolean(data.get("status"));
		if(status) {
			studyMapper.addBookmark(data);
		}else {
			studyMapper.deleteBookmark(data);
		}
		return data;
	}
	
	//글쓰기 페이지로 이동
	@RequestMapping("/studyWanted/writeView")
	public String getWriteView(Model model) {
		model.addAttribute("group", groupMapper.getGroupInfoUserLeader("cksdud3"));
		for(GroupInfo test: groupMapper.getGroupInfoUserLeader("cksdud3")) {
			logger.info(test.toString());
		}
		logger.info(groupMapper.getGroupInfoUserLeader("cksdud3").toString());
		return "study/studyWantedWriteView";
	}

	
	//글쓰기 submit 했을 때
	@RequestMapping(value="/studyWanted/write", method=RequestMethod.POST)
	@ResponseBody
	public String writeStudyWantedBoard(@RequestBody Board board) {
		logger.info("-----------  여기왔니  ------------");
		logger.info(board.toString());
		
/*		studyMapper.writeStudyWantedBoard(board);
		String boardName = boardKind.getBoardName();
		model.addAttribute("boardList", studyMapper.getBoardList(boardName));
		
		*/
		
		return  "왜";
	}
	
	
	
	
	
	
	/*@RequestMapping("{boardNameEn}")
	public String getBoardList(@PathVariable("boardNameEn") String boardNameEn,Model model) {
		
		try{
			BoardKind boardKind = studyMapper.getBoardKind(boardNameEn);
			String boardName = boardKind.getBoardName();
			model.addAttribute("boardList", studyMapper.getBoardList(boardName));
			
		}catch(Exception e) {
			
			 * 이 부분 메인페이지로 가거나 404 페이지 만들어서 거기로 가게 만들기
			 * 
			logger.error("getBoardList() 메서드 --> 파라미터에 DB boardKind 테이블에 정의되지 않은 값이 들어왔을 때 NULL Point Exception이 발생됩니다");
		}
		return "study/studyWanted";
		//		return boardMainSubjectName+"/"+boardNameEn;
		
	}
	
	*/
	
	
}
