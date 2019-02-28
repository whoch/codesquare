package com.bit.codesquare.mapper.study;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.board.BoardKind;

@Mapper
public interface StudyMapper {
	
	public List<Board> getBoardList(String boardName);
	public Board getBoardView(String boardName, int boardId);
	public BoardKind getBoardKind(String boardNameEn);
	public void addBookmark(Map<String, String> data);
	public void deleteBookmark(Map<String, String> data);
	public String getBookmarkId(int boardId);
	public void writeStudyWantedBoard(Board board);
	public void deleteStudyWantedBoard(int boardId);
	public void updateBoardStatus(Map<String, String> data);
	
	
	
	
}
