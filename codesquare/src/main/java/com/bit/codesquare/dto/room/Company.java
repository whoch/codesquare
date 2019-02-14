package com.bit.codesquare.dto.room;

import lombok.Data;

@Data
public class Company {

	String id;
	String localeid;
	String companyname;
	String coordinates;
	String companycontent;
	String address;
	String extension;
	
	String searchOption;
	String keyword;
}
