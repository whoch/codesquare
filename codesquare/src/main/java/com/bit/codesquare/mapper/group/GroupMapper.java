package com.bit.codesquare.mapper.group;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.group.GroupInfo;
import com.bit.codesquare.dto.group.GroupMemberInfo;

@Mapper
public interface GroupMapper {

	public List <GroupMemberInfo> getMyGroupList (String userId);
	
	public GroupInfo getGroupInfo(String groupId);
	public List <GroupMemberInfo> getGroupMember (String groupId);
	public GroupInfo getGroupLeader (String groupId);
	
}
