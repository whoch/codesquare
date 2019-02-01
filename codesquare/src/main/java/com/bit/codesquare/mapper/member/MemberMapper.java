package com.bit.codesquare.mapper.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.member.InstructorInfo;
import com.bit.codesquare.dto.member.JoiningAndRecruitmentLog;
import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.dto.member.ReservationInfo;



@Mapper
public interface MemberMapper {

	public Member checkUser(String userId);
	public Member getUser(String userId);

	public int signUp(Member user);
	//public int modifyMyInfo(User user);
	public int changePw(Member user);
	public int changeNick(Member user);
	public int changeEmail(Member user);
	
	public int idCheck(String userId);
	public int emailCheck(String email);
	public int nickCheck(String nickName);
	
	public String findId(String email);
	public int findPw(String userId, String email);
	
	
	
	public int toInstructor(InstructorInfo instructorInfo);
	public int checkInstructor(String userId);
	
	public int modifyInstructorInfo(InstructorInfo instructorInfo);
	
	public List <ReservationInfo> getReservedList(String userId);
	public List <JoiningAndRecruitmentLog> getAppliedList(String userId);
	public List <JoiningAndRecruitmentLog> getWantedList(String userId);
	
	public InstructorInfo getInstructorInfo (String userId);
}
