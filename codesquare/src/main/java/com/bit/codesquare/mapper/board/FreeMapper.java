package com.bit.codesquare.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.paging.Criteria;

@Mapper
public interface FreeMapper {

	public List<Board> getfree(Criteria cri) throws Exception;
	int countPaging(Criteria cri);
	void insert(Board board) throws Exception;
	
	public List<Board> freeSearch(Board board);
}
