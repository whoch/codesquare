package com.bit.codesquare.controller.board;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.paging.Criteria;
import com.bit.codesquare.dto.paging.PageMaker;
import com.bit.codesquare.mapper.board.FreeMapper;
import com.bit.codesquare.mapper.board.NewMapper;
import com.bit.codesquare.mapper.comment.CommentMapper;
import com.bit.codesquare.mapper.comment.ReplyMapper;
import com.bit.codesquare.service.NewService;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
public class FreeController {

	@Autowired
	FreeMapper freeMapper;
	@Autowired
	NewMapper newMapper;
	@Autowired
	NewService newService;
	@Autowired
	ReplyMapper replyMapper;
	
	@RequestMapping("/getfree")
	public String getfree (Model model, Criteria cri, String keyword, String searchOption) throws Exception {
		model.addAttribute("list", CodesquareUtil.getDateTimeCompareObject(freeMapper.getfree(cri, keyword, searchOption)));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(freeMapper.countPaging(cri, keyword, searchOption));
		model.addAttribute("pageMaker", pageMaker);
		return "board/freeBoard";
	}
//	@RequestMapping("/freeSearch")
//	public String freeSearch(Model model, @ModelAttribute Board board, Criteria cri) throws Exception {
//		model.addAttribute("list", CodesquareUtil.getDateTimeCompareObject(freeMapper.freeSearch(board)));
//		PageMaker pageMaker = new PageMaker();
//		pageMaker.setCri(cri);
//		pageMaker.setTotalCount(freeMapper.countPaging(cri));
//		model.addAttribute("pageMaker", pageMaker);
//		return "board/freeBoard";
//	}
	@RequestMapping("/freeWrite")
	public String write() {
		return "board/freeWrite";
	}
	@RequestMapping("/freewritego")
	public String freewritego(Model model, @ModelAttribute Criteria cri, Board board) throws Exception {
		freeMapper.insert(board);
		return "redirect:getfree";
	}
	@RequestMapping("/freeView")
	public String noticeView(Model model, HttpServletRequest request, Criteria cri) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		newMapper.updateCount(id);
		model.addAttribute("list",  newMapper.getid(id));
		return "board/freeView";
	}
	@RequestMapping("/freedelete")
	public String delete(HttpServletRequest request, Model model, Criteria cri) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		newMapper.delete(id);
		return "redirect:getfree";
	}
	@RequestMapping("/freemodifygo")
	public String modifygo(HttpServletRequest request, Model model) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		model.addAttribute("list", newMapper.getid(id));
		return "board/freeModify";
	}
	@RequestMapping("/freeupdate")
	public String update(HttpServletRequest request, Model model, @ModelAttribute Board board) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		newMapper.update(board);
		return "redirect:getfree";
	}
	
}
