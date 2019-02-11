package com.bit.codesquare.controller.lecture;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bit.codesquare.controller.board.BoardRestController;
import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.comment.LectureReview;
import com.bit.codesquare.dto.lecture.Lecture;
import com.bit.codesquare.dto.lecture.LectureIntro;
import com.bit.codesquare.dto.lecture.LectureIntroContent;
import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.board.BoardMapper;
import com.bit.codesquare.mapper.lecture.LectureMapper;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
@RequestMapping("/learn")
public class LectureController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardRestController.class);
	ModelAndView mav=new ModelAndView();
	@Autowired
	BoardMapper boardMapper;
	@Autowired
	LectureMapper lectureMapper;
	@Autowired
	MemberMapper memberMapper;
	
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
	
	@RequestMapping("{id}")
	public ModelAndView lectureIntroView(@PathVariable("id") int id) {
		try {
			lectureIntro= lectureMapper.getLecture(id);
			String thumbPath=lectureIntropath;
			lectureIntro.setThumbnailPath(thumbPath+=cUtil.getPath(lectureIntro.getId(), lectureIntro.getChangedName(), lectureIntro.getExtension()));
			
			mav.addObject("lectureList",lectureMapper.getLecutreList(id)); //강의목록 불러오기
			recommandList= lectureMapper.getRecommandLecture(lectureIntro.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("lecture",lectureIntro);	//강좌 불러오기
		mav.addObject("recommandList",recommandList); //추천강의 리스트
		mav.setViewName("lecture/lecture");
		return mav; 
	}
	
	@RequestMapping("view")
	public ModelAndView lectureView(@RequestParam("boardId") int boardId, @RequestParam("userId") String userId, Principal pricipal){
		try {
			String thumbPath=userThumbPath;
			map= new HashMap<String, Object>();
			map.put("boardId", boardId);
			map.put("userId", userId);
			
			int id=lectureMapper.getLectureBoardIdno(map);
			map.put("boardId", id);
			String noteContent=lectureMapper.getLectureHandWriting(map);
			lecture= lectureMapper.getLectureContent(id);
			member= memberMapper.getUser(pricipal.getName());
			member.setProfileImagePath(thumbPath+=cUtil.getPath(member.getUserId(), member.getProfileImagePath()));
			
			String status=lectureMapper.getLectureStatus(id);
			lecture.setLectureStatus(status);
			
			mav.addObject("user", member);//유저정보
			mav.addObject("lecture",lecture);//강의정보
			mav.addObject("noteContent",noteContent);//필기정보
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("lecture/lectureView");
		return mav; 
	}
	
	//찜하기 
	@RequestMapping(value="bookmark", method=RequestMethod.POST)
	@ResponseBody
	public int addBookmark(@RequestParam int boardId, @RequestParam String userId) {
		
		int result=0;
		try {
			logger.info("결과"+boardId+","+userId);
			map=new HashMap<String,Object>();
			map.put("boardId", boardId);
			map.put("userId", userId);
			result = lectureMapper.insertBookmark(map);
			logger.info("insert결과"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return result;
	}
	
	
	//필기 정보 가져오기
	@RequestMapping(value="note" , method=RequestMethod.GET)
	@ResponseBody
	private String getNoteContent(@RequestParam("boardId") int boardId, @RequestParam("userId") String userId)throws Exception {
		
		map= new HashMap<String, Object>();
		map.put("boardId", boardId);
		map.put("userId", userId);
		String noteContent=lectureMapper.getLectureHandWriting(map);
		logger.info("noteconntent"+noteContent);
		
		return noteContent;
	}
	
	//필기 정보 저장
	@RequestMapping(value="note" , method=RequestMethod.PUT)
	@ResponseBody
	private int saveNoteContent(@RequestParam("boardId") int boardId, @RequestParam("userId") String userId, @RequestParam("content")String content)throws Exception {
		logger.info("noteconntent"+content);
		map= new HashMap<String, Object>();
		map.put("boardId", boardId);
		map.put("userId", userId);
		map.put("content", content);
		int noteContent=lectureMapper.saveNoteContent(map);
		
		return noteContent;
	}
	
	
	
	
	
}
