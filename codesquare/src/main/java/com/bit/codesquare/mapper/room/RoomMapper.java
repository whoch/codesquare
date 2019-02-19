package com.bit.codesquare.mapper.room;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.room.Room;

@Mapper
public interface RoomMapper {
	
	public List<Room> getAllRoom() throws Exception;
	public List<Room> getid(String companyid) throws Exception;
	public Room getroom(String id) throws Exception;
}
