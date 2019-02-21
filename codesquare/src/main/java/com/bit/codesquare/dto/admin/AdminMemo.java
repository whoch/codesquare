package com.bit.codesquare.dto.admin;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AdminMemo {
	LocalDateTime writeDate;
	String content;
	String author;
	String writeDateFomat;
}
