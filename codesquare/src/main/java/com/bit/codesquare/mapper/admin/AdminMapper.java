package com.bit.codesquare.mapper.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.admin.AdminMemo;
import com.bit.codesquare.dto.admin.ReportedUserInfo;
import com.bit.codesquare.dto.admin.SelectBasket;
import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.lecture.LectureIntroContent;
import com.bit.codesquare.dto.member.Member;
import com.bit.codesquare.dto.paging.Criteria;
import com.bit.codesquare.dto.planner.UserTodoList;

@Mapper
public interface AdminMapper {
	
	public List<Board> getRecommandList() throws Exception;
	public List<ReportedUserInfo> getFiveRepotedInfo() throws Exception;
	public List<UserTodoList> getTodoList(String id) throws Exception;
	public int insertTodoList(Map<String,Object> map) throws Exception;
	public int deleteTodoList(int id) throws Exception;
	public AdminMemo getAdminMemo()throws Exception;
	public int updateAdminMemo(Map<String,Object> map)throws Exception;
	public int getNewPost() throws Exception;
	public int getWeekSignupMember() throws Exception;
	public int getInstroduction() throws Exception;
	public int getRecentLecture() throws Exception;
	public List<ReportedUserInfo> getThreeDayRepotedUserRanking()throws Exception;
	public List<Member> getAllMemberInfo(Criteria cri)throws Exception;
	public int countPaging(Map<String,Object> map)throws Exception;
	public List<Member> getSearchMember(String content)throws Exception;

	public List<Member> getSortIdMemberInfo(Criteria cri)throws Exception;
	public List<Member> getSortPointMemberInfo(Criteria cri)throws Exception;
	public List<Member> getSortBanMemberInfo(Criteria cri)throws Exception;
	
	public Member getSelectMemberInfo(String userId)throws Exception;
	public List<SelectBasket> getAllAuthorInfo()throws Exception;
	public List<SelectBasket> getAllRestrictInfo()throws Exception;
	public int deleteUserInfo(String userId)throws Exception;
	
	public int updateUserInfo(Member member)throws Exception;
	public int updateUsersAuthor(List<SelectBasket> list)throws Exception;
	public int updateUsersRestrict(List<SelectBasket> list)throws Exception;
	public List<LectureIntroContent> getAllLectureList(Criteria cri)throws Exception;	
	public List<LectureIntroContent> getSortbyTagLectureIntro(Map<String,Object> map)throws Exception;	
	public ArrayList<LectureIntroContent> getSearchLectureIntroConent(Map<String,Object> map)throws Exception;
	public ArrayList<Map<String,Object>> getAllLectureTag()throws Exception;
	public int deleteLectureList(List<Map<String,Object>> map)throws Exception;
	public int updateLecturePendingStatus(List<Map<String,Object>> map)throws Exception;
	public List<Map<String,Object>> getAllBlackKeyword()throws Exception;
	public int insertBlackKeyword(List<String> list)throws Exception;
	public int deleteBlackKeyword(List<Map<String,Object>> list)throws Exception;
	
	public List<Board> getAllBoard(Criteria cri)throws Exception;
	
	
	
//	public List<SelectBasket> getAllSelectboxInfo(String table)throws Exception;
//	public List<Member> getSortTagMember(String tag)throws Exception;
}
