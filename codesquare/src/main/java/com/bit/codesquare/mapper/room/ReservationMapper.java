package com.bit.codesquare.mapper.room;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.room.Reservation;

@Mapper
public interface ReservationMapper {

	int insert(Reservation reservation) throws Exception;
	void enddate(int no) throws Exception;
}
