package com.bit.codesquare.dto.member;

import java.sql.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper=true)
@Data
public class JoiningAndRecruitmentLog  extends Member{
	
	private String id;
<<<<<<< HEAD
	private String applyUserId;
=======
>>>>>>> branch 'master' of https://github.com/catsbi/codesquare.git
	private String boardId;
	private String boardKindId;
	private String title;
	private String nickName;
	private Date applyDate;
	private Date writeDate;
	private String applyContent;
	private String declineContent;
	private String status;
}
