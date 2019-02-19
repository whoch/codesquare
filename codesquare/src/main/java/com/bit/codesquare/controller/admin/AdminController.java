package com.bit.codesquare.controller.admin;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bit.codesquare.controller.board.BoardRestController;
import com.bit.codesquare.dto.admin.RepotedInfo;
import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.admin.AdminMapper;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
@RequestMapping(value = "admin")
@ResponseStatus(HttpStatus.OK)
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(BoardRestController.class);
	ModelAndView mav = new ModelAndView();
	@Autowired
	MemberMapper memberMapper;

	@Autowired
	AdminMapper adminMapper;
	
	List<RepotedInfo> list;
	
	Member member;
	CodesquareUtil cUtil=new CodesquareUtil();
	
	//(default) Dashboard 이동 컨트롤러
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView adminDashboard(Principal principal, HttpSession session, Authentication auth) {
		cUtil.getSession(auth, session);
		member= memberMapper.getUser(principal.getName());
		RepotedInfo rInfo;
		try {
			list=new ArrayList<RepotedInfo>();
			list=adminMapper.getFiveRepotedInfo();
			mav.addObject("repotedList",list);
			
//			list=adminMapper.getTodoList(member.getUserId());			
//			mav.addObject("todolist",list);
			
		}catch(Exception e) {
			
		}
		
		mav.setViewName("admin/dashboard");
		return mav;
	}
	// 강좌리스트 페이지 이동 컨트롤러 (default)
	@RequestMapping(value="board",method = RequestMethod.GET)
	public ModelAndView adminBoardControlPage(Model model) {
		
		mav.setViewName("admin/boardControl");
		return mav;
	}
	// 강좌리스트 페이지 이동 컨트롤러 (default)
	@RequestMapping(value="member",method = RequestMethod.GET)
	public ModelAndView adminMemberControlPage(Model model) {
		
		mav.setViewName("admin/memberControl");
		return mav;
	}
	// 강좌리스트 페이지 이동 컨트롤러 (default)
	@RequestMapping(value="lecture",method = RequestMethod.GET)
	public ModelAndView adminLectureControlPage(Model model) {
		
		mav.setViewName("admin/lectureControl");
		return mav;
	}
	// 강좌리스트 페이지 이동 컨트롤러 (default)
	@RequestMapping(value="keyword",method = RequestMethod.GET)
	public ModelAndView adminKeywordControlPage(Model model) {
		
		mav.setViewName("admin/keywordControl");
		return mav;
	}

	// 강의소개글 썸네일 등록용 컨트롤러
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadThumbnail(@RequestPart("thumbnail") MultipartFile tFile, @RequestParam("id") int id,
			Principal principal) throws IOException {

		return "";
	}
}
