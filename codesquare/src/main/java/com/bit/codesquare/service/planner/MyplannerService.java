package com.bit.codesquare.service.planner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.codesquare.dto.planner.UserBookmarkList;
import com.bit.codesquare.dto.planner.UserGroupWorkList;
import com.bit.codesquare.mapper.planner.MyplannerMapper;

@Service
public class MyplannerService {

	@Autowired
	MyplannerMapper myplannerMapper;
	Logger logger = LoggerFactory.getLogger(MyplannerService.class);

	public List<UserGroupWorkList> getUsergetGroupWorkList() {
		List<UserGroupWorkList> groupWorkList = myplannerMapper.getGroupWorkList();
		for (UserGroupWorkList list : groupWorkList) {
			list.setWriteDateFormat(DashboardService.getDifferenceInTime(list.getWriteDate()));
		}
		return groupWorkList;
	}

	public List<UserBookmarkList> getUserBookmarkList() {
		List<UserBookmarkList> userBookmarkList = myplannerMapper.getUserBookmarkList();
		for (UserBookmarkList list : userBookmarkList) {
			list.setWriteDateFormat(DashboardService.getDifferenceInTime(list.getWriteDate()));
		}
		return userBookmarkList;
	}

}
