package com.bit.codesquare.mapper.planner;

import java.util.HashSet;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.planner.UserBookmarkList;
import com.bit.codesquare.dto.planner.UserGroupWorkList;


@Mapper
public interface MyplannerMapper {
	public List<UserGroupWorkList> getGroupWorkList();
	public List<UserBookmarkList> getUserBookmarkList();
	public String[] getUserBookmarkKinds();
	
	
}
