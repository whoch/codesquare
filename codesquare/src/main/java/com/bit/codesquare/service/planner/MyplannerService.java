package com.bit.codesquare.service.planner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.codesquare.dto.planner.UserBookmarkList;
import com.bit.codesquare.dto.planner.UserGroupWorkList;
import com.bit.codesquare.mapper.planner.MyplannerMapper;
import com.bit.codesquare.util.CodesquareUtil;
import com.bit.codesquare.util.ComparableDateTime;

@Service
public class MyplannerService {

	@Autowired
	MyplannerMapper myplannerMapper;
	
	@Autowired
	CodesquareUtil util;
	
	Logger logger = LoggerFactory.getLogger(MyplannerService.class);

	public List<UserGroupWorkList> getUsergetGroupWorkList() {
		List<UserGroupWorkList> groupWorkList = myplannerMapper.getGroupWorkList();
		return (List<UserGroupWorkList>) util.getDateTimeCompareObject(groupWorkList);
	}

	public List<UserBookmarkList> getUserBookmarkList() {
		List<UserBookmarkList> userBookmarkList = myplannerMapper.getUserBookmarkList();
		return (List<UserBookmarkList>) util.getDateTimeCompareObject(userBookmarkList);
	}


	
}
