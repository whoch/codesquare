package com.bit.codesquare.dto.comment;

import java.sql.Date;

import lombok.Data;

@Data
public class ReplyDto {

	private Integer cno;
	private Integer bno;
	private String content;
	private String writer;
	private Date reg_date;
	private Integer commentCount;

}
