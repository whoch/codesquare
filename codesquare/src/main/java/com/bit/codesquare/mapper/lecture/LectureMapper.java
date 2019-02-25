package com.bit.codesquare.mapper.lecture;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.lecture.LearningLogInfo;
import com.bit.codesquare.dto.lecture.Lecture;
import com.bit.codesquare.dto.lecture.LectureIntro;
import com.bit.codesquare.dto.lecture.LectureIntroContent;
import com.bit.codesquare.dto.lecture.LectureTag;

import groovyjarjarpicocli.CommandLine.ExecutionException;

@Mapper
public interface LectureMapper {
	
	public List<LectureIntroContent> getAllLecture(Map<String, Object> map) throws Exception;
	public List<String>getAllLectureTag()throws Exception;
	public LectureIntro getLecture(int id) throws Exception;
	public List<Board> getRecommandLecture(int id) throws Exception;
	public List<Board> getLecutreList(int id)throws Exception;
	public Lecture getLectureContent(int id)throws Exception;
	public String getLectureHandWriting(Map<String,Object> map)throws Exception;
	public int getLectureBoardIdno(Map<String, Object> map)throws Exception;
	public int insertBookmark(Map<String, Object> map)throws Exception;
	public int saveNoteContent(Map<String,Object> map)throws Exception;
	public String getLectureStatus(int id)throws Exception;
	public int insertLectureContent(Lecture lecture)throws Exception;
	public int updateLectureContent(Lecture lecture)throws Exception;
	public int saveLearningLog(Map<String,Object> map)throws Exception;
	public int saveLectureVideo(Map<String,Object> map)throws Exception;
	public int updateLectureHit(int boardId)throws Exception;
	public Map<String, Object> getLearningLog(Map<String,Object> map)throws Exception;
	
	@Transactional
	public int updateLearningLogInfo(LearningLogInfo lLogInfo)throws Exception;
	public List<LectureTag> getLectureTag()throws Exception;
	public int insertLectureIntroContent(Map<String,Object> map)throws Exception;
	public int insertLectureDetailContent(Map<String,Object> map)throws Exception;
	public List<LectureIntroContent> searchLectureIntro(Map<String,Object> map)throws Exception;
	
}
