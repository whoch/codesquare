package com.bit.codesquare.controller.comment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.codesquare.controller.Board.BoardRestController;
import com.bit.codesquare.dto.comment.LectureReview;
import com.bit.codesquare.mapper.comment.CommentMapper;

@Controller
@RequestMapping(value= {"learn/Comment","Board/Comment"})
public class CommentController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardRestController.class);
	
	@Autowired
	CommentMapper cMapper;
	
	@RequestMapping(value="/insert" , method=RequestMethod.POST)
	@ResponseBody
	private int reviewInsert(@ModelAttribute LectureReview lReview)throws Exception {
		logger.info("댓글입력 입장:");
		int result=cMapper.insertReview(lReview);
		cMapper.updateLike(lReview);
		
		logger.info("result:"+result);	
		return result;
	}
	
	
}
