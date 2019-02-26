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
	
	String searchOption;
	String keyword;
	
	String extension;
	String thumbnailPath;
}
