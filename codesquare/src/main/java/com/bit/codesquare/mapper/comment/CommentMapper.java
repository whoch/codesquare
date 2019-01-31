package com.bit.codesquare.mapper.comment;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.comment.LectureReview;

@Mapper
public interface CommentMapper {
	
	public int insertReview(LectureReview lReview)throws Exception;
	public int updateLike(LectureReview lReview)throws Exception;
}
