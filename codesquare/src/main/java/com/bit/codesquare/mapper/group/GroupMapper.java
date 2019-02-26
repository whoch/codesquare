package com.bit.codesquare.mapper.group;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.group.GroupInfo;

@Mapper
public interface GroupMapper {
	
	public GroupInfo getGroupInfoUseBoardId(int boardId);
	public List<GroupInfo> getGroupInfoUserLeader(String userId);

}
