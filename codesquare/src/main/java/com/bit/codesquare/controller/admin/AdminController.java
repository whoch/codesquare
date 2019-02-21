package com.bit.codesquare.controller.admin;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bit.codesquare.controller.board.BoardRestController;
import com.bit.codesquare.dto.admin.AdminMemo;
import com.bit.codesquare.dto.admin.ReportedUserInfo;
import com.bit.codesquare.dto.admin.SelectBasket;
import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.comment.LectureReview;
import com.bit.codesquare.dto.lecture.Lecture;
import com.bit.codesquare.dto.lecture.LectureIntro;
import com.bit.codesquare.dto.lecture.LectureIntroContent;
import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.dto.paging.Criteria;
import com.bit.codesquare.dto.paging.PageMaker;
import com.bit.codesquare.dto.planner.UserTodoList;
import com.bit.codesquare.mapper.admin.AdminMapper;
import com.bit.codesquare.mapper.lecture.LectureMapper;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.service.NewService;
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

	@Autowired
	NewService newService;
	
	@Autowired
	LectureMapper lectureMapper;
	
	
	@Value("${lectureIntro.savepath.directory}")
	String lectureIntropath;
	
	LectureIntroContent liContent;
	List<LectureIntroContent> licList= new ArrayList<LectureIntroContent>();
	LectureIntro lectureIntro;
	List<Board> recommandList;
	List<LectureReview> lrList;
	Lecture lecture;

	List<ReportedUserInfo> list;

	Member member;
	CodesquareUtil cUtil = new CodesquareUtil();
	Map<String, Object> map;
	int result;

	LocalDateTime ldt;

	// (default) Dashboard 이동 컨트롤러
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView adminDashboard(Principal principal, HttpSession session, Authentication auth) {
		cUtil.getSession(auth, session);
		member = memberMapper.getUser(principal.getName());
		ReportedUserInfo rInfo;
		try {
			list = new ArrayList<ReportedUserInfo>();
			list = adminMapper.getFiveRepotedInfo();
			mav.addObject("repotedList", list);

			List<UserTodoList> uList = adminMapper.getTodoList(member.getUserId());
			List<UserTodoList> uList2 = (List<UserTodoList>) cUtil.getDateTimeCompareObject(uList, "yyyy-mm-dd");

			AdminMemo aMemo = adminMapper.getAdminMemo();
			ldt = (LocalDateTime) aMemo.getWriteDate();
			aMemo.setWriteDateFomat(cUtil.compareDateTime(ldt, "yyyy-mm-dd"));
			List<ReportedUserInfo> ruiList = adminMapper.getThreeDayRepotedUserRanking();

			mav.addObject("ruiList", ruiList);
			mav.addObject("adminMemo", aMemo);
			mav.addObject("todolist", uList2);
			mav.addObject("wsuMemberCount", adminMapper.getWeekSignupMember());
			mav.addObject("insCount", adminMapper.getInstroduction());
			mav.addObject("recentLectureCount", adminMapper.getRecentLecture());
			mav.addObject("newPostCount", adminMapper.getNewPost());

		} catch (Exception e) {

		}

		mav.setViewName("admin/dashboard");
		return mav;
	}

	// 관리자 TODOLIST추가
	@RequestMapping(value = "todolist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTodoList(@RequestParam("content") String content, Principal principal)
			throws IOException {
		member = memberMapper.getUser(principal.getName());
		ldt = LocalDateTime.now();
		try {
			map = new HashMap<String, Object>();
			map.put("id", 0);
			map.put("writedate", ldt);
			map.put("content", content);
			map.put("userId", member.getUserId());
			result = adminMapper.insertTodoList(map);
			ldt = (LocalDateTime) map.get("writedate");
			String date = cUtil.compareDateTime(ldt, "yyyy-mm-dd");
			logger.info("hansolhansol" + map.toString());
			map.put("writeDateFomat", date);
			return map;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	// 관리자 TODOLIST삭제
	@RequestMapping(value = "todolist", method = RequestMethod.DELETE)
	@ResponseBody
	public int deleteTodoList(@RequestParam("id") int id) throws IOException {
		try {
			result = adminMapper.deleteTodoList(id);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	// 관리자 메모 저장
	@RequestMapping(value = "memo", method = RequestMethod.PUT)
	@ResponseBody
	public int updateAdminMemo(@RequestParam("content") String content, Principal principal) throws IOException {
		try {

			map = new HashMap<String, Object>();
			member = memberMapper.getUser(principal.getName());
			map.put("content", content);
			map.put("author", member.getUserId());

			result = adminMapper.updateAdminMemo(map);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	// 회원관리 페이지 이동 컨트롤러
	@RequestMapping(value = "member", method = RequestMethod.GET)
	public ModelAndView adminMemberControlPage(@ModelAttribute("cri") Criteria cri) {
		try {
			PageMaker pageMaker = new PageMaker();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tName", "UserInfo");
			List<Member> mList = adminMapper.getAllMemberInfo(cri);

			pageMaker.setCri(cri);
			pageMaker.setTotalCount(adminMapper.countPaging(map));
			map= new HashMap<String, Object>();

			mav.addObject("authorInfo",adminMapper.getAllAuthorInfo());
			mav.addObject("restrictInfo",adminMapper.getAllRestrictInfo());
			mav.addObject("pageMaker", pageMaker);
			mav.addObject("mList", mList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mav.setViewName("admin/memberControl");
		return mav;
	}

	// 회원 아이디및 닉네임 검색 컨트롤러
	@RequestMapping(value = "member/search", method = RequestMethod.GET)
	public ModelAndView getSearchMember(@RequestParam("content") String content, @ModelAttribute("cri") Criteria cri)
			throws IOException {
		try {

			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tName", "UserInfo");
			map.put("content", content);
			List<Member> mList = adminMapper.getSearchMember(content);
			int result = adminMapper.countPaging(map);
			pageMaker.setTotalCount(result);
			mav.addObject("pageMaker", pageMaker);
			// 1.회원정보 조회
			mav.addObject("mList", mList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mav.setViewName("admin/memberControl");
		return mav;
	}

	// 태그별 회원 정렬 컨트롤러
	@RequestMapping(value="member/sort", method = RequestMethod.GET)
		public ModelAndView sortMemberInfo(@RequestParam("tag") String tag,@ModelAttribute("cri") Criteria cri) throws IOException {
			try {
				List<Member> mList=new ArrayList<Member>();
				PageMaker pageMaker = new PageMaker();
				pageMaker.setCri(cri);
				Map<String, Object> map= new HashMap<String,Object>();
				map.put("tName", "UserInfo");
				pageMaker.setTotalCount(adminMapper.countPaging(map));
				mav.addObject("pageMaker", pageMaker);
				
				switch(tag) {
					case "UserId":
						mList=adminMapper.getSortIdMemberInfo(cri);
						break;
					case "Point":
						mList=adminMapper.getSortPointMemberInfo(cri);
						break;
					case "BanCount":
						mList=adminMapper.getSortBanMemberInfo(cri);
						break;
					default:
						logger.info("error");
				}
				mav.addObject("mList",mList);
			} catch (Exception e) {
				// TODO: handle exception
			}
			mav.setViewName("admin/memberControl");
			return mav;
		}
	// 회원정보 상세보기 컨트롤러
	@RequestMapping(value = "/member/detail", method = RequestMethod.GET)
	@ResponseBody
	public Member getSelectMemberInfo(@RequestParam("userId") String userId) throws IOException {
		member=new Member();
		try {
			member = adminMapper.getSelectMemberInfo(userId);
			member.setProfileImagePath(cUtil.getPath(member.getUserId(), member.getProfileImagePath()));
			logger.info(member.toString());
			return member;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return member;
	}
	// 회원 추방시키기 레스트 컨트롤러
	@RequestMapping(value = "/member/out", method = RequestMethod.DELETE)
	@ResponseBody
	public int deleteUserInfo(@RequestParam("userId") String userId) throws IOException {
		result=0;
		try {
			result = adminMapper.deleteUserInfo(userId);
			logger.info("delete result"+result);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	// 회원 정보 변경 레스트 컨트롤러
	@RequestMapping(value = "/member/detail", method = RequestMethod.PUT)
	@ResponseBody
	public int updateUserInfo(@ModelAttribute Member member) throws IOException {
		result=0;
		try {
			logger.info("입장완료"+member.toString());
			result = adminMapper.updateUserInfo(member);
			logger.info("update result"+result);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	// 회원들 등급 변경 레스트 컨트롤러
	@RequestMapping(value="member/authors", method = RequestMethod.PUT)
	@ResponseBody
	public int updateUsersAuthor(@RequestBody ArrayList<SelectBasket> aList) throws IOException {
		result=0;
		try {
			logger.info("authorinfo: "+aList.toString());
			result = adminMapper.updateUsersAuthor(aList);
			logger.info("update result: "+result);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	// 회원 제한 정보 변경 레스트 컨트롤러
	@RequestMapping(value = "/member/restricts", method = RequestMethod.PUT)
	@ResponseBody
	public int updateUserRestrict(@RequestBody List<SelectBasket> rList) throws IOException {
		result=0;
		try {
			logger.info("입장완료"+rList.toString());
			result = adminMapper.updateUsersRestrict(rList);
			logger.info("update result: "+result);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	


	// 강의관리 이동 컨트롤러
	@RequestMapping(value = "lecture", method = RequestMethod.GET)
	public ModelAndView adminLectureControlPage(Model model) {
		List<?> list=new ArrayList<>();
		try {
			list=lectureMapper.getAllLectureTag();
			licList= lectureMapper.getAllLecture();
			for(LectureIntroContent l: licList) {
				String thumbPath=lectureIntropath;
				thumbPath+=cUtil.getPath(l.getId(), l.getChangedName(),l.getExtension());
				l.setThumbnailPath(thumbPath);
			}
			
		}catch(Exception e) {
			
		}
		mav.addObject("tagList",list); //언어 태그 객체
		mav.addObject("lectureList",licList);//강의 목록
		mav.setViewName("admin/lectureControl");
		return mav;
	}
	

	// 게시판 관리 이동 컨트롤러
	@RequestMapping(value = "board", method = RequestMethod.GET)
	public ModelAndView adminBoardControlPage(Model model) {

		mav.setViewName("admin/boardControl");
		return mav;
	}

	// 금칙어 관리 이동 컨트롤러
	@RequestMapping(value = "keyword", method = RequestMethod.GET)
	public ModelAndView adminKeywordControlPage(Model model) {

		mav.setViewName("admin/keywordControl");
		return mav;
	}

}
