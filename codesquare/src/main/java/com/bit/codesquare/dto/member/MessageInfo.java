package com.bit.codesquare.dto.member;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MessageInfo {

	
	private int id;
	private String recipient;
	private String recipientNickName;
	private String sender;
	private String senderNickName;
	private String messageTitle;
	private String messageContent;
	private Timestamp sendDate;
	private Timestamp readDate;
	private String readStatus;
	private String showStatus;

}

