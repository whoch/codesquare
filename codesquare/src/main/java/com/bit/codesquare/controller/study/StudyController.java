package com.bit.codesquare.controller.study;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.security.core.Authentication;
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
	@RequestMapping("/study/StdMo")
	public String getBoardList(Model model) {
		String boardName = "스터디모집";
		model.addAttribute("boardList", studyMapper.getBoardList(boardName));
		return "study/studyWanted";
	}
	
//	/freeView/NewNt?id=235
//	/studyWanted/{boardId}
	//게시판 상세보기로 이동
	@RequestMapping(value = "/study/StdMo/{boardId}")
	public String getBoardView(@PathVariable("boardId") int boardId, Model model, Authentication auth) {
		GroupInfo group = groupMapper.getGroupInfoUseBoardId(boardId);
		Board board = studyMapper.getBoardView("스터디모집", boardId);
		
		if( !group.getApplicationForm().isEmpty() ) {
			BasicJsonParser jsonParser = new BasicJsonParser();
			model.addAttribute("question", jsonParser.parseList(group.getApplicationForm()));
		}

		String statusOfTheUser;

		try{
			String userId = auth.getName();
			String applyStatus = groupMapper.getApplyingStatus(boardId, userId);
			Integer joinningGroupAuthorId = groupMapper.getJoinningGroupInfo(userId, group.getId(), "AuthorId");
			
			if(board.getStatus()==0) {
				statusOfTheUser="END";
			}else if( joinningGroupAuthorId!=null && joinningGroupAuthorId == 5) {
				statusOfTheUser="CLOSE";
			}else if( joinningGroupAuthorId!=null && joinningGroupAuthorId == 4) {
				statusOfTheUser="IN";
			}else if( applyStatus!=null && applyStatus.equals("") ){
				statusOfTheUser="WAIT";
			}else {
				statusOfTheUser="ING";
			}
			model.addAttribute("bookmarkId", studyMapper.getBookmarkId(boardId, userId));
		}catch (Exception e) {
			if(board.getStatus()==0) {
				statusOfTheUser="END";
			}else {
				statusOfTheUser="ING";
			}
		}
		
		model.addAttribute("status", statusOfTheUser);
		model.addAttribute("group", group);
		model.addAttribute("board", CodesquareUtil.setDateTimeCompare(board));
		return "study/studyWantedView";
	}

	
	//게시글에서 북마크 아이콘을 클릭할 경우 찜,찜취소
	@PostMapping("/clickBookmark")
	@ResponseBody
	public void clickBookmark(@RequestBody Map<String, String> data) {
		boolean status = Boolean.parseBoolean(data.get("status"));
		logger.info("#test : "+data.toString());
		studyMapper.updateBoardLikeCount(data);
		if(status) {
			studyMapper.addBookmark(data);
		}else {
			studyMapper.deleteBookmark(data);
		}
	}
	
	
	//글쓰기 페이지로 이동
	@RequestMapping("/studyWanted/writeView")
	public String getWriteView(Model model, Authentication auth) {
		String userId = auth.getName();
		model.addAttribute("group", groupMapper.getGroupInfoUserLeader(userId));
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
	
	@PostMapping("/studyWanted/cancelApplication")
	@ResponseBody
	public String cancelApplication(@RequestBody Map<String, String> data) {
		groupMapper.cancelApplication(data);
		return "ING";
	}
	
	@PostMapping("/studyWanted/wantedClose")
	@ResponseBody
	public String wantedClose(@RequestBody Map<String, String> data) {
		logger.info("##test : " + data.toString());
		studyMapper.updateBoardStatus(data);
		groupMapper.updateGroupRecruitmentCount(data.get("groupId"), 0);
		groupMapper.setDeclineContentUseBoardId(data);
		return "END";
	}
	
	@RequestMapping("/studyWanted/createGroup")
	public void createGroup() {
		
		
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
