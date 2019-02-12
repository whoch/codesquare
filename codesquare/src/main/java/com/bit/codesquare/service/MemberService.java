package com.bit.codesquare.service;

import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.member.MemberMapper;

@Service
public class MemberService {

	@Autowired
	private MemberMapper mm;

	@Autowired
	JavaMailSender mailsender;

	public List<Board> getWantedList(String userId) {

		List<Board> list = mm.getWantedList(userId);
		for (Board item : list) {
			item.setWantedPlist(mm.getWantedPList(item.getId()));
		}

		return list;
	}

	public void mailSending(String userId) {

		int certCharLength = 8;
		final char[] characterTable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
				'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6',
				'7', '8', '9', '0' };

		Random random = new Random(System.currentTimeMillis());
		int tablelength = characterTable.length;
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < certCharLength; i++) {
			buf.append(characterTable[random.nextInt(tablelength)]);
		}

		Member member = mm.getUser(userId);

		member.setPassword(new BCryptPasswordEncoder().encode(buf));
		mm.changePw(member);
		String setfrom = "codesquare12@gmail.com";
		String tomail = member.getEmail();
		String title = "CodeSquare"+userId+"님의 초기화 된 비밀번호";
		String content = userId + "님의 초기화된 비밀 번호는 " + buf + " 입니다. ";
		try {
			MimeMessage message = mailsender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom(setfrom);
			messageHelper.setTo(tomail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);

			mailsender.send(message);

		} catch (Exception e) {
		}
	}

}
