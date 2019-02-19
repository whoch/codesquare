package com.bit.codesquare.util;

import lombok.Getter;

@Getter
public enum DayEnum {
	월(1,"평일"),
	화(2,"평일"),
	수(3,"평일"),
	목(4,"평일"),
	금(5,"평일"),
	토(6,"주말"),
	일(7,"주말");
	
	private int index;
	private String index2;
	
	DayEnum(int index, String index2) {
		this.index = index;
		this.index2 = index2;
	}

}
