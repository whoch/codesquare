package com.bit.codesquare.dto.paging;

import lombok.Data;

@Data
public class SearchCriteria extends Criteria {
	
	private String searchOption;
	private String keyword;
}
