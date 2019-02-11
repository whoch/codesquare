package com.bit.codesquare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.codesquare.dto.board.Board;
import com.bit.codesquare.mapper.member.MemberMapper;

@Service
public class MemberService {

	@Autowired
	private MemberMapper mm;
	
	
	//private Board board;
	
	
	public List <Board> getWantedList(String userId){
		
	List<Board> list =  mm.getWantedList(userId);
	//logger.info(mm.getWantedList(userId).toString());
	
	 //for(int i =0; i<list.size(); i++) {
	 //board.setWantedPlist( mm.getWantedPList(mm.getWantedList(userId).get(0).getId()));
	//System.out.println(mm.getWantedPList(mm.getWantedList(userId).get(i).getId()));
	 //}
	for(Board item : list) {
		item.setWantedPlist(mm.getWantedPList(item.getId()));
	}
	 
	 
	
	return list;
	}
}
