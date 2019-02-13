package com.bit.codesquare.controller.member;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bit.codesquare.dto.member.InstructorInfo;
import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.service.MemberService;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
@RequestMapping("/member")
public class MemberController {
	private final static Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberMapper mm;

	@Autowired
	CodesquareUtil csu;

	@Autowired
	MemberService ms;

	@RequestMapping("/login")
	public String login(HttpServletRequest request) {

		String referrer = request.getHeader("Referer");
		request.getSession().setAttribute("prevPage", referrer);

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
		member.setNickName(userId);
		member.setPassword(new BCryptPasswordEncoder().encode(password));
		mm.signUp(member);
		model.addAttribute("user", mm.getUser(userId));
		return "member/login/signUpDone";
	}

	@PostMapping("idCheck")
	@ResponseBody
	public int idCheck(@RequestBody String userId) {
		int count = 0;
		count = mm.idCheck(userId);
		return count;
	}

	@GetMapping("/changeNick")
	public String changeNick(Model model, Principal principal) {
		String userId = principal.getName();
		model.addAttribute("user", mm.getUser(userId));
		// logger.info(userId);
		return "member/myPage/changeNick";
	}

	@PostMapping("/nickChange")
	@ResponseBody
	public int changeNick(Authentication auth, HttpSession session, @RequestBody String nickName) {
		String userId = auth.getName();

		// 다른정보 가지고올때
//		SecurityMember sm = (SecurityMember) auth.getPrincipal();
//		sm.getNickName();

		int count = 0;
		count = mm.nickCheck(nickName);

		// logger.info(nickName);

		if (count == 0) {
			Member member = mm.getUser(userId);
			member.setNickName(nickName);
			// us.changeNick(user);
			logger.info(member.toString());
			mm.changeNick(member);
			session.setAttribute("nickName", member.getNickName());
		}

		// logger.info("count:"+count);
		return count;
	}

	@PostMapping("/emailChange")
	@ResponseBody
	public int emailChange(Principal principal, @RequestBody String email) {
		String userId = principal.getName();

		int count = 0;
		count = mm.emailCheck(email);

		// logger.info(nickName);
		// logger.info(count+"d");
		if (count == 0) {
			Member member = mm.getUser(userId);
			member.setEmail(email);
			// us.changeNick(user);
			// logger.info(user.toString());
			mm.changeEmail(member);
		}
		return count;
	}

	@GetMapping("/findIdPw")
	public String findIdPw() {
		return "member/login/findIdPw";
	}

	@PostMapping("/emailCheck")
	@ResponseBody
	public int emailCheck(@RequestBody String email) {

		int count = 0;
		count = mm.emailCheck(email);

		return count;
	}

	@PostMapping("findId")
	@ResponseBody
	public String findId(@RequestBody String email) {
		return mm.findId(email);
	}

	@PostMapping("findPw")
	@ResponseBody
	public int findPw(@RequestBody Map<String, String> data) {
		String userId = data.get("userId");
		String email = data.get("email");
		int count = 0;
		count = mm.findPw(userId, email);

		return count;
	}

	@PostMapping("/findPwMail")
	@ResponseBody
	public void mailSending(@RequestBody String userId) {
		ms.mailSending(userId);

	}

	@GetMapping("/myPage")
	public String myPage(Model model, Authentication auth, HttpSession session) {
		csu.getSession(auth, session);
		String userId = auth.getName();
		model.addAttribute("user", mm.getUser(userId));
		model.addAttribute("rlist", mm.getReservedList(userId));
		model.addAttribute("alist", mm.getAppliedList(userId));
		model.addAttribute("wlist", ms.getWantedList(userId));
		model.addAttribute("blist", mm.getMyBoardList(userId));
		model.addAttribute("count", mm.getMyCount(userId));
		model.addAttribute("instructorInfo", mm.getInstructorInfo(userId));

		return "member/myPage/myPage";
	}

	@PostMapping("/changePw")
	@ResponseBody
	public int changePwDone(@ModelAttribute Member member, @RequestBody Map<String, String> data) {
		String userId = data.get("userId");
		String password = data.get("password");
		int count = 0;
		member.setPassword(new BCryptPasswordEncoder().encode(password));
		member.setUserId(userId);
		count = mm.changePw(member);

		return count;
	}

	@PostMapping("/toInstructor")
	@ResponseBody
	public String toInstructor(@ModelAttribute InstructorInfo instructorInfo, @RequestBody Map<String, String> data) {

		instructorInfo.setUserId(data.get("userId"));
		instructorInfo.setIntroContent(data.get("introContent"));
		mm.toInstructor(instructorInfo);

		return "redirect:myPage";
	}

	@PostMapping("/modifyInstructorInfo")
	public String modifyInstructorInfo(@ModelAttribute InstructorInfo instructorInfo,
			@RequestBody Map<String, String> data) {

		String userId = data.get("userId");
		String introContent = data.get("introContent");
		String history = data.get("history");

		instructorInfo.setUserId(userId);
		instructorInfo.setIntroContent(introContent);
		instructorInfo.setHistory(history);
		mm.modifyInstructorInfo(instructorInfo);

		return "redirect:myPage" + "#modifyInstructorInfoForm";
	}

	@GetMapping("/upload")
	public String upload( Authentication auth, HttpSession session) {
		csu.getSession(auth, session);
		return "member/myPage/upload";
	}
	
	
	
    @PostMapping("/upload")
    @ResponseBody
    public String uploadForm(@RequestBody MultipartFile[] uploadForm, Authentication auth) {

    	String userId = auth.getName();
    	String uploadFolder = "/Users/jiyeon/git/codesquare/codesquare/src/main/resources/static/codesquareDB/UserThumbnail/"+userId;
    	//db에 저장할 상대경로
    	//String uploadRelativeDirectory = "/static/codesquareDB/UserThumbnail/"+userId;
    	
    	File uploadPath= new File(uploadFolder); //안에 여러개 쓰면 합쳐짐
    	
    	if (!uploadPath.exists()) {
    		uploadPath.mkdirs(); //존재하지 않으면 경로를 만든다
        }
    	
    	String uploadFileName = userId+"_Thumbnail.jpg"; //+multipartFile.getOriginalFilename()하면 업로드한 파일네임으로 들어감

        try {
        	File saveFile = new File(uploadPath, uploadFileName);
        	uploadForm[0].transferTo(saveFile); //실제저장되는단계. savefile:경로랑 파일명 합친거
        } catch (Exception e) {
        	e.getMessage();
        }

        return "redirect:upload";
    }

  

}
