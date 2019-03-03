package com.bit.codesquare.service.planner;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.codesquare.mapper.group.GroupMapper;
import com.bit.codesquare.mapper.planner.CalendarMapper;
import com.bit.codesquare.util.CodesquareUtil;

@Service
public class CalendarService {
	@Autowired
	CalendarMapper calMapper;
	@Autowired
	GroupMapper groupMapper;
	@Autowired
	CodesquareUtil util;

	Logger logger = LoggerFactory.getLogger(CalendarService.class);
	
	
	
	


	

}

