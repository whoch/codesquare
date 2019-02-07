package com.bit.codesquare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.dto.paging.Criteria;
import com.bit.codesquare.mapper.board.NewMapper;

@Service
public class NewService {

	@Autowired
	NewMapper newMapper;
	
	public List<Board> listCriteria(Criteria cri) throws Exception {
		return newMapper.listCriteria(cri);
	}
	public int listCountCriteria(Criteria cri) throws Exception {
		return newMapper.countPaging(cri);
	}
}
