package com.bit.codesquare.mapper.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.board.Board;

@Mapper
public interface BoardMapper {
	
	public List<Board> getRecommandList() throws Exception;
	public List<Board> getQuickBoardList(String tag) throws Exception;
	public List<String> getAllBlackKeyword()throws Exception;
	public List<Board> getAllBoardList(Map<String,Object> map)throws Exception;
	public List<Map<String,Object>> getBoardKindList()throws Exception;
	public List<Map<String,Object>> getBookmarkBoardKindList()throws Exception;
}
