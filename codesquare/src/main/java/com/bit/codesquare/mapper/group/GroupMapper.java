package com.bit.codesquare.mapper.group;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.group.GroupInfo;
import com.bit.codesquare.dto.group.GroupMemberInfo;
import com.bit.codesquare.dto.group.WriteWantedBoard;
import com.bit.codesquare.dto.member.JoiningAndRecruitmentLog;

@Mapper
public interface GroupMapper {
	
	public List <GroupMemberInfo> getMyGroupList (String userId);
	public GroupInfo getGroupInfo(String groupId);
	public List <GroupMemberInfo> getGroupMember (String groupId);
	public GroupInfo getGroupLeader (String groupId);
	
	
	public GroupInfo getGroupInfoUseBoardId(int boardId);
	public List<GroupInfo> getGroupInfoUserLeader(String userId);
	public int updateWantedInfo(WriteWantedBoard writeWantedBoard);
	public int insertStudyJoining(JoiningAndRecruitmentLog data);
	public String getApplyingStatus(int boardId, String applyUserId);
	public Integer getJoinningGroupId(String userId, String groupId);
	public void cancelApplication(Map<String, String> data);
	
	

}
