package com.bit.codesquare.dto.admin;

import java.time.LocalDateTime;

import com.bit.codesquare.util.ComparableDateTime;

import lombok.Data;

@Data
public class AdminMemo implements ComparableDateTime{
	LocalDateTime writeDate;
	String content;
	String author;
	String writeDateFomat;
	
	@Override
	public LocalDateTime getDateTimeCompare() {
		// TODO Auto-generated method stub
		return writeDate;
	}

	@Override
	public void setDateTimeCompare(String format) {
		this.writeDateFomat=format;
		
	}
}
