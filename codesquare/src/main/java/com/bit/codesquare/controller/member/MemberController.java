package com.bit.codesquare.controller.member;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.codesquare.dto.member.InstructorInfo;
import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.member.MemberMapper;


@Controller
@RequestMapping("/member")
public class MemberController {
	private final static Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberMapper mm;

//	Authentication auth;
//	LoginUserDetails lud = (LoginUserDetails) auth.getPrincipal();


	@RequestMapping("/login")
	public String login() {
		return "member/login/login";
	}

	@GetMapping("/signUp")
	public String signUp() {
		return "member/login/signUp";
	}

	@PostMapping("/signUp")
	public String signUp(Model model, @ModelAttribute Member member, @RequestParam String userId,
			@RequestParam String password) {
		member.setAuthorId(1);
		member.setPassword(new BCryptPasswordEncoder().encode(password));
		mm.signUp(member);
		model.addAttribute("user", mm.getUser(userId));
		return "member/login/signUpDone";
	}

	

	@PostMapping("/idCheck")
	@ResponseBody
	public int idCheck(@RequestBody String userId) {
		logger.info("idCheck호출");
		logger.info(userId);
		int count = 0;
		count = mm.idCheck(userId);
		logger.info(count+"count");
		return count;
	}

	@PostMapping("/emailCheck")
	@ResponseBody
	public int emailCheck(@RequestBody String email) {
		logger.info("anjwl");
		logger.info(email);
		
		int count = 0;
		count = mm.emailCheck(email);
		logger.info(email);
		return count;
	}

//	@PostMapping("/nickCheck")
//	@ResponseBody
//	public int nickCheck(@RequestBody String nickName) {
//
//		int count = 0;
//		count = mm.nickCheck(nickName);
//
//		return count;
//	}

	@PostMapping("/nickChange")
	@ResponseBody
	public int changeNick(Principal principal, @RequestBody String nickName) {
		String userId = principal.getName();
	
		int count = 0;
		count = mm.nickCheck(nickName);

		//logger.info(nickName);

		if (count == 0) {
			Member member = mm.getUser(userId);
			member.setNickName(nickName);
			// us.changeNick(user);
			logger.info(member.toString());
			mm.changeNick(member);
		} 
		
		//logger.info("count:"+count);
		return count;
	}

	@PostMapping("/emailChange")
	@ResponseBody
	public int emailChange (Principal principal, @RequestBody String email) {
		String userId = principal.getName();
		
		int count = 0;
		count = mm.emailCheck(email);

		//logger.info(nickName);
		//logger.info(count+"d");
		if (count == 0) {
			Member member = mm.getUser(userId);
			member.setEmail(email);
			// us.changeNick(user);
			//logger.info(user.toString());
			mm.changeEmail(member);
		}
		return count;
	}

	@GetMapping("/findIdPw")
	public String findIdPw() {
		return "member/login/findIdPw";
	}
	
	
	
	@GetMapping("/findId")
	public String findId() {
		return "member/login/findId";
	}
	@PostMapping("/findId")
	public String findIdDone(HttpServletResponse response, @RequestParam String email, Model model) {
		model.addAttribute("findIdDone", mm.findId(email));
		return "findId";
	}
	
	@GetMapping("/findPw")
	public String findPw() {
		return "member/login/findPw";
	}
	
	@PostMapping("/findPw")
	public String findPw(@RequestParam String email) {
		return "member/login/findPw";
	}
	
	
//	@RequestMapping("/myPage")
//	public String myPage(Model model, Principal principal, @ModelAttribute Member member) {
//		String userId = principal.getName();
//		model.addAttribute("user", mm.getUser(userId));
//		return "member/myPage/myPage";
//	}
	
	
	
	@RequestMapping("/myPage")
	public String myPage(Model model, Principal principal, @ModelAttribute Member member) {
		String userId = principal.getName();
		model.addAttribute("user", mm.getUser(userId));
		return "index";
	}

	@GetMapping("/modifyMyInfo")
	public String ModifyMyInfo(Model model, Principal principal) {
		String userId = principal.getName();
		// logger.info("아이디"+userId);
		model.addAttribute("user", mm.getUser(userId));
		return "member/myPage/modifyMyInfo";
	}


	@GetMapping("/changePw")
	public String changePw(Model model, Principal principal) {
		String userId = principal.getName();
		model.addAttribute("user", mm.getUser(userId));
		//logger.info(userId);
		return "member/myPage/changePw";
	}

	@PostMapping("/changePw")
	@ResponseBody
	public int changePwDone(@ModelAttribute Member user, @RequestBody Map<String, String> data) {
		int count =0;
		String password = data.get("password");
		String userId = data.get("userId");
//		logger.info(userId);
//		logger.info(password);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setUserId(userId);
		count = mm.changePw(user);
		
		return count;
	}

	@GetMapping("/toInstructor")
	public String toInstructor(Model model, Principal principal) {
		String userId = principal.getName();
		model.addAttribute("user", mm.getUser(userId));
		
		int count = 0;
		count = mm.checkInstructor(userId);
		if(count == 0) {
			return "member/myPage/toInstructor";
		}
		return "member/myPage/instructorPending";
	}
	
	@PostMapping("/toInstructor")
	public String toInstructor(Principal principal, @ModelAttribute InstructorInfo instructorInfo, @RequestBody String introContent ) {
	
		String userId = principal.getName();
		//model.addAttribute("user", mm.getUser(userId));
		//logger.info(userId);
		//logger.info(introContent);

		instructorInfo.setUserId(userId);
		instructorInfo.setIntroContent(introContent);
		mm.toInstructor(instructorInfo);

		return "redirect:toInstructor";
	}
	
	@GetMapping("/modifyInstructorInfo")
	public String modifyInstructorInfo(Model model, Principal principal,@ModelAttribute InstructorInfo instructorInfo) {
		String userId = principal.getName();
		model.addAttribute("user", mm.getUser(userId));
		//logger.info(mm.getUser(userId).toString());
		model.addAttribute("instructorInfo", mm.getInstructorInfo(userId));
		//logger.info(mm.getInstructorInfo(userId).toString());
		return "member/myPage/modifyInstructorInfo";
	}
	
	
	@PostMapping("/modifyInstructorInfo")
	public String modifyInstructorInfo(Model model, Principal principal, @ModelAttribute InstructorInfo instructorInfo,  @RequestBody Map<String, String> data) {
		String userId = principal.getName();
		//String userId = data.get("userId");
		String introContent = data.get("introContent");
		String history = data.get("history");
//		logger.info(data.toString());
		model.addAttribute("userId", mm.getUser(userId));
//		logger.info(userId);
		
		instructorInfo.setUserId(userId);
		instructorInfo.setIntroContent(introContent);
		instructorInfo.setHistory(history);
		mm.modifyInstructorInfo(instructorInfo);
		
		
		return "redirect:modifyInstructorInfo";
	}
	
	@GetMapping("/myReservedList")
	public String myReservedList(Model model, Principal principal) {
		String userId = principal.getName();
		//model.addAttribute("user", us.getUser(userId));
		
		model.addAttribute("list",mm.getReservedList(userId));
		return "member/myPage/myReservedList";
	}
	
	@GetMapping("/myAppliedList")
	public String myAppliedList(Model model, Principal principal) {
		String userId = principal.getName();
		model.addAttribute("list", mm.getAppliedList(userId));
		return "member/myPage/myAppliedList";
	}
	
	@GetMapping("/myWantedList")
	public String myWantedList(Model model, Principal principal) {
		String userId = principal.getName();
		model.addAttribute("list",mm.getWantedList(userId));
		return "member/myPage/myWantedList";
	}
	
	@GetMapping("/myBoardList")
	public String myBoardList() {
//		String userId = principal.getName();
//		model.addAttribute("list",us.getWantedList(userId));
		return "member/myPage/myBoardList";
	}
	
//	@RequestMapping("/logout")
//	public void logout() {
//
//	}

}
