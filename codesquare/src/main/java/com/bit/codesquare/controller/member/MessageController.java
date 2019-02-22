package com.bit.codesquare.controller.member;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bit.codesquare.dto.member.MessageInfo;
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

	@GetMapping("receivedMessage")
	public String myMessage(Model model, Authentication auth, HttpSession session) {
		csu.getSession(auth, session);
		String recipient = auth.getName();
		model.addAttribute("received", mim.getReceivedMessageList(recipient));
		
		return "member/myPage/myReceivedMessage";
	}
	
	
	
	@GetMapping("receivedMessage/{id}")
	public String readMessage(Model model,@PathVariable int id) {
		mim.changeToRead(id);
		model.addAttribute("read", mim.readMessage(id));
		
		return "member/myPage/myReceivedMessageView";
	}
	
//	@PostMapping("receivedMessage/{id}")
//	@ResponseBody
//	public int deleteMessage( @PathVariable int id) {
//		
//		return mim.deleteMessage(id);
//	}
	
	

	@GetMapping("sentMessage")
	public String sent(Model model, Authentication auth, HttpSession session) {
		csu.getSession(auth, session);
		String sender = auth.getName();
		model.addAttribute("sent", mim.getSentMessageList(sender));
		
		return "member/myPage/mySentMessage";
	}
	

	@GetMapping("sendMessage")
	public String send(Model model, @RequestBody Map<String, String> data) {
		
		String recipient = data.get("recipient");
		String recipientNickName = data.get("recipientNickName");
		logger.info(recipient+recipientNickName);
		
		model.addAttribute(recipient);
		model.addAttribute(recipientNickName);
		
		
		return "member/myPage/mySendMessage";
	};
	

	
	@PostMapping("sendMessage")
	@ResponseBody
	public MessageInfo sendMessage(Authentication auth, HttpSession session, @RequestBody Map<String, String> data) {
		
		logger.info("sendMessageCalled");
		
		String sender = data.get("sender");
		String senderNickName = data.get("senderNickName");
		String recipientNickName = data.get("recipientNickName");
		String recipient = data.get("recipient");
		String messageTitle = data.get("messageTitle");
		String messageContent = data.get("messageContent");
		
		MessageInfo messageInfo = new MessageInfo();
		
		messageInfo.setSender(sender);
		messageInfo.setSenderNickName(senderNickName);
		messageInfo.setRecipient(recipient);
		messageInfo.setRecipientNickName(recipientNickName);
		messageInfo.setMessageTitle(messageTitle);
		messageInfo.setMessageContent(messageContent);
		
		return mim.sendMessage(messageInfo);
	}
	
//
//	public int sendMessage(MessageInfo messageInfo);
//
//
//	public int deleteMessage(MessageInfo messageInfo);

}
