package com.bit.codesquare.dto.member;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper=true)
@Data
public class InstructorInfo extends Member  {

	//private String userId;
	private String introContent;
	private String history;


	
}
