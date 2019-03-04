package com.bit.codesquare.mapper.planner;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.board.BoardKind;
import com.bit.codesquare.dto.planner.UserBookmarkCatecory;
import com.bit.codesquare.dto.planner.UserBookmarkList;
import com.bit.codesquare.dto.planner.UserGroupWorkList;
import com.bit.codesquare.dto.planner.UserTodoList;


@Mapper
public interface MyplannerMapper {
	public List<UserGroupWorkList> getGroupWorkList(String userId);
	public List<UserBookmarkList> getUserBookmarkList(String userId);
	public List<UserBookmarkCatecory> getUserBookmarkKinds(String userId);
	
	public int deleteBookmarkUsingId(Map<String, String> data);
	public int updateBookmarkDeleteCount(Map<String, String> data);
	
	public List<UserTodoList> getUserTodoList(String userId);
	public int writeTodo(UserTodoList userTodoList);
	public int updateTodo(Map<String, String> data);
	public int updateTodoStatus(Map<String, String> data);
	
	public String[] getRowNumTodo(String userId);
	public String getBoardSubjectUseBoardId(int boardId);
	public BoardKind getBoardkindDetailUseBoardId(int boardId);
}
