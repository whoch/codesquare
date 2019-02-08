package com.bit.codesquare.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.board.Board;

@Mapper
public interface BoardMapper {
	
	public List<Board> getRecommandList() throws Exception;
	public List<Board> getQuickBoardList(String tag) throws Exception;

	
}
