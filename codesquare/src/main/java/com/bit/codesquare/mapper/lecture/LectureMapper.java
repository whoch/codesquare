package com.bit.codesquare.mapper.lecture;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.lecture.Lecture;
import com.bit.codesquare.dto.lecture.LectureIntro;
import com.bit.codesquare.dto.lecture.LectureIntroContent;

@Mapper
public interface LectureMapper {
	
	public List<LectureIntroContent> getAllLecture() throws Exception;
	public LectureIntro getLecture(int id) throws Exception;
	public List<Board> getRecommandLecture(int id) throws Exception;
	public List<Board> getLecutreList(int id)throws Exception;
	public Lecture getLectureContent(int id)throws Exception;
	public String getLectureHandWriting(Map<String,Object> map)throws Exception;
	public int getLectureBoardIdno(Map<String, Object> map)throws Exception;
	public int insertBookmark(Map<String, Object> map)throws Exception;
	public int saveNoteContent(Map<String,Object> map)throws Exception;
	public String getLectureStatus(int boardId)throws Exception;
}
