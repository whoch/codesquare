package com.bit.codesquare.controller.comment;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.codesquare.dto.comment.ReplyDto;
import com.bit.codesquare.dto.paging.Criteria;
import com.bit.codesquare.mapper.board.FreeMapper;
import com.bit.codesquare.mapper.comment.ReplyMapper;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
@RequestMapping("/comment")
public class FreeCommentController {

	@Autowired
    ReplyMapper replyMapper;
    @Autowired
    FreeMapper freeMapper;
    
    @RequestMapping("/list") //댓글 리스트
    @ResponseBody
    private List<ReplyDto> mCommentServiceList(Model model, @RequestParam int bno, Criteria cri, String keyword, String searchOption, String boardKindId) throws Exception{
    	@SuppressWarnings("unchecked")
		List<ReplyDto> test = (List<ReplyDto>) CodesquareUtil.getDateTimeCompareObject(replyMapper.commentList(bno));
    	return test;
    }
    
    @RequestMapping("/insert") //댓글 작성 
    @ResponseBody
    private int mCommentServiceInsert(@RequestParam int bno, @RequestParam String content, HttpSession session, HttpServletRequest request) throws Exception{
    	String nickName = (String) session.getAttribute("nickName");
        ReplyDto comment = new ReplyDto();
        replyMapper.inco(bno);
        comment.setBno(bno);
        comment.setContent(content);
        //로그인 기능을 구현했거나 따로 댓글 작성자를 입력받는 폼이 있다면 입력 받아온 값으로 사용하면 됩니다. 저는 따로 폼을 구현하지 않았기때문에 임시로 "test"라는 값을 입력해놨습니다.
        comment.setWriter(nickName);
        return replyMapper.commentInsert(comment);
    }
    
    @RequestMapping("/delete/{cno}") //댓글 삭제   
    @ResponseBody
    private int mCommentServiceDelete(@PathVariable int cno) throws Exception{
        return replyMapper.commentDelete(cno);
    }
    
    @RequestMapping("/update") //댓글 수정  
    @ResponseBody
    private int mCommentServiceUpdateProc(@RequestParam int cno, @RequestParam String content) throws Exception{
    	ReplyDto comment = new ReplyDto();
        comment.setCno(cno);
        comment.setContent(content);
        return replyMapper.commentUpdate(comment);
    }
}