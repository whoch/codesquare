package com.bit.codesquare.mapper.planner;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.planner.UserBookmarkCatecory;
import com.bit.codesquare.dto.planner.UserBookmarkList;
import com.bit.codesquare.dto.planner.UserGroupWorkList;
import com.bit.codesquare.dto.planner.UserTodoList;


@Mapper
public interface MyplannerMapper {
	public List<UserGroupWorkList> getGroupWorkList();
	public List<UserBookmarkList> getUserBookmarkList();
	public List<UserBookmarkCatecory> getUserBookmarkKinds();
	public List<UserTodoList> getUserTodoList();
	public void writeTodo(UserTodoList userTodoList);
	
}
