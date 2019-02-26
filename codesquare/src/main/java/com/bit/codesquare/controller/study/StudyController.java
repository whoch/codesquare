package com.bit.codesquare.controller.study;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.group.GroupInfo;
import com.bit.codesquare.dto.group.WriteWantedBoard;
import com.bit.codesquare.dto.member.JoiningAndRecruitmentLog;
import com.bit.codesquare.mapper.group.GroupMapper;
import com.bit.codesquare.mapper.study.StudyMapper;
import com.bit.codesquare.util.CodesquareUtil;


@Controller
public class StudyController {
	
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
		
		GroupInfo group = groupMapper.getGroupInfoUseBoardId(boardId);
		Board board = studyMapper.getBoardView("스터디모집", boardId);
		if( !group.getApplicationForm().isEmpty() ) {
			BasicJsonParser jsonParser = new BasicJsonParser();
			model.addAttribute("question", jsonParser.parseList(group.getApplicationForm()));
			logger.info( " ## parsing한 결과값 : "+jsonParser.parseList( group.getApplicationForm()).toString());
		}
		
		String applyStatus = groupMapper.getApplyingStatus(boardId, "cksdud");
		Integer joinningGroupId = groupMapper.getJoinningGroupId("cksdud", group.getId());
		String statusOfTheUser;
		
		if(board.getStatus()==0) {
			statusOfTheUser="END";
		}else if( joinningGroupId!=null && joinningGroupId > 0) {
			statusOfTheUser="IN";
		}else if( applyStatus!=null && applyStatus.equals("") ){
			statusOfTheUser="WAIT";
		}else {
			statusOfTheUser="ING";
		}
		
		model.addAttribute("group", group);
		model.addAttribute("board", board );
		model.addAttribute("bookmarkId", studyMapper.getBookmarkId(boardId));
		model.addAttribute("status", statusOfTheUser);
		
		logger.info("------------ 유저의 그룹스터디 신청 상태 : "+statusOfTheUser+"------------------");
		logger.info("이 그룹에 신청한 상태 : " + applyStatus);
		logger.info("이 그룹에 가입한 상태 : " + groupMapper.getJoinningGroupId("cksdud", group.getId()));
		return "study/studyWantedView";
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
		model.addAttribute("group", groupMapper.getGroupInfoUserLeader("cksdud"));
		return "study/studyWantedWriteView";
	}

	
	//글쓰기 submit 했을 때
	/*
	 * 글 제목, 내용 미입력시 글 안써지게 해야되는데 ajax 사용시 required가 먹히지 않아 미구현입니다
	 * */
	@PostMapping("/studyWanted/write")
	@ResponseBody
	public String writeStudyWantedBoard(@RequestBody WriteWantedBoard data) {
		Board board = data.getBoard();
		studyMapper.writeStudyWantedBoard(board);
		
		if(!board.getGroupId().equals("그룹없음")) {
			groupMapper.updateWantedInfo(data);
		}
		return  "/studyWanted/"+board.getId();
	}
	
	@PostMapping("/studyWanted/submitApplication")
	@ResponseBody
	public String submitApplication(@RequestBody JoiningAndRecruitmentLog data) {
		groupMapper.insertStudyJoining(data);
		return "WAIT";
	}
	
	
	@RequestMapping("/studyWanted/test")
	public String test(Model model) {
		BasicJsonParser jsonPaser = new BasicJsonParser();
		JoiningAndRecruitmentLog test = groupMapper.test();
		
		model.addAttribute("test", test);
		model.addAttribute("map", jsonPaser.parseMap(test.getApplyContent()));
		
		return "study/test";
	}
	
	@PostMapping("/studyWanted/cancelApplication")
	@ResponseBody
	public String cancelApplication(@RequestBody Map<String, String> data) {
		logger.info("### cancel Application");
		groupMapper.cancelApplication(data);
		logger.info(data.toString());
		return "ING";
	}
	
	@RequestMapping("/studyWanted/createGroup")
	public void createGroup() {
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/studyWanted/delete")
	public String deleteBoard() {
		
//		@PathVariable("boardId") int boardId
//		logger.info("boardId : " +boardId);
		return "/studyWanted";
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
