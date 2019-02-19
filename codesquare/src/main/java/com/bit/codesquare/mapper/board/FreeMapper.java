package com.bit.codesquare.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.board.Board;

@Mapper
public interface FreeMapper {

	public List<Board> getfree() throws Exception;
}
