package com.bit.codesquare.controller.lecture;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.bit.codesquare.dto.lecture.LectureIntroContent;
import com.bit.codesquare.mapper.board.BoardMapper;
import com.bit.codesquare.mapper.lecture.LectureMapper;
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
	
	@Value("${lectureIntro.savepath.directory}")
	String lectureIntropath;
	
	@Value("${user.thumbnail.directory}")
	String reviewPath;
	
	CodesquareUtil cUtil=new CodesquareUtil();
	Board board;
	LectureIntroContent liContent;
	List<LectureIntroContent> licList= new ArrayList<LectureIntroContent>();
	Lecture lecture;
	List<Board> recommandList;
	List<LectureReview> lrList;
	
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
			lecture= lectureMapper.getLecture(id);
			
			String thumbPath=lectureIntropath;
			lecture.setThumbnailPath(thumbPath+=cUtil.getPath(lecture.getId(), lecture.getChangedName(), lecture.getExtension()));
			
			mav.addObject("lectureList",lectureMapper.getLecutreList(id)); //강의목록 불러오기
			recommandList= lectureMapper.getRecommandLecture(lecture.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("lecture",lecture);	//강좌 불러오기
		mav.addObject("recommandList",recommandList); //추천강의 리스트
		mav.setViewName("lecture/lecture");
		return mav; 
	}
	@RequestMapping("view/{id}")
	public ModelAndView lectureView(@PathVariable("id") int id) {
		
		mav.setViewName("lecture/lectureView");
		return mav; 
	}
	
	
	
	
	
}
