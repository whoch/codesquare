package com.bit.codesquare.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.paging.Criteria;

@Mapper
public interface FreeMapper {

	public List<Board> getfree(@Param("cri") Criteria cri, String keyword, String searchOption) throws Exception;
	int countPaging(@Param("cri") Criteria cri, String keyword, String searchOption);
	void insert(Board board) throws Exception;
}
