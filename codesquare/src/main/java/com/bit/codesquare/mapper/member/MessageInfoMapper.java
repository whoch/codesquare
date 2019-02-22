package com.bit.codesquare.mapper.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.codesquare.dto.member.MessageInfo;
@Mapper
public interface MessageInfoMapper {

	
	public List <MessageInfo> getReceivedMessageList(String recipient);
	public List <MessageInfo> getSentMessageList(String sender);
	
	public MessageInfo sendMessage(MessageInfo messageInfo);

	
	public MessageInfo readMessage(int id);
	public int changeToRead(int id);
	public int deleteMessage(int id);


}
