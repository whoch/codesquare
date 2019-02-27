package com.bit.codesquare.controller.admin;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
import com.bit.codesquare.mapper.board.BoardMapper;
import com.bit.codesquare.mapper.lecture.LectureMapper;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.service.NewService;
import com.bit.codesquare.util.CodesquareUtil;
import com.bit.codesquare.util.ComparableDateTime;

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

	@Autowired
	BoardMapper boardMapper;
	
	@Value("${lectureIntro.savepath.directory}")
	String lectureIntropath;

	LectureIntroContent liContent;
	List<LectureIntroContent> licList = new ArrayList<LectureIntroContent>();
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

	/*
	 * 관리자 기본페이지인 Dashboard페이지 컨트롤러 영역
	 */  
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

//			List<UserTodoList> uList = adminMapper.getTodoList(member.getUserId());
			List<UserTodoList> uList2 = (List<UserTodoList>) cUtil.getDateTimeCompareObject(adminMapper.getTodoList(member.getUserId()), "yyyy-mm-dd");

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

	/*
	 * 관리자 회원관리 페이지 컨트롤러 영역
	 */
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
			map = new HashMap<String, Object>();

			mav.addObject("authorInfo", adminMapper.getAllAuthorInfo());
			mav.addObject("restrictInfo", adminMapper.getAllRestrictInfo());
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
	@RequestMapping(value = "member/sort", method = RequestMethod.GET)
	public ModelAndView sortMemberInfo(@RequestParam("tag") String tag, @ModelAttribute("cri") Criteria cri)
			throws IOException {
		try {
			List<Member> mList = new ArrayList<Member>();
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tName", "UserInfo");
			pageMaker.setTotalCount(adminMapper.countPaging(map));
			mav.addObject("pageMaker", pageMaker);

			switch (tag) {
			case "UserId":
				mList = adminMapper.getSortIdMemberInfo(cri);
				break;
			case "Point":
				mList = adminMapper.getSortPointMemberInfo(cri);
				break;
			case "BanCount":
				mList = adminMapper.getSortBanMemberInfo(cri);
				break;
			default:
				logger.info("error");
			}
			mav.addObject("mList", mList);
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
		member = new Member();
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
		result = 0;
		try {
			result = adminMapper.deleteUserInfo(userId);
			logger.info("delete result" + result);
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
		result = 0;
		try {
			logger.info("입장완료" + member.toString());
			result = adminMapper.updateUserInfo(member);
			logger.info("update result" + result);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	// 회원들 등급 변경 레스트 컨트롤러
	@RequestMapping(value = "member/authors", method = RequestMethod.PUT)
	@ResponseBody
	public int updateUsersAuthor(@RequestBody ArrayList<SelectBasket> aList) throws IOException {
		result = 0;
		try {
			logger.info("authorinfo: " + aList.toString());
			result = adminMapper.updateUsersAuthor(aList);
			logger.info("update result: " + result);
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
		result = 0;
		try {
			logger.info("입장완료" + rList.toString());
			result = adminMapper.updateUsersRestrict(rList);
			logger.info("update result: " + result);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	
	/*
	 * 관리자 강의관리 페이지 컨트롤러 영역
	 */
	// 강의관리 이동 컨트롤러
	@RequestMapping(value = "lecture", method = RequestMethod.GET)
	public ModelAndView adminLectureControlPage(@ModelAttribute("cri") Criteria cri,Principal pricipal, HttpSession session, Authentication auth,HttpServletResponse response) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		PageMaker pageMaker = new PageMaker();
//		cUtil.getSession(auth, session);
		try {
			cri.setPerPageNum(5);
			map = new HashMap<String, Object>();
			map.put("tName", "v_LectureIntroContent");
			licList = adminMapper.getAllLectureList(cri);

			pageMaker.setCri(cri);
			pageMaker.setTotalCount(adminMapper.countPaging(map));

			list = adminMapper.getAllLectureTag();

			for (LectureIntroContent l : licList) {
				String thumbPath = lectureIntropath;
				thumbPath += cUtil.getPath(l.getId(), l.getChangedName(), l.getExtension());
				l.setThumbnailPath(thumbPath);
			}

		} catch (Exception e) {

		}
		mav.addObject("pageMaker", pageMaker);
		mav.addObject("tagList", list); // 언어 태그 객체
		mav.addObject("lectureList", licList);// 강의 목록
		mav.setViewName("admin/lectureControl");
		return mav;
	}

	// 강의 검색 컨트롤러
	@RequestMapping(value = "lecture/search", method = RequestMethod.GET)
	public ModelAndView searchLectureIntro(@RequestParam("content") String keyword, @ModelAttribute("cri") Criteria cri)
			throws IOException {
		PageMaker pageMaker = new PageMaker();
		try {
			licList = new ArrayList<LectureIntroContent>();
			cri.setPerPageNum(5);
			pageMaker.setCri(cri);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tName", "v_LectureIntroContent");
			map.put("keyword", keyword);
			pageMaker.setTotalCount(adminMapper.countPaging(map));
			mav.addObject("pageMaker", pageMaker);
			map.put("cri", cri);
			licList = adminMapper.getSearchLectureIntroConent(map);
			for (LectureIntroContent l : licList) {
				String thumbPath = lectureIntropath;
				thumbPath += cUtil.getPath(l.getId(), l.getChangedName(), l.getExtension());
				l.setThumbnailPath(thumbPath);
				;
			}
			mav.addObject("lectureList", licList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mav.setViewName("admin/lectureControl");
		return mav;
	}

	// 태그별 강의소개글 검색 컨트롤러
	@RequestMapping(value = "lecture/sort", method = RequestMethod.GET)
	public ModelAndView sortLectureIntroInfo(@RequestParam("langKind") String tag, @ModelAttribute("cri") Criteria cri)
			throws IOException {
		PageMaker pageMaker = new PageMaker();

		try {
			licList = new ArrayList<LectureIntroContent>();
			cri.setPerPageNum(5);
			pageMaker.setCri(cri);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tName", "v_LectureIntroContent");
			map.put("tag", tag);
			pageMaker.setTotalCount(adminMapper.countPaging(map));
			mav.addObject("pageMaker", pageMaker);
			map.put("cri", cri);
			licList = adminMapper.getSortbyTagLectureIntro(map);
			for (LectureIntroContent l : licList) {
				String thumbPath = lectureIntropath;
				thumbPath += cUtil.getPath(l.getId(), l.getChangedName(), l.getExtension());
				l.setThumbnailPath(thumbPath);
				;
			}
			mav.addObject("lectureList", licList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mav.setViewName("admin/lectureControl");
		return mav;
	}
	// 강의 소개글 상세보기 이동 컨트롤러
	@RequestMapping(value = "lecture/intro/{boardId}", method = RequestMethod.GET)
	public ModelAndView getLectureIntroPage(@PathVariable("boardId")int boardId, Principal principal, HttpSession session, Authentication auth) throws IOException {
//		cUtil.getSession(auth, session);
		try {
			lectureIntro= lectureMapper.getLecture(boardId);
			String thumbPath=lectureIntropath;
			lectureIntro.setThumbnailPath(thumbPath+=cUtil.getPath(lectureIntro.getId(), lectureIntro.getChangedName(), lectureIntro.getExtension()));
			
			mav.addObject("lectureList",lectureMapper.getLecutreList(boardId)); //강의목록 불러오기
			recommandList= lectureMapper.getRecommandLecture(lectureIntro.getId());
			lectureMapper.updateLectureHit(boardId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("lecture",lectureIntro);	//강좌 불러오기
		mav.addObject("recommandList",recommandList); //추천강의 리스트
		mav.setViewName("admin/lectureIntro");
		return mav;
	}
	// 강의 소개글 수정하기 이동 컨트롤러
	@RequestMapping(value = "lecture/intro/post/{boardId}", method = RequestMethod.GET)
	public ModelAndView modifyLectureIntroPage(@PathVariable("boardId")int boardId, Principal principal, HttpSession session, Authentication auth) throws IOException {
//		cUtil.getSession(auth, session);
		try {
			lectureIntro= lectureMapper.getLecture(boardId);
			String thumbPath=lectureIntropath;
			lectureIntro.setThumbnailPath(thumbPath+=cUtil.getPath(lectureIntro.getId(), lectureIntro.getChangedName(), lectureIntro.getExtension()));
			
			mav.addObject("lectureList",lectureMapper.getLecutreList(boardId)); //강의목록 불러오기
			recommandList= lectureMapper.getRecommandLecture(lectureIntro.getId());
			lectureMapper.updateLectureHit(boardId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("lecture",lectureIntro);	//강좌 불러오기
		mav.setViewName("admin/lectureIntroModify");
		return mav;
	}
	
	
	//강좌보기 페이지 이동 컨트롤러
	@RequestMapping("intro/{parentId}/course/{boardId}")
	public ModelAndView firstlectureView(@PathVariable("parentId") int parentId,@PathVariable("boardId") int boardId, Principal pricipal, HttpSession session, Authentication auth,HttpServletResponse response){
//		cUtil.getSession(auth, session);
		try {
			member= memberMapper.getUser(pricipal.getName());
			map= new HashMap<String, Object>();
			Map<String,Object> vLogInfo=new HashMap<String, Object>();
			map.put("parentId", parentId);
			map.put("boardId", boardId);
			map.put("userId", member.getUserId());
			
			lectureMapper.saveLearningLog(map);
			
			vLogInfo=lectureMapper.getLearningLog(map);
			logger.info("vLogInfo: "+vLogInfo.toString());
			String noteContent=lectureMapper.getLectureHandWriting(map);
			lecture= lectureMapper.getLectureContent(boardId);
			String status=lectureMapper.getLectureStatus(boardId);
			lecture.setLectureStatus(status);
			mav.addObject("vLogInfo",vLogInfo);
			mav.addObject("lectureList",lectureMapper.getLecutreList(parentId)); //강의목록 불러오기
			mav.addObject("lecture",lecture);//강의정보
			mav.addObject("noteContent",noteContent);//필기정보
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("admin/adminLectureView");
		return mav; 
	}
	// 강의 리스트 삭제 레스트 컨트롤러
	@RequestMapping(value = "/lecture", method = RequestMethod.DELETE)
	@ResponseBody
	public int deleteLectureList(@RequestBody List<Map<String,Object>> rList) throws IOException {
		result = 0;
		try {
			result = adminMapper.deleteLectureList(rList);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	// 강의 리스트 보류상태 변경 레스트 컨트롤러
	@RequestMapping(value = "/lecture/pending", method = RequestMethod.PUT)
	@ResponseBody
	public int updateLecturePendingStatus(@RequestBody List<Map<String,Object>> list) throws IOException {
		result = 0;
		try {
			logger.info("결과값:"+list.toString());
			result = adminMapper.updateLecturePendingStatus(list);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	// 금칙어 관리 이동 컨트롤러
	@RequestMapping(value = "keyword", method = RequestMethod.GET)
	public ModelAndView adminKeywordControlPage(Model model) {
		List<Map<String,Object>> kList=new ArrayList<Map<String,Object>>();
		try {
			 kList=adminMapper.getAllBlackKeyword();
			 logger.info("hihihi"+kList.toString());
			 mav.addObject("keywordList",kList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		mav.setViewName("admin/keywordControl");
		return mav;
	}
	// 금칙어 삭제 레스트 컨트롤러
	@RequestMapping(value ="delete/keyword", method = RequestMethod.DELETE)
	@ResponseBody
	public int deleteBlackKeyword(@RequestBody List<Map<String,Object>> list) {
		result=0;
		try {
			logger.info("hi 삭제컨트롤러");
			result=adminMapper.deleteBlackKeyword(list);
			logger.info(result+"삭제결과");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	// 금칙어 추가 레스트 컨트롤러
	@RequestMapping(value = "insert/keyword", method = RequestMethod.POST)
	@ResponseBody
	public int insertBlackKeyword(@RequestBody List<String> list) {
		result=0;
		try {
			result=adminMapper.insertBlackKeyword(list);
			logger.info(result+"입력결과");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}

	

	// 게시판 관리 이동 컨트롤러
	@RequestMapping(value = "board", method = RequestMethod.GET)
	public ModelAndView adminBoardControlPage(@ModelAttribute("cri") Criteria cri,Principal pricipal, HttpSession session, Authentication auth) {
		List<Board> list = new ArrayList<Board>();
		PageMaker pageMaker = new PageMaker();
		List<Map<String,Object>> bkList=new ArrayList<Map<String,Object>>();
		try {
			bkList=boardMapper.getBoardKindList();
			mav.addObject("boardKindList",bkList);
			bkList=boardMapper.getBookmarkBoardKindList();
			mav.addObject("bBKList",bkList);
			cri.setPerPageNum(10);
			map = new HashMap<String, Object>();
			map.put("tName", "Board");
			map.put("cri", cri);
			map.put("boardKindId", "ComFr");
			
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(adminMapper.countPaging(map));
			list =boardMapper.getAllBoardList(map);
			cUtil.getDateTimeCompareObject(list, "yyyy-mm-dd");
			logger.info(list.toString());

		} catch (Exception e) {

		}
		
		mav.addObject("pageMaker", pageMaker);
		mav.addObject("boardList", list);// 강의 목록
		mav.setViewName("admin/boardControl");
		return mav;
	}
}
