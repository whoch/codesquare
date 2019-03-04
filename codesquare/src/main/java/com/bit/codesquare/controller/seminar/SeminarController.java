package com.bit.codesquare.controller.seminar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.seminar.SeminarInstructorInfo;
import com.bit.codesquare.mapper.group.GroupMapper;
import com.bit.codesquare.mapper.seminar.SeminarMapper;
import com.bit.codesquare.mapper.study.StudyMapper;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
public class SeminarController {
	
	@Autowired
	SeminarMapper seminarMapper;
	
	@Autowired
	CodesquareUtil csu;
	
	@Autowired
	GroupMapper groupMapper;
	
	@Autowired
	StudyMapper studyMapper;
	@Value("${lectureimg.savepath.directory}")
	String path;
	
//	@RequestMapping("/seminarList")
//	public String seminarBoard(Model model) throws Exception {
//		model.addAttribute("list", seminarMapper.seminarList());
////		model.addAttribute("info", seminarMapper.seminarInfoList());
//		return "seminar/seminarList";
//	}
	
	@RequestMapping("/seminarList")
	public String seminarBoard(Model model) throws Exception {
		try {
			List<Board> licList = seminarMapper.seminarList();
			for (Board l:licList) {
				String thumbPath = path;
				thumbPath+=l.getId()+"/Thumbnail."+l.getExtension();
				l.setThumbnailPath(thumbPath);
			}
			model.addAttribute("list", licList);
			System.out.println("여기에요!!!!!!!!!!!!!!!!!!!!!"+licList);
		}catch (Exception e) {
		}
		return "seminar/seminarList";
	}
	
	@RequestMapping("/seminar/SmnMo/{boardId}")
	public String seminarBoardDetail(@PathVariable("boardId") int boardId, Model model, Authentication auth) throws Exception {
		String userId = auth.getName();
		String applyStatus = groupMapper.getApplyingStatus(boardId, userId);
		Board board = seminarMapper.getid(boardId);
		SeminarInstructorInfo seminar = seminarMapper.getSeminarInfo(boardId);
		LocalDate today = LocalDateTime.now().toLocalDate();
		String statusOfTheUser;
		
		if(board.getStatus()==0 || today.isAfter(seminar.getSeminarjoinenddate()) ) {
			if( applyStatus!=null && applyStatus.equals("") ) {
				statusOfTheUser="IN";
			}else {
				statusOfTheUser="END";
			}
		}else if( userId.equals(board.getUserId()) ) {
			statusOfTheUser="CLOSE";
		}else if(applyStatus!=null && applyStatus.equals("") ) {
			statusOfTheUser="WAIT";
		}else {
			statusOfTheUser="ING";
		}
		model.addAttribute("board", csu.setDateTimeCompare(board));
		model.addAttribute("seminar", seminar);
		model.addAttribute("bookmarkId", studyMapper.getBookmarkId(boardId, userId));
		model.addAttribute("status", statusOfTheUser);
		
		return "seminar/seminarWantedView";
	}
	
	
//	@GetMapping("/seminarList")
//	public ModelAndView seminarList() throws Exception {
//		List<Board> seminarList = seminarMapper.seminarList();
//		ModelAndView nextPage = new ModelAndView("seminar/seminarList");
//		nextPage.addObject("list", seminarList);
//		return nextPage;
//	}
	
//	@PostMapping("/seminarList")
//	public void create(Board board) throws Exception {
//		seminarMapper.in
//	}
//	@RequestMapping("/seminarView")
//	public String noticeView(Model model, HttpServletRequest request) throws Exception {
//		int id = Integer.parseInt(request.getParameter("id"));
//		model.addAttribute("list", seminarMapper.getid(id));
//		return "seminar/seminarList";
//	}
}
