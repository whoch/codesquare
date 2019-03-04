package com.bit.codesquare.mapper.comment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.comment.ReplyDto;

@Mapper
public interface ReplyMapper {

	public List<ReplyDto> commentList(int bno) throws Exception;
	public int commentInsert(ReplyDto comment) throws Exception;
	public int commentDelete(int cno) throws Exception;
	public int commentUpdate(ReplyDto comment) throws Exception;
	public Integer coCount(Integer id) throws Exception;
	void inco(int bno) throws Exception;
}