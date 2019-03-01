package com.bit.codesquare.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.board.BoardKind;
import com.bit.codesquare.dto.paging.Criteria;

@Mapper
public interface FreeMapper {

	public List<Board> getfree(@Param("cri") Criteria cri, String keyword, String searchOption, String boardKindId) throws Exception;
	int countPaging(@Param("cri") Criteria cri, String keyword, String searchOption, String boardKindId);
	public List<Board> getkind() throws Exception;
	Board getid(int id) throws Exception;
	void insert(Board board) throws Exception;
	void updateCount(int id) throws Exception;
	void delete(int id) throws Exception;
	void update(Board board) throws Exception;
	
	BoardKind getBoardName(String boardName) throws Exception;
}
