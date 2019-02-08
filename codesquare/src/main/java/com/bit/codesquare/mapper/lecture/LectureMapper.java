package com.bit.codesquare.mapper.lecture;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.comment.LectureReview;
import com.bit.codesquare.dto.lecture.Lecture;
import com.bit.codesquare.dto.lecture.LectureIntroContent;

@Mapper
public interface LectureMapper {
	
	public List<LectureIntroContent> getAllLecture() throws Exception;
	public Lecture getLecture(int id) throws Exception;
	public List<Board> getRecommandLecture(int id) throws Exception;
	public List<Board> getLecutreList(int id)throws Exception;
	
}
