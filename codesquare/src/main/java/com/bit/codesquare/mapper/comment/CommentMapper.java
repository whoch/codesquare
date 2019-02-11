package com.bit.codesquare.mapper.comment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.comment.Comment;
import com.bit.codesquare.dto.comment.LectureReview;

@Mapper
public interface CommentMapper {
	
	public int insertReview(LectureReview lReview)throws Exception;
	public int updateLikePlus(LectureReview lReview)throws Exception;
	public int updateLikeMinus(LectureReview lReview)throws Exception;
	public int deleteReview(int id)throws Exception;
	public int updateReview(LectureReview lReview)throws Exception;
	public List<LectureReview> getLectureReview(int id)throws Exception;
	public List<Comment> getQNACommentList(int id)throws Exception;
	public int insertQNAComment(Comment comment)throws Exception;
	public int deleteQNAComment(int id)throws Exception;
	public int updateQNAComment(Comment comment)throws Exception;
	public int searchChildComment(int id)throws Exception;
	public int searchParentComment(int id)throws Exception;
	
	public List<Comment> getCommentList(Map<String, Object> map)throws Exception;
	public int insertComment(Comment comment)throws Exception;
	public int deleteComment(int id)throws Exception;
	public int updateComment(Comment comment)throws Exception;
}
