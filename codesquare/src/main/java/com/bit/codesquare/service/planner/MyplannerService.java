package com.bit.codesquare.service.planner;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	

	public List<UserGroupWorkList> getUsergetGroupWorkList(){
		List<UserGroupWorkList> groupWorkList = myplannerMapper.getGroupWorkList();
		for(UserGroupWorkList list : groupWorkList) {
			list.setWriteDateFormat(DashboardService.getDifferenceInTime(list.getWriteDate()));			
		}
		return groupWorkList;
	}
	
//	public void getUserBookmarkKinds() {
//		Map<String,String> categori = new HashMap<String, String>();
//		categori.put("All", "전체");
//		categori.put("Cod", "코드리뷰");
//		categori.put("Com", "커뮤니티");
//		categori.put("Cur", "커리큘럼");
//		categori.put("Grp", "그룹");
//		categori.put("Lrn", "강의");
//		categori.put("New", "새소식");
//		categori.put("Smn", "세미나");
//		categori.put("Std", "스터디");
//		categori.put("Usr", "회원");
//		
//		String[] bookmarkOfUser = myplannerMapper.getUserBookmarkKinds();
////		java에서 할지 스크립트에서 할지
//	}
//	
	public List<UserBookmarkList> getUserBookmarkList(){
		List<UserBookmarkList> userBookmarkList = myplannerMapper.getUserBookmarkList();
		for(UserBookmarkList list : userBookmarkList) {
			list.setWriteDateFormat(DashboardService.getDifferenceInTime(list.getWriteDate()));			
		}
		return userBookmarkList;
	}

}
