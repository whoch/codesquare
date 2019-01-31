package com.bit.codesquare.mapper.comment;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.comment.LectureReview;

@Mapper
public interface CommentMapper {
	
	public int insertReview(LectureReview lReview)throws Exception;
	public int updateLikePlus(LectureReview lReview)throws Exception;
	public int updateLikeMinus(LectureReview lReview)throws Exception;
	public int deleteReview(int id)throws Exception;
	public int updateReview(LectureReview lReview)throws Exception;
}
