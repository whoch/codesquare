package com.bit.codesquare.dto.member;

import java.sql.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper=true)
@Data
public class JoiningAndRecruitmentLog  extends Member{
	
	private int boardId;
	private String boardKindId;
	private String applyUserId;
	private String title;
	private String nickname;
	private Date applyDate;
	private Date writeDate;
	private String applyContent;
	private String declineContent;
	private String status;
}
