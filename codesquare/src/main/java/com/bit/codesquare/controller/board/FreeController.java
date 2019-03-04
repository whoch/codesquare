package com.bit.codesquare.controller.board;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.paging.Criteria;
import com.bit.codesquare.dto.paging.PageMaker;
import com.bit.codesquare.mapper.board.FreeMapper;
import com.bit.codesquare.mapper.comment.ReplyMapper;
import com.bit.codesquare.mapper.study.StudyMapper;
import com.bit.codesquare.service.NewService;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
public class FreeController {

	@Autowired
	FreeMapper freeMapper;
	@Autowired
	NewService newService;
	@Autowired
	ReplyMapper replyMapper;
	@Autowired
	StudyMapper studyMapper;
	
	@RequestMapping("/getfree/{boardKindId}")
	public String getfree (Model model, Criteria cri, String keyword, String searchOption, @PathVariable String boardKindId, String boardName) throws Exception {
		model.addAttribute("list", CodesquareUtil.getDateTimeCompareObject(freeMapper.getfree(cri, keyword, searchOption, boardKindId)));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(freeMapper.countPaging(cri, keyword, searchOption, boardKindId));
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("bn", freeMapper.getBoardName(boardKindId));
		return "board/freeBoard";
	}
	@RequestMapping("/freeWrite/{boardKindId}")
	public String write(@PathVariable String boardKindId, Model model) throws Exception {
		model.addAttribute(boardKindId);
		model.addAttribute("bn", freeMapper.getBoardName(boardKindId));
		return "board/freeWrite";
	}
	@RequestMapping("/freewritego")
	public String freewritego(Board board, @RequestParam String boardKindId) throws Exception {
		freeMapper.insert(board);
		return "redirect:getfree/"+boardKindId;
	}
	@RequestMapping("/freeView/{boardKindId}")
	public String noticeView(Model model, HttpServletRequest request, @PathVariable String boardKindId, Authentication auth) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		freeMapper.updateCount(id);
		model.addAttribute("list",  freeMapper.getid(id));
		model.addAttribute("bn", freeMapper.getBoardName(boardKindId));
		
		try{
			String userId = auth.getName();
			model.addAttribute("bookmarkId", studyMapper.getBookmarkId(id, userId));
			System.out.println("#bookmarkId : "+studyMapper.getBookmarkId(id, userId));
			System.out.println("#bookmarkId : "+userId+" ,"+id);
		}catch (Exception e) {
		}
		return "board/freeView";
	}
	@RequestMapping("/freedelete")
	public String delete(HttpServletRequest request, Model model, @RequestParam String boardKindId) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		freeMapper.delete(id);
		model.addAttribute("list", freeMapper.getid(id));
		return "redirect:getfree/"+boardKindId;
	}
	@RequestMapping("/freemodifygo/{boardKindId}")
	public String modifygo(HttpServletRequest request, Model model, @PathVariable String boardKindId) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		model.addAttribute("list", freeMapper.getid(id));
		model.addAttribute("bn", freeMapper.getBoardName(boardKindId));
		return "board/freeModify";
	}
	@RequestMapping("/freeupdate")
	public String update(HttpServletRequest request, Model model, @ModelAttribute Board board, @RequestParam String boardKindId) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		freeMapper.update(board);
		return "redirect:freeView/"+boardKindId+"?id="+id;
	}
	
}
