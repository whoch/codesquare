package com.bit.codesquare.mapper.group;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.group.GroupInfo;
import com.bit.codesquare.dto.group.GroupMemberInfo;
import com.bit.codesquare.dto.group.WriteWantedBoard;
import com.bit.codesquare.dto.member.JoiningAndRecruitmentLog;
import com.bit.codesquare.dto.planner.GroupMeetingDateDetails;

@Mapper
public interface GroupMapper {
	
	public List <GroupMemberInfo> getMyGroupList (String userId);
	public GroupInfo getGroupInfo(String groupId);
	public List <GroupMemberInfo> getGroupMember (String groupId);
	public GroupInfo getGroupLeader (String groupId);
	
	
	public GroupInfo getGroupInfoUseBoardId(int boardId);
	public List<GroupInfo> getGroupInfoUserLeader(String userId);
	public List<GroupMeetingDateDetails> getGroupInfoUserJoinning(String userId);
	public int updateWantedInfo(WriteWantedBoard writeWantedBoard);
	public int updateGroupMeetingStatus(Map<String, String> data);
	public String getApplyingStatus(int boardId, String userId);
	public LocalDate getRecentMeetingDate(String groupId);
	public Integer getJoinningGroupInfo(String userId, String groupId, String column);
	public int insertStudyJoining(JoiningAndRecruitmentLog data);
	public void cancelApplication(Map<String, String> data);
	public void updateGroupRecruitmentCount(String groupId, int recruitmentCount);
	public void setDeclineContentUseBoardId(Map<String, String> data);
	

}
