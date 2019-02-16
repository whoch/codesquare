package com.bit.codesquare.mapper.planner;

import java.util.List;
import java.util.Map;

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
	
	public int deleteUsingId(Map map);
	
	public List<UserTodoList> getUserTodoList();
	public int writeTodo(UserTodoList userTodoList);
	public int updateTodo(Map map);
	public int updateTodoStatus(Map map);
	
	public String[] getRowNumTodo();
}
