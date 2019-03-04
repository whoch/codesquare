package com.bit.codesquare.dto.group;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class GroupMemberInfo extends GroupInfo {

	
	private String userId;
	private String groupId;
	private int AuthorId;
	private String Nickname;
	private String Role;
	private String Content;
	private int AttendCount;
	private int AbsenceCount;
	private String profileImagePath;
}
