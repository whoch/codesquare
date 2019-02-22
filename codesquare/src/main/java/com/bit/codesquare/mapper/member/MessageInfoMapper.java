package com.bit.codesquare.mapper.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bit.codesquare.dto.member.MessageInfo;
import com.bit.codesquare.dto.paging.Criteria;
@Mapper
public interface MessageInfoMapper {

	
	public List <MessageInfo> getReceivedMessageList(String recipient,@Param("cri") Criteria cri);
	int countRPaging(String recipient, @Param("cri") Criteria cri);
	
	public List <MessageInfo> getSentMessageList(String sender, @Param("cri")Criteria cri);
	int countSPaging(String sender, @Param("cri") Criteria cri);
	
	
	public int sendMessage(MessageInfo messageInfo);
	public int messageCountUp(String recipient);

	
	public MessageInfo readMessage(int id);
	public int setUnreadMessageCount(String recipient);
	
	
	public int changeToRead(int id);

	public int deleteMessage(int[] ids);


}
