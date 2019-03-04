package com.bit.codesquare.controller.group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bit.codesquare.controller.member.MemberController;
import com.bit.codesquare.mapper.group.GroupMapper;
import com.bit.codesquare.util.CodesquareUtil;

@Controller
public class GroupController {

	private final static Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired 
	GroupMapper gm;
	
	@Autowired
	CodesquareUtil csu;
	
//	@GetMapping("glist")
//	public String getMyGroupList (Model model, Authentication auth) {
//		String userId = auth.getName();
//		model.addAttribute("glist", gm.getMyGroupList(userId));
//		
//		return "group/groupList";
//	}
	
	@GetMapping("group/{groupId}")
	public String getGroupInfo(Model model, Authentication auth, @PathVariable String groupId) {
		String userId = auth.getName();
		model.addAttribute("ginfo", gm.getGroupInfo(groupId));
		model.addAttribute("gMember", gm.getGroupMember(groupId));
		model.addAttribute("gLeader", gm.getGroupLeader(groupId));
		model.addAttribute("glist", gm.getMyGroupList(userId));
		model.addAttribute("grpnt", CodesquareUtil.getDateTimeCompareObject(gm.getGroupNotice(groupId)));
		model.addAttribute("grpwk", CodesquareUtil.getDateTimeCompareObject(gm.getGroupWorks(groupId)));
		return "group/groupInfo";
	}
}
