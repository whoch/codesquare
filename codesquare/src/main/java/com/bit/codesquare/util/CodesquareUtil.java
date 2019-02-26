package com.bit.codesquare.util;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.mapper.member.MemberMapper;
import com.bit.codesquare.security.SecurityMember;

@Component
public class CodesquareUtil {
	@Value("${user.thumbnail.directory}")
	String userThumbPath;

	/**
	 * 
	 * @param postId  글번호
	 * @param imgName 이미지의 이름
	 * @return 글번호+ 이미지의 이름
	 */
	public String getPath(int postId, String imgName) {
		return postId + "/" + imgName;
	}

	/**
	 * 
	 * @param postId    글번호
	 * @param imgName   이미지의 이름
	 * @param extension 확장자 이름
	 * @return 글번호+이미지의 이름+확장자의 이름
	 */
	public String getPath(int postId, String imgName, String extension) {
		return postId + "/" + imgName + "." + extension;
	}

	/**
	 * 
	 * @param userId  회원아이디
	 * @param imgName 이미지의 이름
	 * @return 회원아이디+이미지의 이름
	 */
	public String getPath(String userId, String imgName) {

		return userId + "/" + imgName;
	}

	/**
	 * 
	 * @param cTime 회원이 영상을 본 현재시간
	 * @param dTime 영상의 총 길이
	 * @return 회원이 영상을 완강했는지 여부
	 */
	public String isClearcalc(Double cTime, Double dTime) {
		double val = (cTime / dTime) * 100;
		return (val >= 90) ? "END" : "ING";
	}

	@Autowired
	MemberMapper mm;

	public void getSession(Authentication auth, HttpSession session) {

		if (auth != null && session.getAttribute("userId") == null) {
			SecurityMember sc = (SecurityMember) auth.getPrincipal();

			Member member = mm.getUser(sc.getUsername());

			if (!member.getProfileImagePath().equals("DefaultThumbnail")) {
				member.setProfileImagePath(userThumbPath += getPath(member.getUserId(), member.getProfileImagePath()));
			} else {
				member.setProfileImagePath("/static/images/DefaultThumbnail.png");
			}
			session.setAttribute("userId", member.getUserId());
			session.setAttribute("nickName", member.getNickName());
			session.setAttribute("authorId", member.getAuthorId());
			session.setAttribute("profileImagePath", member.getProfileImagePath());
		}
	}

	public int uploadProfile(MultipartFile[] uploadForm, Authentication auth) throws Exception {

		String userId = auth.getName();
		String uploadFolder = "/Users/jiyeon/git/codesquare/codesquare/src/main/resources/static/codesquareDB/UserThumbnail/"
				+ userId;
		// db에 저장할 상대경로
		// String uploadRelativeDirectory =
		// "/static/codesquareDB/UserThumbnail/"+userId;
		File uploadPath = new File(uploadFolder); // 안에 여러개 쓰면 합쳐짐

		if (!uploadPath.exists()) {
			uploadPath.mkdirs(); // 존재하지 않으면 경로를 만든다
		}
		String uploadFileName = userId + "_Thumbnail.jpg"; // +multipartFile.getOriginalFilename()하면 업로드한 파일네임으로 들어감

		int count = 0;
		try {
			File saveFile = new File(uploadPath, uploadFileName);
			uploadForm[0].transferTo(saveFile); // 실제저장되는단계. savefile:경로랑 파일명 합친거

			 count = mm.updateProfile(userId, uploadFileName);
		} catch (Exception e) {
			e.getMessage();
		}

		return count;
	}

	/**
	 * 
	 * @param LocalDateTime타입 compareDateTime 현재와 비교할 날짜
	 * @return String 문자열 반환(ex: 방금 전, 1분 전, 1시간 전, 7일 전)
	 * @see 차이가 1분 이하일 땐 방금 전, 최대 7일까지만 텍스트로 반환 나머지는 yyyy.MM.dd의 문자열
	 */
	public static String compareDateTime(LocalDateTime dateTime) {
		LocalDate compareDate = dateTime.toLocalDate();
		LocalTime compareTime = dateTime.toLocalTime();
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		String result;
		if (dateTime != null && compareDate.isEqual(currentDate)) { // 오늘일 때
			int differenceInMinutes = (int) Duration.between(compareTime, currentTime).toMinutes();
			if (differenceInMinutes < 1) {
				result = "방금 전";
			} else if (differenceInMinutes < 60) {
				result = differenceInMinutes + "분 전";
			} else {
				result = differenceInMinutes / 60 + "시간 전";
			}
		} else { // 오늘이 아닐 때
			int differenceInDates = Period.between(compareDate, currentDate).getDays();
			if (differenceInDates <= 7) {
				result = differenceInDates + "일 전";
			} else {
				result = compareDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
			}
		}
		return result;
	}

	/**
	 * 
	 * @param datetime    LocalDateTime타입의 오늘 날짜와 비교할 대상
	 * @param datePattern 변경할 날짜 형식("yy-MM-dd")
	 * @return String 문자열 반환(ex: 방금 전, 1분 전, 1시간 전, 7일 전)
	 * @see 차이가 1분 이하일 땐 방금 전, 최대 7일까지만 텍스트로 반환 나머지는 yyyy.MM.dd의 문자열
	 */
	public String compareDateTime(LocalDateTime datetime, String datePattern) {
		LocalDate compareDate = datetime.toLocalDate();
		LocalTime compareTime = datetime.toLocalTime();
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		String result;
		if (datetime != null && compareDate.isEqual(currentDate)) { // 오늘일 때
			int differenceInMinutes = (int) Duration.between(compareTime, currentTime).toMinutes();
			if (differenceInMinutes < 1) {
				result = "방금 전";
			} else if (differenceInMinutes < 60) {
				result = differenceInMinutes + "분 전";
			} else {
				result = differenceInMinutes / 60 + "시간 전";
			}
		} else { // 오늘이 아닐 때
			int differenceInDates = Period.between(compareDate, currentDate).getDays();
			if (differenceInDates <= 7) {
				result = differenceInDates + "일 전";
			} else {
				result = compareDate.format(DateTimeFormatter.ofPattern(datePattern));
			}
		}
		return result;
	}

	/**
	 * 
	 * @see writeDateformat을 set함
	 * @param impl ComparebleDateTime 인터페이스를 구현한 객체의 List
	 */
	public void setDateTimeCompare(List<? extends ComparableDateTime> impl) {
		List<? extends ComparableDateTime> result = impl;
		for (ComparableDateTime list : result) {
			list.setDateTimeCompare(compareDateTime(list.getDateTimeCompare()));
		}
	}

	/**
	 * 
	 * @see String datePattern으로 날짜 형식을 정한뒤 저장할 수 있습니다 예시 compareDateTime(dateTime,
	 *      "yyyy.MM.dd")
	 * @param impl        인터페이스를 구현한 객체의 List
	 * @param datePattern 변경할 날짜 형식("yy-MM-dd")
	 * 
	 */
	public void setDateTimeCompare(List<? extends ComparableDateTime> impl, String datePattern) {
		List<? extends ComparableDateTime> result = impl;
		for (ComparableDateTime list : impl) {
			list.setDateTimeCompare(compareDateTime(list.getDateTimeCompare(), datePattern));
		}
	}

	/**
	 * 
	 * @see writeDateformat을 set함
	 * @param impl ComparebleDateTime 인터페이스를 구현한 객체의 List
	 */
	public static List<? extends ComparableDateTime> getDateTimeCompareObject(List<? extends ComparableDateTime> impl) {
		List<? extends ComparableDateTime> result = impl;
		for (ComparableDateTime list : result) {
			list.setDateTimeCompare(compareDateTime(list.getDateTimeCompare()));
		}
		return result;
	}

	/**
	 * 
	 * @see String datePattern으로 날짜 형식을 정한뒤 저장할 수 있습니다 예시 compareDateTime(dateTime,
	 *      "yyyy.MM.dd")
	 * @param impl        인터페이스를 구현한 객체의 List
	 * @param datePattern 변경할 날짜 형식("yy-MM-dd")
	 * 
	 */
	public List<? extends ComparableDateTime> getDateTimeCompareObject(List<? extends ComparableDateTime> impl,
			String datePattern) {
		List<? extends ComparableDateTime> result = impl;
		for (ComparableDateTime list : result) {
			list.setDateTimeCompare(compareDateTime(list.getDateTimeCompare(), datePattern));
		}
		return result;
	}

	public static final Set<DayOfWeek> WEEKEND = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

}
