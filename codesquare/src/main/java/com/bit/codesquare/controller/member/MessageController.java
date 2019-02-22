package com.bit.codesquare.controller.member;


import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.codesquare.dto.member.MessageInfo;
import com.bit.codesquare.dto.paging.Criteria;
import com.bit.codesquare.dto.paging.PageMaker;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.mapper.member.MessageInfoMapper;
import com.bit.codesquare.service.MemberService;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
@RequestMapping("/message")
public class MessageController {

	private final static Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MessageInfoMapper mim;

	@Autowired
	CodesquareUtil csu;

	@Autowired
	MemberService ms;

	@Autowired
	MemberMapper mm;

	@GetMapping("receivedMessage")
	public String receivedMessage(Model model, Authentication auth, HttpSession session, @ModelAttribute Criteria cri) throws Exception {
		csu.getSession(auth, session);
		String recipient = auth.getName();
		cri.setPerPageNum(4);
		model.addAttribute("received", mim.getReceivedMessageList(recipient, cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setDisplayPageNum(5);
		pageMaker.setTotalCount(mim.countRPaging(recipient, cri));
		model.addAttribute("pageMaker", pageMaker);

		return "member/myPage/myReceivedMessage";
	}
	
	@GetMapping("receivedMessage/{keyword}")
	public String searchedReceivedMessage(Model model, Authentication auth, HttpSession session, @ModelAttribute Criteria cri, @PathVariable String keyword) throws Exception {
		csu.getSession(auth, session);
		String recipient = auth.getName();
		cri.setPerPageNum(4);
		model.addAttribute("received", mim.getSearchedReceivedMessageList(recipient, keyword, cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setDisplayPageNum(5);
		pageMaker.setTotalCount(mim.searchCountRPaging(recipient, keyword, cri));
		model.addAttribute("pageMaker", pageMaker);

		return "member/myPage/myReceivedMessage";
	}


	@GetMapping("sentMessage")
	public String sentMessage(Model model, Authentication auth, HttpSession session, @ModelAttribute Criteria cri) throws Exception {
		csu.getSession(auth, session);
		String sender = auth.getName();
		cri.setPerPageNum(4);
		model.addAttribute("sent", mim.getSentMessageList(sender, cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setDisplayPageNum(5);
		pageMaker.setTotalCount(mim.countSPaging(sender, cri));
		model.addAttribute("pageMaker", pageMaker);

		return "member/myPage/mySentMessage";
	}

	
//
//	@GetMapping("sentMessage/{keyword}")
//	public String searchedSentMessage(Model model, Authentication auth, HttpSession session, @ModelAttribute Criteria cri) throws Exception {
//		csu.getSession(auth, session);
//		String sender = auth.getName();
//		cri.setPerPageNum(4);
//		model.addAttribute("sent", mim.getSentMessageList(sender, cri));
//		PageMaker pageMaker = new PageMaker();
//		pageMaker.setCri(cri);
//		pageMaker.setDisplayPageNum(5);
//		pageMaker.setTotalCount(mim.countPaging(sender, cri));
//		model.addAttribute("pageMaker", pageMaker);
//
//		return "member/myPage/mySentMessage";
//	}
	
//	@GetMapping("receivedMessage/{id}")
//	public String readMessage(Model model, @PathVariable int id) {
//
//		String readStatus = mim.readMessage(id).getReadStatus();
//	
//		if (!readStatus.equals("읽음")) {
//			//읽지 않음이면
//			mim.changeToRead(id);
//			mim.setUnreadMessageCount(mim.readMessage(id).getRecipient());
//		}
//		
//		model.addAttribute("receivedRead", mim.readMessage(id));
//
//		return "member/myPage/myReceivedMessageView";
//	}


	@GetMapping("sentMessage/{id}")
	public String sentMessage(Model model, @PathVariable int id) {
		model.addAttribute("sentRead", mim.readMessage(id));

		return "member/myPage/mySentMessageView";
	}

	@GetMapping("sendMessage")
	public String sendNew(Authentication auth, HttpSession session) {
		csu.getSession(auth, session);
		
		return "member/myPage/mySendNewMessage";
	}
	
	
	
	@GetMapping("sendMessage/{recipient}")
	public String send(Model model, @PathVariable String recipient) {

		model.addAttribute(recipient);
		model.addAttribute("recipientNickName", mm.getUser(recipient).getNickName());

		return "member/myPage/mySendMessage";
	};

	
	
	@PostMapping("sendMessage")
	@ResponseBody
	public int sendMessage(@ModelAttribute MessageInfo messageInfo, @RequestBody Map<String, String> data) {
		String sender = data.get("sender");
		String senderNickName = data.get("senderNickName");
		String recipient = data.get("recipient");
		String recipientNickName = mm.getUser(recipient).getNickName();
		String messageContent = data.get("messageContent");

		messageInfo.setSender(sender);
		messageInfo.setSenderNickName(senderNickName);
		messageInfo.setRecipient(recipient);
		messageInfo.setRecipientNickName(recipientNickName);
		messageInfo.setMessageContent(messageContent);

		mim.messageCountUp(recipient);

		return mim.sendMessage(messageInfo);
	}

	@PostMapping("deleteMessage")
	@ResponseBody
	public int deleteMessage(@RequestBody int[] ids) {
		logger.info(ids[0]+"ids0");
		int count = 0;

		count = mim.deleteMessage(ids);
		for (int i = 0; i < ids.length; i++) {
			String recipient = mim.readMessage(ids[i]).getRecipient();
			mim.setUnreadMessageCount(recipient);
		}

		return count;
	}

}
