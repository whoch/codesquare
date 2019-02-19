package com.bit.codesquare.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.admin.RepotedInfo;
import com.bit.codesquare.dto.board.Board;

@Mapper
public interface AdminMapper {
	
	public List<Board> getRecommandList() throws Exception;
	public List<RepotedInfo> getFiveRepotedInfo() throws Exception;
//	public List<> getTodoList(String id) throws Exception;
	
}
