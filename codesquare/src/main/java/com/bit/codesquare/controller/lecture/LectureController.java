 package com.bit.codesquare.controller.lecture;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bit.codesquare.controller.board.BoardRestController;
import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.comment.LectureReview;
import com.bit.codesquare.dto.lecture.LearningLogInfo;
import com.bit.codesquare.dto.lecture.Lecture;
import com.bit.codesquare.dto.lecture.LectureIntro;
import com.bit.codesquare.dto.lecture.LectureIntroContent;
import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.board.BoardMapper;
import com.bit.codesquare.mapper.lecture.LectureMapper;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
@RequestMapping(value="/learn")
public class LectureController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardRestController.class);
	ModelAndView mav=new ModelAndView();
	@Autowired
	BoardMapper boardMapper;
	@Autowired
	LectureMapper lectureMapper;
	@Autowired
	MemberMapper memberMapper;
	
	
	LearningLogInfo llInfo;
	Member member;
	
	@Value("${lectureIntro.savepath.directory}")
	String lectureIntropath;
	
	@Value("${user.thumbnail.directory}")
	String userThumbPath;
	
	CodesquareUtil cUtil=new CodesquareUtil();
	Board board;
	LectureIntroContent liContent;
	List<LectureIntroContent> licList= new ArrayList<LectureIntroContent>();
	LectureIntro lectureIntro;
	List<Board> recommandList;
	List<LectureReview> lrList;
	Lecture lecture;
	Map<String, Object> map ;
	
	//강좌리스트 페이지 이동 컨트롤러 (default)
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView learnList(Model model) {
		try {
			licList= lectureMapper.getAllLecture();
			for(LectureIntroContent l: licList) {
				String thumbPath=lectureIntropath;
				thumbPath+=cUtil.getPath(l.getId(), l.getChangedName(),l.getExtension());
				l.setThumbnailPath(thumbPath);
			}
		}catch(Exception e) {
			
		}
		mav.setViewName("lecture/lectureList");
		mav.addObject("lectureList",licList);
		return mav; 
	}
	//강좌 소개 페이지 이동 컨트롤러
	@RequestMapping("intro/{id}")
	public ModelAndView lectureIntroView(@PathVariable("id") int id, Principal pricipal, HttpSession session, Authentication auth) {
		cUtil.getSession(auth, session);
		try {
			lectureIntro= lectureMapper.getLecture(id);
			String thumbPath=lectureIntropath;
			lectureIntro.setThumbnailPath(thumbPath+=cUtil.getPath(lectureIntro.getId(), lectureIntro.getChangedName(), lectureIntro.getExtension()));
			
			mav.addObject("lectureList",lectureMapper.getLecutreList(id)); //강의목록 불러오기
			recommandList= lectureMapper.getRecommandLecture(lectureIntro.getId());
			lectureMapper.updateLectureHit(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("lecture",lectureIntro);	//강좌 불러오기
		mav.addObject("recommandList",recommandList); //추천강의 리스트
		mav.setViewName("lecture/lecture");
		return mav; 
	}
	//강의소개 등록 페이지 이동 컨트롤러
	@RequestMapping("intro")
	public ModelAndView lectureIntroWrite(Principal pricipal, HttpSession session, Authentication auth) {
		cUtil.getSession(auth, session);
		mav.setViewName("lecture/lectureIntroWritePage");
		return mav; 
	}
	
	
	
	
	//강좌보기 페이지 이동 컨트롤러
	@RequestMapping("intro/{parentId}/course/{boardId}")
	public ModelAndView firstlectureView(@PathVariable("parentId") int parentId,@PathVariable("boardId") int boardId, Principal pricipal, HttpSession session, Authentication auth){
		cUtil.getSession(auth, session);
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
		mav.setViewName("lecture/lectureView");
		return mav; 
	}
	//이동할 강의페이지 구하기
	@RequestMapping(value="get/boardId", method=RequestMethod.GET)
	@ResponseBody
	public int getBoardId(@RequestParam int parentId, @RequestParam String userId) {
		int result=0;
		try {
			map= new HashMap<String, Object>();
			map.put("parentId", parentId);
			map.put("userId", userId);
			result=lectureMapper.getLectureBoardIdno(map);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return result;
	}
	
	
	
	
	/*//강좌보기 페이지 이동 컨트롤러(인트로쪽이 아닌 강좌 내에서 이동)
	@RequestMapping("intro/{parentId}/course/{boardId}")
	public ModelAndView lectureView(@PathVariable("parentId") int parentId,@PathVariable("boardId") int boardId, Principal pricipal, HttpSession session, Authentication auth){
		cUtil.getSession(auth, session);
		try {
			member= memberMapper.getUser(pricipal.getName());
			
			String thumbPath=userThumbPath;
			map= new HashMap<String, Object>();
			map.put("parentId", parentId);
			map.put("boardId", boardId);
			map.put("userId", member.getUserId());
			lectureMapper.saveLearningLog(map);
			String noteContent=lectureMapper.getLectureHandWriting(map);
			lecture= lectureMapper.getLectureContent(boardId);
			
			member.setProfileImagePath(thumbPath+=cUtil.getPath(member.getUserId(), member.getProfileImagePath()));
			
			String status=lectureMapper.getLectureStatus(boardId);
			lecture.setLectureStatus(status);
			mav.addObject("lectureList",lectureMapper.getLecutreList(boardId)); //강의목록 불러오기
			mav.addObject("lecture",lecture);//강의정보
			mav.addObject("noteContent",noteContent);//필기정보
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("lecture/lectureView");
		return mav; 
	}
	*/
	
	
	
	
	
	//찜하기 
	@RequestMapping(value="bookmark", method=RequestMethod.POST)
	@ResponseBody
	public int addBookmark(@RequestParam int boardId, @RequestParam String userId) {
		
		int result=0;
		try {
			map=new HashMap<String,Object>();
			map.put("boardId", boardId);
			map.put("userId", userId);
			result = lectureMapper.insertBookmark(map);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return result;
	}
	
	
	//필기 정보 가져오기
	@RequestMapping(value="/note" , method=RequestMethod.GET)
	@ResponseBody
	private String getNoteContent(@RequestParam("boardId") int boardId, @RequestParam("userId") String userId)throws Exception {
		
		map= new HashMap<String, Object>();
		map.put("boardId", boardId);
		map.put("userId", userId);
		String noteContent=lectureMapper.getLectureHandWriting(map);
		
		return noteContent;
	}
	
	//필기 정보 저장
	@RequestMapping(value="note" , method=RequestMethod.PUT)
	@ResponseBody
	private int saveNoteContent(@RequestParam("boardId") int boardId, @RequestParam("userId") String userId, @RequestParam("content")String content)throws Exception {
		map= new HashMap<String, Object>();
		map.put("boardId", boardId);
		map.put("userId", userId);
		map.put("content", content);
		int noteContent=lectureMapper.saveNoteContent(map);
		
		return noteContent;
	}
	
	//강좌 등록 수정 페이지 이동 컨트롤러
	@RequestMapping(value="intro/{parentId}/course/post/{boardId}",method=RequestMethod.GET)
	public ModelAndView learnModifyViewPage(@PathVariable("boardId") int boardId, HttpSession session, Authentication auth) {
		cUtil.getSession(auth, session);
		try {
			lecture=lectureMapper.getLectureContent(boardId);
			lectureIntro=lectureMapper.getLecture(lecture.getParentId());
			mav.addObject("lecture",lecture);
			mav.addObject("parentTitle",lectureIntro.getTitle());
		}catch(Exception e) {
			
		}
		mav.setViewName("lecture/lectureModifyPage");
		return mav; 
	}
	//강좌 등록 페이지 이동 컨트롤러
	@RequestMapping(value="intro/{parentId}/course",method=RequestMethod.GET)
	public ModelAndView learnWriteViewPage(@PathVariable("parentId") int parentId, HttpSession session, Authentication auth) {
		cUtil.getSession(auth, session);
		try {
			mav.addObject("parentId", parentId);
		}catch(Exception e) {

		}
		mav.setViewName("lecture/lectureWritePage");
		return mav; 
	}
	
	//강좌 등록 레스트 컨트롤러
	@RequestMapping(value="/course",method=RequestMethod.POST)
	@ResponseBody
	public int learnRegist(@ModelAttribute Lecture lt) {
		int result=0;
		try{
			result=lectureMapper.insertLectureContent(lt);
			return lt.getId();
		}catch(Exception e) {
			
		}
		return result; 
	}
	//강좌 내용 변경 레스트 컨트롤러
	@RequestMapping(value="/course",method=RequestMethod.PUT)
	@ResponseBody
	public int learnUpdate(@ModelAttribute Lecture lt) {
		int result=0;
		try{
			result=lectureMapper.updateLectureContent(lt);
			return result;
		}catch(Exception e) {
			
		}
		return result; 
	}
	
	//강좌 영상 저장 컨트롤러
	@RequestMapping(value="/video",method=RequestMethod.POST)
	@ResponseBody
	public int saveLectureVideo(@RequestParam String videoUrl,@RequestParam int id) {
		int result=0;
		try{
			map=new HashMap<String,Object>();
			map.put("videoUrl", videoUrl);
			map.put("id",id);
			result = lectureMapper.saveLectureVideo(map);
			return result;
		}catch(Exception e) {
			
		}
		return result; 
	}
	//강의소개글 조회수 증가
	@RequestMapping(value="/course/update",method=RequestMethod.PUT)
	@ResponseBody
	public int saveLectureVideo(@RequestParam int boardId) {
		int result=0;
		try{
			result = lectureMapper.updateLectureHit(boardId);
			return result;
		}catch(Exception e) {
			
		}
		return result; 
	}
	//강의 마지막으로 본 영상 시간과 총 재생시간 저장
	@RequestMapping(value="/learninglog/update",method=RequestMethod.PUT)
	@ResponseBody
	public int updateLearningLog(@ModelAttribute LearningLogInfo lLogInfo ) {
		int result=0;
		try{
			LearningLogInfo l=new LearningLogInfo();
			l=lLogInfo;
			l.setIsClear(cUtil.isClearcalc(l.getCurrentTime(), l.getDuration()));
			logger.info("lLogInfo: "+l.toString());
			result = lectureMapper.updateLearningLogInfo(l);
			logger.info("결과"+result);
			return result;
		}catch(Exception e) {
		}
		return result; 
	}
	
	
	
	
	
}
