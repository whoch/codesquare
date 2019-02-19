package com.bit.codesquare.mapper.seminar;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.seminar.SeminarInfo;

@Mapper
public interface SeminarMapper {
	
	public List<Board> seminarList() throws Exception;
	public List<SeminarInfo> seminarInfoList() throws Exception;
	public Board getid(int id) throws Exception;
}
