package com.bit.codesquare.mapper.study;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.board.BoardKind;
import com.bit.codesquare.dto.group.GroupInfo;

@Mapper
public interface StudyMapper {
	
	public List<Board> getBoardList(String boardName);
	public Board getBoardView(String boardName, int boardId);
	public BoardKind getBoardKind(String boardNameEn);
	public GroupInfo getGroupInfo(int boardId);
	public void addBookmark(Map<String, String> data);
	public String getBookmark(int boardId);
}
