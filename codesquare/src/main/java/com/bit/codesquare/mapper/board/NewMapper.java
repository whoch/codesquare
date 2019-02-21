package com.bit.codesquare.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.paging.Criteria;

@Mapper
public interface NewMapper {


//	public List<Board> getAllNew() throws Exception;
	public List<Board> getkind() throws Exception;
	Board getboardKindId(String boardKindId) throws Exception;
	Board getid(int id) throws Exception;
	Board boardkindid(String boardkindid) throws Exception;
	void insert(Board board) throws Exception;
	void updateCount(int id) throws Exception;
	void delete(int id) throws Exception;
	void update(Board board) throws Exception;
	
	public List<Board> listCriteria(Criteria cri) throws Exception;
	public int listCountCriteria(Criteria cri) throws Exception;
	int countPaging(Criteria cri);
}
